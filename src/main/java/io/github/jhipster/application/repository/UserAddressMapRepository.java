package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.UserAddressMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserAddressMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAddressMapRepository extends JpaRepository<UserAddressMap, Long> {

}
