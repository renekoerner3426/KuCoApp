package com.vw.fakultaet73.VerordnungenDataService.VerordnungenDataService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
public class DataController {

    @Autowired
    private CSVReader csvReader;

    @GetMapping("/decrees")
	@ResponseStatus(HttpStatus.OK)
	public  List<DecreeEntity> getDecrees() {
		return csvReader.getDecreesList();
	}
    
    @GetMapping("/decrees/{state}")
	@ResponseStatus(HttpStatus.OK)
	public  List<DecreeEntity> getDecreesPerState(@PathVariable String state) {
    	List<DecreeEntity> filteredList = csvReader.getDecreesList();   	
		return filteredList.stream()
                .filter(decree -> decree.getState().equals(state)).collect(Collectors.toList());
	}
    
    
}
