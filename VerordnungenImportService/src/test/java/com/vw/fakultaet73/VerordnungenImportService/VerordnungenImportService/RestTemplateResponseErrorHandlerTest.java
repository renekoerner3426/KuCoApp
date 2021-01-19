package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.DecreeEntity;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.errors.RestTemplateResponseErrorHandler;

@SpringBootTest
@ActiveProfiles("test")
public class RestTemplateResponseErrorHandlerTest {
 
    @Autowired 
    private RestTemplateBuilder builder;
 
    @Test
    public void  handlerTest() {
    	Assertions.assertNotNull(this.builder);
  
        RestTemplate restTemplate = this.builder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
        
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
 
        server
          .expect(ExpectedCount.once(), requestTo("/decrees"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.NOT_FOUND));
 
        Assertions.assertThrows(NotFoundException.class, 
        		() -> {restTemplate.getForObject("/decrees", DecreeEntity.class); server.verify();});
    }
}