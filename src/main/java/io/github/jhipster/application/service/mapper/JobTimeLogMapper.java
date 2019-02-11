package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.JobTimeLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobTimeLog and its DTO JobTimeLogDTO.
 */
@Mapper(componentModel = "spring", uses = {JobHistoryMapper.class})
public interface JobTimeLogMapper extends EntityMapper<JobTimeLogDTO, JobTimeLog> {

    @Mapping(source = "jobHistory.id", target = "jobHistoryId")
    JobTimeLogDTO toDto(JobTimeLog jobTimeLog);

    @Mapping(source = "jobHistoryId", target = "jobHistory")
    JobTimeLog toEntity(JobTimeLogDTO jobTimeLogDTO);

    default JobTimeLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobTimeLog jobTimeLog = new JobTimeLog();
        jobTimeLog.setId(id);
        return jobTimeLog;
    }
}
