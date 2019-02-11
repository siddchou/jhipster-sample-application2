package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ContractorServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContractorService and its DTO ContractorServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {ContractorMapper.class, ServicesMapper.class})
public interface ContractorServiceMapper extends EntityMapper<ContractorServiceDTO, ContractorService> {

    @Mapping(source = "contractor.id", target = "contractorId")
    @Mapping(source = "services.id", target = "servicesId")
    ContractorServiceDTO toDto(ContractorService contractorService);

    @Mapping(source = "contractorId", target = "contractor")
    @Mapping(source = "servicesId", target = "services")
    ContractorService toEntity(ContractorServiceDTO contractorServiceDTO);

    default ContractorService fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContractorService contractorService = new ContractorService();
        contractorService.setId(id);
        return contractorService;
    }
}
