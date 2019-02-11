package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ContractorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contractor and its DTO ContractorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContractorMapper extends EntityMapper<ContractorDTO, Contractor> {



    default Contractor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contractor contractor = new Contractor();
        contractor.setId(id);
        return contractor;
    }
}
