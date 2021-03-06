package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.JobHistory;
import io.github.jhipster.application.domain.Payment;
import io.github.jhipster.application.domain.ContractorService;
import io.github.jhipster.application.domain.UserAddressMap;
import io.github.jhipster.application.domain.JobTimeLog;
import io.github.jhipster.application.repository.JobHistoryRepository;
import io.github.jhipster.application.repository.search.JobHistorySearchRepository;
import io.github.jhipster.application.service.JobHistoryService;
import io.github.jhipster.application.service.dto.JobHistoryDTO;
import io.github.jhipster.application.service.mapper.JobHistoryMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.JobHistoryCriteria;
import io.github.jhipster.application.service.JobHistoryQueryService;

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

import io.github.jhipster.application.domain.enumeration.JobStatus;
/**
 * Test class for the JobHistoryResource REST controller.
 *
 * @see JobHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class JobHistoryResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final JobStatus DEFAULT_JOB_STATUS = JobStatus.NOT_STARTED;
    private static final JobStatus UPDATED_JOB_STATUS = JobStatus.PROCESSING;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobHistoryMapper jobHistoryMapper;

    @Autowired
    private JobHistoryService jobHistoryService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.JobHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private JobHistorySearchRepository mockJobHistorySearchRepository;

    @Autowired
    private JobHistoryQueryService jobHistoryQueryService;

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

    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobHistoryResource jobHistoryResource = new JobHistoryResource(jobHistoryService, jobHistoryQueryService);
        this.restJobHistoryMockMvc = MockMvcBuilders.standaloneSetup(jobHistoryResource)
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
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .jobStatus(DEFAULT_JOB_STATUS);
        return jobHistory;
    }

    @Before
    public void initTest() {
        jobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobHistory() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobHistory.getJobStatus()).isEqualTo(DEFAULT_JOB_STATUS);

        // Validate the JobHistory in Elasticsearch
        verify(mockJobHistorySearchRepository, times(1)).save(testJobHistory);
    }

    @Test
    @Transactional
    public void createJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the JobHistory in Elasticsearch
        verify(mockJobHistorySearchRepository, times(0)).save(jobHistory);
    }

    @Test
    @Transactional
    public void getAllJobHistories() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.jobStatus").value(DEFAULT_JOB_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where startDate equals to DEFAULT_START_DATE
        defaultJobHistoryShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the jobHistoryList where startDate equals to UPDATED_START_DATE
        defaultJobHistoryShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultJobHistoryShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the jobHistoryList where startDate equals to UPDATED_START_DATE
        defaultJobHistoryShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where startDate is not null
        defaultJobHistoryShouldBeFound("startDate.specified=true");

        // Get all the jobHistoryList where startDate is null
        defaultJobHistoryShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where endDate equals to DEFAULT_END_DATE
        defaultJobHistoryShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the jobHistoryList where endDate equals to UPDATED_END_DATE
        defaultJobHistoryShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultJobHistoryShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the jobHistoryList where endDate equals to UPDATED_END_DATE
        defaultJobHistoryShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where endDate is not null
        defaultJobHistoryShouldBeFound("endDate.specified=true");

        // Get all the jobHistoryList where endDate is null
        defaultJobHistoryShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByJobStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where jobStatus equals to DEFAULT_JOB_STATUS
        defaultJobHistoryShouldBeFound("jobStatus.equals=" + DEFAULT_JOB_STATUS);

        // Get all the jobHistoryList where jobStatus equals to UPDATED_JOB_STATUS
        defaultJobHistoryShouldNotBeFound("jobStatus.equals=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByJobStatusIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where jobStatus in DEFAULT_JOB_STATUS or UPDATED_JOB_STATUS
        defaultJobHistoryShouldBeFound("jobStatus.in=" + DEFAULT_JOB_STATUS + "," + UPDATED_JOB_STATUS);

        // Get all the jobHistoryList where jobStatus equals to UPDATED_JOB_STATUS
        defaultJobHistoryShouldNotBeFound("jobStatus.in=" + UPDATED_JOB_STATUS);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByJobStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where jobStatus is not null
        defaultJobHistoryShouldBeFound("jobStatus.specified=true");

        // Get all the jobHistoryList where jobStatus is null
        defaultJobHistoryShouldNotBeFound("jobStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        Payment payment = PaymentResourceIntTest.createEntity(em);
        em.persist(payment);
        em.flush();
        jobHistory.setPayment(payment);
        jobHistoryRepository.saveAndFlush(jobHistory);
        Long paymentId = payment.getId();

        // Get all the jobHistoryList where payment equals to paymentId
        defaultJobHistoryShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the jobHistoryList where payment equals to paymentId + 1
        defaultJobHistoryShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByContractorServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ContractorService contractorService = ContractorServiceResourceIntTest.createEntity(em);
        em.persist(contractorService);
        em.flush();
        jobHistory.setContractorService(contractorService);
        jobHistoryRepository.saveAndFlush(jobHistory);
        Long contractorServiceId = contractorService.getId();

        // Get all the jobHistoryList where contractorService equals to contractorServiceId
        defaultJobHistoryShouldBeFound("contractorServiceId.equals=" + contractorServiceId);

        // Get all the jobHistoryList where contractorService equals to contractorServiceId + 1
        defaultJobHistoryShouldNotBeFound("contractorServiceId.equals=" + (contractorServiceId + 1));
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByUserAddressMapIsEqualToSomething() throws Exception {
        // Initialize the database
        UserAddressMap userAddressMap = UserAddressMapResourceIntTest.createEntity(em);
        em.persist(userAddressMap);
        em.flush();
        jobHistory.setUserAddressMap(userAddressMap);
        jobHistoryRepository.saveAndFlush(jobHistory);
        Long userAddressMapId = userAddressMap.getId();

        // Get all the jobHistoryList where userAddressMap equals to userAddressMapId
        defaultJobHistoryShouldBeFound("userAddressMapId.equals=" + userAddressMapId);

        // Get all the jobHistoryList where userAddressMap equals to userAddressMapId + 1
        defaultJobHistoryShouldNotBeFound("userAddressMapId.equals=" + (userAddressMapId + 1));
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByJobTimeLogIsEqualToSomething() throws Exception {
        // Initialize the database
        JobTimeLog jobTimeLog = JobTimeLogResourceIntTest.createEntity(em);
        em.persist(jobTimeLog);
        em.flush();
        jobHistory.addJobTimeLog(jobTimeLog);
        jobHistoryRepository.saveAndFlush(jobHistory);
        Long jobTimeLogId = jobTimeLog.getId();

        // Get all the jobHistoryList where jobTimeLog equals to jobTimeLogId
        defaultJobHistoryShouldBeFound("jobTimeLogId.equals=" + jobTimeLogId);

        // Get all the jobHistoryList where jobTimeLog equals to jobTimeLogId + 1
        defaultJobHistoryShouldNotBeFound("jobTimeLogId.equals=" + (jobTimeLogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultJobHistoryShouldBeFound(String filter) throws Exception {
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())));

        // Check, that the count call also returns 1
        restJobHistoryMockMvc.perform(get("/api/job-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultJobHistoryShouldNotBeFound(String filter) throws Exception {
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobHistoryMockMvc.perform(get("/api/job-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findById(jobHistory.getId()).get();
        // Disconnect from session so that the updates on updatedJobHistory are not directly saved in db
        em.detach(updatedJobHistory);
        updatedJobHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .jobStatus(UPDATED_JOB_STATUS);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(updatedJobHistory);

        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobHistory.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);

        // Validate the JobHistory in Elasticsearch
        verify(mockJobHistorySearchRepository, times(1)).save(testJobHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the JobHistory in Elasticsearch
        verify(mockJobHistorySearchRepository, times(0)).save(jobHistory);
    }

    @Test
    @Transactional
    public void deleteJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeDelete = jobHistoryRepository.findAll().size();

        // Delete the jobHistory
        restJobHistoryMockMvc.perform(delete("/api/job-histories/{id}", jobHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the JobHistory in Elasticsearch
        verify(mockJobHistorySearchRepository, times(1)).deleteById(jobHistory.getId());
    }

    @Test
    @Transactional
    public void searchJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);
        when(mockJobHistorySearchRepository.search(queryStringQuery("id:" + jobHistory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(jobHistory), PageRequest.of(0, 1), 1));
        // Search the jobHistory
        restJobHistoryMockMvc.perform(get("/api/_search/job-histories?query=id:" + jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobHistory.class);
        JobHistory jobHistory1 = new JobHistory();
        jobHistory1.setId(1L);
        JobHistory jobHistory2 = new JobHistory();
        jobHistory2.setId(jobHistory1.getId());
        assertThat(jobHistory1).isEqualTo(jobHistory2);
        jobHistory2.setId(2L);
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);
        jobHistory1.setId(null);
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobHistoryDTO.class);
        JobHistoryDTO jobHistoryDTO1 = new JobHistoryDTO();
        jobHistoryDTO1.setId(1L);
        JobHistoryDTO jobHistoryDTO2 = new JobHistoryDTO();
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
        jobHistoryDTO2.setId(jobHistoryDTO1.getId());
        assertThat(jobHistoryDTO1).isEqualTo(jobHistoryDTO2);
        jobHistoryDTO2.setId(2L);
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
        jobHistoryDTO1.setId(null);
        assertThat(jobHistoryDTO1).isNotEqualTo(jobHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobHistoryMapper.fromId(null)).isNull();
    }
}
