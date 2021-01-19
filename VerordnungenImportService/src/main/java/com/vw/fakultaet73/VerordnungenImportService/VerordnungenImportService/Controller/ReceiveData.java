package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.Controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.DecreeEntity;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.LastChanged;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.LastChangedStorage;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.errors.RestTemplateResponseErrorHandler;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.services.ExportService;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.services.ImportService;


@EnableScheduling
@RestController
public class ReceiveData {
	
	@Autowired
	private ImportService importService;
	
	@Autowired
	private ExportService exportService;
	
	private LastChangedStorage lastChangedStorage = new LastChangedStorage();
	
	private final String GET_URL = "http://dataservice:8080/decrees";
	
	@CrossOrigin("*")
	@GetMapping("/maches/{state}")
	@ResponseStatus(HttpStatus.OK)
	public List<DecreeEntity> saveFilteredList(@PathVariable String state) {
		this.importService.deletePerState(state);
		this.lastChangedStorage.change(state);
		return this.importService.saveDecrees((parse(state)));
	}
	
	@CrossOrigin("*")
	@GetMapping("/change/{state}")
	@ResponseStatus(HttpStatus.OK)
	public LastChanged getTime(@PathVariable String state) {
		return this.lastChangedStorage.getLastChangedByState(state);
	}
	
	@CrossOrigin("*")
	@GetMapping("/maches")
	@ResponseStatus(HttpStatus.OK)
	public List<DecreeEntity> saveList() {
		this.importService.deleteAll();
		this.lastChangedStorage.changeAll();
		return this.importService.saveDecrees((parse("")));
	}
	
	private List<DecreeEntity> parse(String state) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		DecreeEntity[] response;
		if(state.length() == 0 || state == null) {
			response = restTemplate.getForObject(this.GET_URL,DecreeEntity[].class);
			
		} else {
			String URL = this.GET_URL + "/" + state;
			response = restTemplate.getForObject(URL,DecreeEntity[].class);
		}		
		List<DecreeEntity> decreesList = new ArrayList<>();
		for (DecreeEntity decreeEntity : response) {
			decreesList.add(decreeEntity);
		}
		return decreesList;
	}
	
	@CrossOrigin("*")
	@PostMapping("/newDecree")
	@ResponseStatus(HttpStatus.OK)
	public  DecreeEntity getDecrees(@RequestBody DecreeEntity decreeEntity) {
		this.lastChangedStorage.change(decreeEntity.getState());
		return this.importService.saveDecree(decreeEntity);
	}
	
	@CrossOrigin("*")
	@GetMapping("/decrees")
	@ResponseStatus(HttpStatus.OK)
	public List<DecreeEntity> getDecrees() {
		return this.exportService.getDecreeList();
	}
	
	@CrossOrigin("*")
	@PutMapping("/editDecree")
	@ResponseStatus(HttpStatus.OK)
	public DecreeEntity editDecree(@RequestBody DecreeEntity decreeEntity) {
		this.lastChangedStorage.change(decreeEntity.getState());
		return this.importService.saveDecree(decreeEntity);
	}
	
	@CrossOrigin("*")
	@PostMapping("/deleteDecree")
	@ResponseStatus(HttpStatus.OK)
	public void deleteDecree(@RequestBody DecreeEntity decreeEntity) {
		this.lastChangedStorage.change(decreeEntity.getState());
		this.importService.deleteDecree(decreeEntity);
	}
	
}
