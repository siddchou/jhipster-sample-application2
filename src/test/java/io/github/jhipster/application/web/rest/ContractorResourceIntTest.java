package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.Contractor;
import io.github.jhipster.application.repository.ContractorRepository;
import io.github.jhipster.application.repository.search.ContractorSearchRepository;
import io.github.jhipster.application.service.ContractorService;
import io.github.jhipster.application.service.dto.ContractorDTO;
import io.github.jhipster.application.service.mapper.ContractorMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ContractorCriteria;
import io.github.jhipster.application.service.ContractorQueryService;

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
 * Test class for the ContractorResource REST controller.
 *
 * @see ContractorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class ContractorResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ContractorMapper contractorMapper;

    @Autowired
    private ContractorService contractorService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ContractorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContractorSearchRepository mockContractorSearchRepository;

    @Autowired
    private ContractorQueryService contractorQueryService;

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

    private MockMvc restContractorMockMvc;

    private Contractor contractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractorResource contractorResource = new ContractorResource(contractorService, contractorQueryService);
        this.restContractorMockMvc = MockMvcBuilders.standaloneSetup(contractorResource)
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
    public static Contractor createEntity(EntityManager em) {
        Contractor contractor = new Contractor()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return contractor;
    }

    @Before
    public void initTest() {
        contractor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractor() throws Exception {
        int databaseSizeBeforeCreate = contractorRepository.findAll().size();

        // Create the Contractor
        ContractorDTO contractorDTO = contractorMapper.toDto(contractor);
        restContractorMockMvc.perform(post("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorDTO)))
            .andExpect(status().isCreated());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeCreate + 1);
        Contractor testContractor = contractorList.get(contractorList.size() - 1);
        assertThat(testContractor.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContractor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContractor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContractor.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testContractor.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testContractor.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContractor.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the Contractor in Elasticsearch
        verify(mockContractorSearchRepository, times(1)).save(testContractor);
    }

    @Test
    @Transactional
    public void createContractorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractorRepository.findAll().size();

        // Create the Contractor with an existing ID
        contractor.setId(1L);
        ContractorDTO contractorDTO = contractorMapper.toDto(contractor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractorMockMvc.perform(post("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Contractor in Elasticsearch
        verify(mockContractorSearchRepository, times(0)).save(contractor);
    }

    @Test
    @Transactional
    public void getAllContractors() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList
        restContractorMockMvc.perform(get("/api/contractors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractor.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get the contractor
        restContractorMockMvc.perform(get("/api/contractors/{id}", contractor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractor.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllContractorsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where firstName equals to DEFAULT_FIRST_NAME
        defaultContractorShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the contractorList where firstName equals to UPDATED_FIRST_NAME
        defaultContractorShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllContractorsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultContractorShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the contractorList where firstName equals to UPDATED_FIRST_NAME
        defaultContractorShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllContractorsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where firstName is not null
        defaultContractorShouldBeFound("firstName.specified=true");

        // Get all the contractorList where firstName is null
        defaultContractorShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where lastName equals to DEFAULT_LAST_NAME
        defaultContractorShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the contractorList where lastName equals to UPDATED_LAST_NAME
        defaultContractorShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllContractorsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultContractorShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the contractorList where lastName equals to UPDATED_LAST_NAME
        defaultContractorShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllContractorsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where lastName is not null
        defaultContractorShouldBeFound("lastName.specified=true");

        // Get all the contractorList where lastName is null
        defaultContractorShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where email equals to DEFAULT_EMAIL
        defaultContractorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contractorList where email equals to UPDATED_EMAIL
        defaultContractorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContractorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContractorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contractorList where email equals to UPDATED_EMAIL
        defaultContractorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContractorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where email is not null
        defaultContractorShouldBeFound("email.specified=true");

        // Get all the contractorList where email is null
        defaultContractorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultContractorShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the contractorList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultContractorShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllContractorsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultContractorShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the contractorList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultContractorShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllContractorsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where phoneNumber is not null
        defaultContractorShouldBeFound("phoneNumber.specified=true");

        // Get all the contractorList where phoneNumber is null
        defaultContractorShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByHireDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where hireDate equals to DEFAULT_HIRE_DATE
        defaultContractorShouldBeFound("hireDate.equals=" + DEFAULT_HIRE_DATE);

        // Get all the contractorList where hireDate equals to UPDATED_HIRE_DATE
        defaultContractorShouldNotBeFound("hireDate.equals=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByHireDateIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where hireDate in DEFAULT_HIRE_DATE or UPDATED_HIRE_DATE
        defaultContractorShouldBeFound("hireDate.in=" + DEFAULT_HIRE_DATE + "," + UPDATED_HIRE_DATE);

        // Get all the contractorList where hireDate equals to UPDATED_HIRE_DATE
        defaultContractorShouldNotBeFound("hireDate.in=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByHireDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where hireDate is not null
        defaultContractorShouldBeFound("hireDate.specified=true");

        // Get all the contractorList where hireDate is null
        defaultContractorShouldNotBeFound("hireDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where startDate equals to DEFAULT_START_DATE
        defaultContractorShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the contractorList where startDate equals to UPDATED_START_DATE
        defaultContractorShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultContractorShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the contractorList where startDate equals to UPDATED_START_DATE
        defaultContractorShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where startDate is not null
        defaultContractorShouldBeFound("startDate.specified=true");

        // Get all the contractorList where startDate is null
        defaultContractorShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractorsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where endDate equals to DEFAULT_END_DATE
        defaultContractorShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the contractorList where endDate equals to UPDATED_END_DATE
        defaultContractorShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultContractorShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the contractorList where endDate equals to UPDATED_END_DATE
        defaultContractorShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllContractorsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList where endDate is not null
        defaultContractorShouldBeFound("endDate.specified=true");

        // Get all the contractorList where endDate is null
        defaultContractorShouldNotBeFound("endDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractorShouldBeFound(String filter) throws Exception {
        restContractorMockMvc.perform(get("/api/contractors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractor.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restContractorMockMvc.perform(get("/api/contractors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractorShouldNotBeFound(String filter) throws Exception {
        restContractorMockMvc.perform(get("/api/contractors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContractorMockMvc.perform(get("/api/contractors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContractor() throws Exception {
        // Get the contractor
        restContractorMockMvc.perform(get("/api/contractors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        int databaseSizeBeforeUpdate = contractorRepository.findAll().size();

        // Update the contractor
        Contractor updatedContractor = contractorRepository.findById(contractor.getId()).get();
        // Disconnect from session so that the updates on updatedContractor are not directly saved in db
        em.detach(updatedContractor);
        updatedContractor
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ContractorDTO contractorDTO = contractorMapper.toDto(updatedContractor);

        restContractorMockMvc.perform(put("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorDTO)))
            .andExpect(status().isOk());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeUpdate);
        Contractor testContractor = contractorList.get(contractorList.size() - 1);
        assertThat(testContractor.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContractor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContractor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContractor.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testContractor.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testContractor.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContractor.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the Contractor in Elasticsearch
        verify(mockContractorSearchRepository, times(1)).save(testContractor);
    }

    @Test
    @Transactional
    public void updateNonExistingContractor() throws Exception {
        int databaseSizeBeforeUpdate = contractorRepository.findAll().size();

        // Create the Contractor
        ContractorDTO contractorDTO = contractorMapper.toDto(contractor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractorMockMvc.perform(put("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Contractor in Elasticsearch
        verify(mockContractorSearchRepository, times(0)).save(contractor);
    }

    @Test
    @Transactional
    public void deleteContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        int databaseSizeBeforeDelete = contractorRepository.findAll().size();

        // Delete the contractor
        restContractorMockMvc.perform(delete("/api/contractors/{id}", contractor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Contractor in Elasticsearch
        verify(mockContractorSearchRepository, times(1)).deleteById(contractor.getId());
    }

    @Test
    @Transactional
    public void searchContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);
        when(mockContractorSearchRepository.search(queryStringQuery("id:" + contractor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contractor), PageRequest.of(0, 1), 1));
        // Search the contractor
        restContractorMockMvc.perform(get("/api/_search/contractors?query=id:" + contractor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractor.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contractor.class);
        Contractor contractor1 = new Contractor();
        contractor1.setId(1L);
        Contractor contractor2 = new Contractor();
        contractor2.setId(contractor1.getId());
        assertThat(contractor1).isEqualTo(contractor2);
        contractor2.setId(2L);
        assertThat(contractor1).isNotEqualTo(contractor2);
        contractor1.setId(null);
        assertThat(contractor1).isNotEqualTo(contractor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractorDTO.class);
        ContractorDTO contractorDTO1 = new ContractorDTO();
        contractorDTO1.setId(1L);
        ContractorDTO contractorDTO2 = new ContractorDTO();
        assertThat(contractorDTO1).isNotEqualTo(contractorDTO2);
        contractorDTO2.setId(contractorDTO1.getId());
        assertThat(contractorDTO1).isEqualTo(contractorDTO2);
        contractorDTO2.setId(2L);
        assertThat(contractorDTO1).isNotEqualTo(contractorDTO2);
        contractorDTO1.setId(null);
        assertThat(contractorDTO1).isNotEqualTo(contractorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contractorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contractorMapper.fromId(null)).isNull();
    }
}
