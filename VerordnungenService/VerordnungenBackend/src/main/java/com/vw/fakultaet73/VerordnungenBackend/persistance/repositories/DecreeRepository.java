package com.vw.fakultaet73.VerordnungenBackend.persistance.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vw.fakultaet73.VerordnungenBackend.persistance.DecreeEntity;

@Repository
public interface DecreeRepository extends CrudRepository<DecreeEntity, Long> {
}
