package com.vw.fakultaet73.VerordnungenBackend.persistance.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vw.fakultaet73.VerordnungenBackend.persistance.DecreeEntity;

@Repository
public interface DecreeRepository extends MongoRepository<DecreeEntity, Long> {
}
