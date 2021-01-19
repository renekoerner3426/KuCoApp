package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.DecreeEntity;
import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.repositories.DecreeEntityRepository;

@Service
public class ImportService {
	
	@Autowired
	private DecreeEntityRepository decreeRepository;
	
	public List<DecreeEntity> saveDecrees(List<DecreeEntity> decrees) {
		Iterable<DecreeEntity> savedEntitys = this.decreeRepository.saveAll(decrees);
		
		List<DecreeEntity> decreeList = new ArrayList<>();
		savedEntitys.forEach(savedEntity -> decreeList.add(savedEntity));
		return decreeList;
	}

	public DecreeEntity saveDecree(DecreeEntity decree) {
		return this.decreeRepository.save(decree);
	}
	
	public void deleteAll() {
		this.decreeRepository.deleteAll();
	}
	
	public void deleteDecree(DecreeEntity decree) {
		this.decreeRepository.delete(decree);
	}
	
	public void deletePerState(String state) {
		Iterable<DecreeEntity> savedEntitys = this.decreeRepository.findAll();		
		savedEntitys.forEach(savedEntity -> {
			if(savedEntity.getState().equals(state)) {
				this.decreeRepository.delete(savedEntity);
			}		
		});
	}
	
}