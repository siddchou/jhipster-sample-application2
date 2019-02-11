package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.JobTimeLog;
import io.github.jhipster.application.repository.JobTimeLogRepository;
import io.github.jhipster.application.repository.search.JobTimeLogSearchRepository;
import io.github.jhipster.application.service.JobTimeLogService;
import io.github.jhipster.application.service.dto.JobTimeLogDTO;
import io.github.jhipster.application.service.mapper.JobTimeLogMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobTimeLogResource REST controller.
 *
 * @see JobTimeLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class JobTimeLogResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    @Autowired
    private JobTimeLogRepository jobTimeLogRepository;

    @Autowired
    private JobTimeLogMapper jobTimeLogMapper;

    @Autowired
    private JobTimeLogService jobTimeLogService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.JobTimeLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private JobTimeLogSearchRepository mockJobTimeLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restJobTimeLogMockMvc;

    private JobTimeLog jobTimeLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobTimeLogResource jobTimeLogResource = new JobTimeLogResource(jobTimeLogService);
        this.restJobTimeLogMockMvc = MockMvcBuilders.standaloneSetup(jobTimeLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTimeLog createEntity(EntityManager em) {
        JobTimeLog jobTimeLog = new JobTimeLog()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isValidated(DEFAULT_IS_VALIDATED);
        return jobTimeLog;
    }

    @Before
    public void initTest() {
        jobTimeLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobTimeLog() throws Exception {
        int databaseSizeBeforeCreate = jobTimeLogRepository.findAll().size();

        // Create the JobTimeLog
        JobTimeLogDTO jobTimeLogDTO = jobTimeLogMapper.toDto(jobTimeLog);
        restJobTimeLogMockMvc.perform(post("/api/job-time-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobTimeLogDTO)))
            .andExpect(status().isCreated());

        // Validate the JobTimeLog in the database
        List<JobTimeLog> jobTimeLogList = jobTimeLogRepository.findAll();
        assertThat(jobTimeLogList).hasSize(databaseSizeBeforeCreate + 1);
        JobTimeLog testJobTimeLog = jobTimeLogList.get(jobTimeLogList.size() - 1);
        assertThat(testJobTimeLog.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobTimeLog.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobTimeLog.isIsValidated()).isEqualTo(DEFAULT_IS_VALIDATED);

        // Validate the JobTimeLog in Elasticsearch
        verify(mockJobTimeLogSearchRepository, times(1)).save(testJobTimeLog);
    }

    @Test
    @Transactional
    public void createJobTimeLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobTimeLogRepository.findAll().size();

        // Create the JobTimeLog with an existing ID
        jobTimeLog.setId(1L);
        JobTimeLogDTO jobTimeLogDTO = jobTimeLogMapper.toDto(jobTimeLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTimeLogMockMvc.perform(post("/api/job-time-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobTimeLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobTimeLog in the database
        List<JobTimeLog> jobTimeLogList = jobTimeLogRepository.findAll();
        assertThat(jobTimeLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the JobTimeLog in Elasticsearch
        verify(mockJobTimeLogSearchRepository, times(0)).save(jobTimeLog);
    }

    @Test
    @Transactional
    public void getAllJobTimeLogs() throws Exception {
        // Initialize the database
        jobTimeLogRepository.saveAndFlush(jobTimeLog);

        // Get all the jobTimeLogList
        restJobTimeLogMockMvc.perform(get("/api/job-time-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTimeLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getJobTimeLog() throws Exception {
        // Initialize the database
        jobTimeLogRepository.saveAndFlush(jobTimeLog);

        // Get the jobTimeLog
        restJobTimeLogMockMvc.perform(get("/api/job-time-logs/{id}", jobTimeLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobTimeLog.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJobTimeLog() throws Exception {
        // Get the jobTimeLog
        restJobTimeLogMockMvc.perform(get("/api/job-time-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobTimeLog() throws Exception {
        // Initialize the database
        jobTimeLogRepository.saveAndFlush(jobTimeLog);

        int databaseSizeBeforeUpdate = jobTimeLogRepository.findAll().size();

        // Update the jobTimeLog
        JobTimeLog updatedJobTimeLog = jobTimeLogRepository.findById(jobTimeLog.getId()).get();
        // Disconnect from session so that the updates on updatedJobTimeLog are not directly saved in db
        em.detach(updatedJobTimeLog);
        updatedJobTimeLog
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isValidated(UPDATED_IS_VALIDATED);
        JobTimeLogDTO jobTimeLogDTO = jobTimeLogMapper.toDto(updatedJobTimeLog);

        restJobTimeLogMockMvc.perform(put("/api/job-time-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobTimeLogDTO)))
            .andExpect(status().isOk());

        // Validate the JobTimeLog in the database
        List<JobTimeLog> jobTimeLogList = jobTimeLogRepository.findAll();
        assertThat(jobTimeLogList).hasSize(databaseSizeBeforeUpdate);
        JobTimeLog testJobTimeLog = jobTimeLogList.get(jobTimeLogList.size() - 1);
        assertThat(testJobTimeLog.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobTimeLog.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobTimeLog.isIsValidated()).isEqualTo(UPDATED_IS_VALIDATED);

        // Validate the JobTimeLog in Elasticsearch
        verify(mockJobTimeLogSearchRepository, times(1)).save(testJobTimeLog);
    }

    @Test
    @Transactional
    public void updateNonExistingJobTimeLog() throws Exception {
        int databaseSizeBeforeUpdate = jobTimeLogRepository.findAll().size();

        // Create the JobTimeLog
        JobTimeLogDTO jobTimeLogDTO = jobTimeLogMapper.toDto(jobTimeLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTimeLogMockMvc.perform(put("/api/job-time-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobTimeLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobTimeLog in the database
        List<JobTimeLog> jobTimeLogList = jobTimeLogRepository.findAll();
        assertThat(jobTimeLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the JobTimeLog in Elasticsearch
        verify(mockJobTimeLogSearchRepository, times(0)).save(jobTimeLog);
    }

    @Test
    @Transactional
    public void deleteJobTimeLog() throws Exception {
        // Initialize the database
        jobTimeLogRepository.saveAndFlush(jobTimeLog);

        int databaseSizeBeforeDelete = jobTimeLogRepository.findAll().size();

        // Delete the jobTimeLog
        restJobTimeLogMockMvc.perform(delete("/api/job-time-logs/{id}", jobTimeLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobTimeLog> jobTimeLogList = jobTimeLogRepository.findAll();
        assertThat(jobTimeLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the JobTimeLog in Elasticsearch
        verify(mockJobTimeLogSearchRepository, times(1)).deleteById(jobTimeLog.getId());
    }

    @Test
    @Transactional
    public void searchJobTimeLog() throws Exception {
        // Initialize the database
        jobTimeLogRepository.saveAndFlush(jobTimeLog);
        when(mockJobTimeLogSearchRepository.search(queryStringQuery("id:" + jobTimeLog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(jobTimeLog), PageRequest.of(0, 1), 1));
        // Search the jobTimeLog
        restJobTimeLogMockMvc.perform(get("/api/_search/job-time-logs?query=id:" + jobTimeLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTimeLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTimeLog.class);
        JobTimeLog jobTimeLog1 = new JobTimeLog();
        jobTimeLog1.setId(1L);
        JobTimeLog jobTimeLog2 = new JobTimeLog();
        jobTimeLog2.setId(jobTimeLog1.getId());
        assertThat(jobTimeLog1).isEqualTo(jobTimeLog2);
        jobTimeLog2.setId(2L);
        assertThat(jobTimeLog1).isNotEqualTo(jobTimeLog2);
        jobTimeLog1.setId(null);
        assertThat(jobTimeLog1).isNotEqualTo(jobTimeLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTimeLogDTO.class);
        JobTimeLogDTO jobTimeLogDTO1 = new JobTimeLogDTO();
        jobTimeLogDTO1.setId(1L);
        JobTimeLogDTO jobTimeLogDTO2 = new JobTimeLogDTO();
        assertThat(jobTimeLogDTO1).isNotEqualTo(jobTimeLogDTO2);
        jobTimeLogDTO2.setId(jobTimeLogDTO1.getId());
        assertThat(jobTimeLogDTO1).isEqualTo(jobTimeLogDTO2);
        jobTimeLogDTO2.setId(2L);
        assertThat(jobTimeLogDTO1).isNotEqualTo(jobTimeLogDTO2);
        jobTimeLogDTO1.setId(null);
        assertThat(jobTimeLogDTO1).isNotEqualTo(jobTimeLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobTimeLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobTimeLogMapper.fromId(null)).isNull();
    }
}
