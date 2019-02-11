package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.ContractorService;
import io.github.jhipster.application.domain.Contractor;
import io.github.jhipster.application.domain.Services;
import io.github.jhipster.application.repository.ContractorServiceRepository;
import io.github.jhipster.application.repository.search.ContractorServiceSearchRepository;
import io.github.jhipster.application.service.ContractorServiceService;
import io.github.jhipster.application.service.dto.ContractorServiceDTO;
import io.github.jhipster.application.service.mapper.ContractorServiceMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ContractorServiceCriteria;
import io.github.jhipster.application.service.ContractorServiceQueryService;

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
 * Test class for the ContractorServiceResource REST controller.
 *
 * @see ContractorServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class ContractorServiceResourceIntTest {

    private static final Boolean DEFAULT_IS_VERIFIED = false;
    private static final Boolean UPDATED_IS_VERIFIED = true;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContractorServiceRepository contractorServiceRepository;

    @Autowired
    private ContractorServiceMapper contractorServiceMapper;

    @Autowired
    private ContractorServiceService contractorServiceService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ContractorServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContractorServiceSearchRepository mockContractorServiceSearchRepository;

    @Autowired
    private ContractorServiceQueryService contractorServiceQueryService;

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

    private MockMvc restContractorServiceMockMvc;

    private ContractorService contractorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractorServiceResource contractorServiceResource = new ContractorServiceResource(contractorServiceService, contractorServiceQueryService);
        this.restContractorServiceMockMvc = MockMvcBuilders.standaloneSetup(contractorServiceResource)
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
    public static ContractorService createEntity(EntityManager em) {
        ContractorService contractorService = new ContractorService()
            .isVerified(DEFAULT_IS_VERIFIED)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return contractorService;
    }

    @Before
    public void initTest() {
        contractorService = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractorService() throws Exception {
        int databaseSizeBeforeCreate = contractorServiceRepository.findAll().size();

        // Create the ContractorService
        ContractorServiceDTO contractorServiceDTO = contractorServiceMapper.toDto(contractorService);
        restContractorServiceMockMvc.perform(post("/api/contractor-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the ContractorService in the database
        List<ContractorService> contractorServiceList = contractorServiceRepository.findAll();
        assertThat(contractorServiceList).hasSize(databaseSizeBeforeCreate + 1);
        ContractorService testContractorService = contractorServiceList.get(contractorServiceList.size() - 1);
        assertThat(testContractorService.isIsVerified()).isEqualTo(DEFAULT_IS_VERIFIED);
        assertThat(testContractorService.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContractorService.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the ContractorService in Elasticsearch
        verify(mockContractorServiceSearchRepository, times(1)).save(testContractorService);
    }

    @Test
    @Transactional
    public void createContractorServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractorServiceRepository.findAll().size();

        // Create the ContractorService with an existing ID
        contractorService.setId(1L);
        ContractorServiceDTO contractorServiceDTO = contractorServiceMapper.toDto(contractorService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractorServiceMockMvc.perform(post("/api/contractor-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContractorService in the database
        List<ContractorService> contractorServiceList = contractorServiceRepository.findAll();
        assertThat(contractorServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContractorService in Elasticsearch
        verify(mockContractorServiceSearchRepository, times(0)).save(contractorService);
    }

    @Test
    @Transactional
    public void getAllContractorServices() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList
        restContractorServiceMockMvc.perform(get("/api/contractor-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractorService.getId().intValue())))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getContractorService() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get the contractorService
        restContractorServiceMockMvc.perform(get("/api/contractor-services/{id}", contractorService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractorService.getId().intValue()))
            .andExpect(jsonPath("$.isVerified").value(DEFAULT_IS_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllContractorServicesByIsVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where isVerified equals to DEFAULT_IS_VERIFIED
        defaultContractorServiceShouldBeFound("isVerified.equals=" + DEFAULT_IS_VERIFIED);

        // Get all the contractorServiceList where isVerified equals to UPDATED_IS_VERIFIED
        defaultContractorServiceShouldNotBeFound("isVerified.equals=" + UPDATED_IS_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByIsVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where isVerified in DEFAULT_IS_VERIFIED or UPDATED_IS_VERIFIED
        defaultContractorServiceShouldBeFound("isVerified.in=" + DEFAULT_IS_VERIFIED + "," + UPDATED_IS_VERIFIED);

        // Get all the contractorServiceList where isVerified equals to UPDATED_IS_VERIFIED
        defaultContractorServiceShouldNotBeFound("isVerified.in=" + UPDATED_IS_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByIsVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where isVerified is not null
        defaultContractorServiceShouldBeFound("isVerified.specified=true");

        // Get all the contractorServiceList where isVerified is null
        defaultContractorServiceShouldNotBeFound("isVerified.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorServicesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where startDate equals to DEFAULT_START_DATE
        defaultContractorServiceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the contractorServiceList where startDate equals to UPDATED_START_DATE
        defaultContractorServiceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultContractorServiceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the contractorServiceList where startDate equals to UPDATED_START_DATE
        defaultContractorServiceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where startDate is not null
        defaultContractorServiceShouldBeFound("startDate.specified=true");

        // Get all the contractorServiceList where startDate is null
        defaultContractorServiceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorServicesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where endDate equals to DEFAULT_END_DATE
        defaultContractorServiceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the contractorServiceList where endDate equals to UPDATED_END_DATE
        defaultContractorServiceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultContractorServiceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the contractorServiceList where endDate equals to UPDATED_END_DATE
        defaultContractorServiceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorServicesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        // Get all the contractorServiceList where endDate is not null
        defaultContractorServiceShouldBeFound("endDate.specified=true");

        // Get all the contractorServiceList where endDate is null
        defaultContractorServiceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorServicesByContractorIsEqualToSomething() throws Exception {
        // Initialize the database
        Contractor contractor = ContractorResourceIntTest.createEntity(em);
        em.persist(contractor);
        em.flush();
        contractorService.setContractor(contractor);
        contractorServiceRepository.saveAndFlush(contractorService);
        Long contractorId = contractor.getId();

        // Get all the contractorServiceList where contractor equals to contractorId
        defaultContractorServiceShouldBeFound("contractorId.equals=" + contractorId);

        // Get all the contractorServiceList where contractor equals to contractorId + 1
        defaultContractorServiceShouldNotBeFound("contractorId.equals=" + (contractorId + 1));
    }


    @Test
    @Transactional
    public void getAllContractorServicesByServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        Services services = ServicesResourceIntTest.createEntity(em);
        em.persist(services);
        em.flush();
        contractorService.setServices(services);
        contractorServiceRepository.saveAndFlush(contractorService);
        Long servicesId = services.getId();

        // Get all the contractorServiceList where services equals to servicesId
        defaultContractorServiceShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the contractorServiceList where services equals to servicesId + 1
        defaultContractorServiceShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractorServiceShouldBeFound(String filter) throws Exception {
        restContractorServiceMockMvc.perform(get("/api/contractor-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractorService.getId().intValue())))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restContractorServiceMockMvc.perform(get("/api/contractor-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractorServiceShouldNotBeFound(String filter) throws Exception {
        restContractorServiceMockMvc.perform(get("/api/contractor-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContractorServiceMockMvc.perform(get("/api/contractor-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContractorService() throws Exception {
        // Get the contractorService
        restContractorServiceMockMvc.perform(get("/api/contractor-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractorService() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        int databaseSizeBeforeUpdate = contractorServiceRepository.findAll().size();

        // Update the contractorService
        ContractorService updatedContractorService = contractorServiceRepository.findById(contractorService.getId()).get();
        // Disconnect from session so that the updates on updatedContractorService are not directly saved in db
        em.detach(updatedContractorService);
        updatedContractorService
            .isVerified(UPDATED_IS_VERIFIED)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ContractorServiceDTO contractorServiceDTO = contractorServiceMapper.toDto(updatedContractorService);

        restContractorServiceMockMvc.perform(put("/api/contractor-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorServiceDTO)))
            .andExpect(status().isOk());

        // Validate the ContractorService in the database
        List<ContractorService> contractorServiceList = contractorServiceRepository.findAll();
        assertThat(contractorServiceList).hasSize(databaseSizeBeforeUpdate);
        ContractorService testContractorService = contractorServiceList.get(contractorServiceList.size() - 1);
        assertThat(testContractorService.isIsVerified()).isEqualTo(UPDATED_IS_VERIFIED);
        assertThat(testContractorService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContractorService.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the ContractorService in Elasticsearch
        verify(mockContractorServiceSearchRepository, times(1)).save(testContractorService);
    }

    @Test
    @Transactional
    public void updateNonExistingContractorService() throws Exception {
        int databaseSizeBeforeUpdate = contractorServiceRepository.findAll().size();

        // Create the ContractorService
        ContractorServiceDTO contractorServiceDTO = contractorServiceMapper.toDto(contractorService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractorServiceMockMvc.perform(put("/api/contractor-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContractorService in the database
        List<ContractorService> contractorServiceList = contractorServiceRepository.findAll();
        assertThat(contractorServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContractorService in Elasticsearch
        verify(mockContractorServiceSearchRepository, times(0)).save(contractorService);
    }

    @Test
    @Transactional
    public void deleteContractorService() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);

        int databaseSizeBeforeDelete = contractorServiceRepository.findAll().size();

        // Delete the contractorService
        restContractorServiceMockMvc.perform(delete("/api/contractor-services/{id}", contractorService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractorService> contractorServiceList = contractorServiceRepository.findAll();
        assertThat(contractorServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContractorService in Elasticsearch
        verify(mockContractorServiceSearchRepository, times(1)).deleteById(contractorService.getId());
    }

    @Test
    @Transactional
    public void searchContractorService() throws Exception {
        // Initialize the database
        contractorServiceRepository.saveAndFlush(contractorService);
        when(mockContractorServiceSearchRepository.search(queryStringQuery("id:" + contractorService.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contractorService), PageRequest.of(0, 1), 1));
        // Search the contractorService
        restContractorServiceMockMvc.perform(get("/api/_search/contractor-services?query=id:" + contractorService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractorService.getId().intValue())))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractorService.class);
        ContractorService contractorService1 = new ContractorService();
        contractorService1.setId(1L);
        ContractorService contractorService2 = new ContractorService();
        contractorService2.setId(contractorService1.getId());
        assertThat(contractorService1).isEqualTo(contractorService2);
        contractorService2.setId(2L);
        assertThat(contractorService1).isNotEqualTo(contractorService2);
        contractorService1.setId(null);
        assertThat(contractorService1).isNotEqualTo(contractorService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractorServiceDTO.class);
        ContractorServiceDTO contractorServiceDTO1 = new ContractorServiceDTO();
        contractorServiceDTO1.setId(1L);
        ContractorServiceDTO contractorServiceDTO2 = new ContractorServiceDTO();
        assertThat(contractorServiceDTO1).isNotEqualTo(contractorServiceDTO2);
        contractorServiceDTO2.setId(contractorServiceDTO1.getId());
        assertThat(contractorServiceDTO1).isEqualTo(contractorServiceDTO2);
        contractorServiceDTO2.setId(2L);
        assertThat(contractorServiceDTO1).isNotEqualTo(contractorServiceDTO2);
        contractorServiceDTO1.setId(null);
        assertThat(contractorServiceDTO1).isNotEqualTo(contractorServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contractorServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contractorServiceMapper.fromId(null)).isNull();
    }
}
