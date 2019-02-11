package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.UserAddressMapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAddressMap and its DTO UserAddressMapDTO.
 */
@Mapper(componentModel = "spring", uses = {AppUserMapper.class, AddressMapper.class})
public interface UserAddressMapMapper extends EntityMapper<UserAddressMapDTO, UserAddressMap> {

    @Mapping(source = "appUser.id", target = "appUserId")
    @Mapping(source = "address.id", target = "addressId")
    UserAddressMapDTO toDto(UserAddressMap userAddressMap);

    @Mapping(source = "appUserId", target = "appUser")
    @Mapping(source = "addressId", target = "address")
    UserAddressMap toEntity(UserAddressMapDTO userAddressMapDTO);

    default UserAddressMap fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAddressMap userAddressMap = new UserAddressMap();
        userAddressMap.setId(id);
        return userAddressMap;
    }
}
