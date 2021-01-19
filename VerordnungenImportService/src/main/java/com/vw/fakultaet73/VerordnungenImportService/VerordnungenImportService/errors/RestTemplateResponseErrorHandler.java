package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.errors;

import java.io.IOException;


import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler 
  implements ResponseErrorHandler {
	
	 private Logger logger = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);
 
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) 
      throws IOException {
 
        return (
          httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR 
          || httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR);
    }
 
    @Override
    public void handleError(ClientHttpResponse httpResponse) 
      throws IOException {
 
        if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR 5xx
        	logger.error("Fehlercode: " + httpResponse.getRawStatusCode());
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR 4xx
        	logger.error("Fehlercode: " + httpResponse.getRawStatusCode());
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException();
            }
        }
    }
}
