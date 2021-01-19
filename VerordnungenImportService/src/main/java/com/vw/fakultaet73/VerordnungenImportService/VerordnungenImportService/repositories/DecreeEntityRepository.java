package com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vw.fakultaet73.VerordnungenImportService.VerordnungenImportService.entitites.DecreeEntity;

@Repository
public interface DecreeEntityRepository extends MongoRepository<DecreeEntity, Long> {
}