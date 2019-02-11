package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ServicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Services and its DTO ServicesDTO.
 */
@Mapper(componentModel = "spring", uses = {RateMapper.class, CategoryMapper.class, LocationMapper.class})
public interface ServicesMapper extends EntityMapper<ServicesDTO, Services> {

    @Mapping(source = "rate.id", target = "rateId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "location.id", target = "locationId")
    ServicesDTO toDto(Services services);

    @Mapping(source = "rateId", target = "rate")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "locationId", target = "location")
    Services toEntity(ServicesDTO servicesDTO);

    default Services fromId(Long id) {
        if (id == null) {
            return null;
        }
        Services services = new Services();
        services.setId(id);
        return services;
    }
}
