package com.corona.VerordnungenUpload.services;

import java.util.List;
import java.util.Optional;

import com.corona.VerordnungenUpload.entities.RegulationEntity;
import com.corona.VerordnungenUpload.repositories.RegulationEntityRepository;
/**
 * InnerRegulationEntityService
 */
public class RegulationEntityService {

    private final RegulationEntityRepository regulationRepository;

    public RegulationEntityService(RegulationEntityRepository regulationRepository) {
        this.regulationRepository = regulationRepository;
    }

    public Optional<RegulationEntity> findById(long id) {
        return regulationRepository.findById(id);
    }

    public List<RegulationEntity> findAllByStateEntityId(long id) {
        return regulationRepository.findAllByStateEntityId(id);
    }

    public RegulationEntity save(RegulationEntity regulation) {
        regulationRepository.save(regulation);
        return regulationRepository.getOne(regulation.getId());
    }

    public boolean removeRegulation(long regulationId) {
        if (regulationRepository.findById(regulationId).isPresent()) {
            regulationRepository.deleteById(regulationId);
            return true;
        }
        return false;
    }

}