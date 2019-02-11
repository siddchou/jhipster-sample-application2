package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.JobHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobHistory and its DTO JobHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class, ContractorServiceMapper.class, UserAddressMapMapper.class})
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {

    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "contractorService.id", target = "contractorServiceId")
    @Mapping(source = "userAddressMap.id", target = "userAddressMapId")
    JobHistoryDTO toDto(JobHistory jobHistory);

    @Mapping(source = "paymentId", target = "payment")
    @Mapping(source = "contractorServiceId", target = "contractorService")
    @Mapping(source = "userAddressMapId", target = "userAddressMap")
    @Mapping(target = "jobTimeLogs", ignore = true)
    JobHistory toEntity(JobHistoryDTO jobHistoryDTO);

    default JobHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobHistory jobHistory = new JobHistory();
        jobHistory.setId(id);
        return jobHistory;
    }
}
