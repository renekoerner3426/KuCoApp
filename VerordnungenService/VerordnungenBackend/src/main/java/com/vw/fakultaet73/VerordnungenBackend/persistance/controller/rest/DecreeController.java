package com.vw.fakultaet73.VerordnungenBackend.persistance.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vw.fakultaet73.VerordnungenBackend.persistance.DecreeEntity;
import com.vw.fakultaet73.VerordnungenBackend.service.DecreeService;

@RestController
public class DecreeController {
	
	@Autowired
	private DecreeService decreeService;
	
	@CrossOrigin("*")
	@GetMapping("/decrees")
	@ResponseStatus(HttpStatus.OK)
	public List<DecreeEntity> getDecrees() {
		return decreeService.getDecreesList();
	}
}
