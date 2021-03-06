package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.Rate;
import io.github.jhipster.application.repository.RateRepository;
import io.github.jhipster.application.repository.search.RateSearchRepository;
import io.github.jhipster.application.service.RateService;
import io.github.jhipster.application.service.dto.RateDTO;
import io.github.jhipster.application.service.mapper.RateMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.RateCriteria;
import io.github.jhipster.application.service.RateQueryService;

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
 * Test class for the RateResource REST controller.
 *
 * @see RateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class RateResourceIntTest {

    private static final String DEFAULT_RATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_RATE_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_FULL_RATE = 1;
    private static final Integer UPDATED_FULL_RATE = 2;

    private static final Integer DEFAULT_IDLE_RATE = 1;
    private static final Integer UPDATED_IDLE_RATE = 2;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateMapper rateMapper;

    @Autowired
    private RateService rateService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RateSearchRepositoryMockConfiguration
     */
    @Autowired
    private RateSearchRepository mockRateSearchRepository;

    @Autowired
    private RateQueryService rateQueryService;

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

    private MockMvc restRateMockMvc;

    private Rate rate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RateResource rateResource = new RateResource(rateService, rateQueryService);
        this.restRateMockMvc = MockMvcBuilders.standaloneSetup(rateResource)
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
    public static Rate createEntity(EntityManager em) {
        Rate rate = new Rate()
            .rateName(DEFAULT_RATE_NAME)
            .rateDesc(DEFAULT_RATE_DESC)
            .fullRate(DEFAULT_FULL_RATE)
            .idleRate(DEFAULT_IDLE_RATE);
        return rate;
    }

    @Before
    public void initTest() {
        rate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRate() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate
        RateDTO rateDTO = rateMapper.toDto(rate);
        restRateMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isCreated());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate + 1);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getRateName()).isEqualTo(DEFAULT_RATE_NAME);
        assertThat(testRate.getRateDesc()).isEqualTo(DEFAULT_RATE_DESC);
        assertThat(testRate.getFullRate()).isEqualTo(DEFAULT_FULL_RATE);
        assertThat(testRate.getIdleRate()).isEqualTo(DEFAULT_IDLE_RATE);

        // Validate the Rate in Elasticsearch
        verify(mockRateSearchRepository, times(1)).save(testRate);
    }

    @Test
    @Transactional
    public void createRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate with an existing ID
        rate.setId(1L);
        RateDTO rateDTO = rateMapper.toDto(rate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rate in Elasticsearch
        verify(mockRateSearchRepository, times(0)).save(rate);
    }

    @Test
    @Transactional
    public void getAllRates() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList
        restRateMockMvc.perform(get("/api/rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateName").value(hasItem(DEFAULT_RATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].rateDesc").value(hasItem(DEFAULT_RATE_DESC.toString())))
            .andExpect(jsonPath("$.[*].fullRate").value(hasItem(DEFAULT_FULL_RATE)))
            .andExpect(jsonPath("$.[*].idleRate").value(hasItem(DEFAULT_IDLE_RATE)));
    }
    
    @Test
    @Transactional
    public void getRate() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", rate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rate.getId().intValue()))
            .andExpect(jsonPath("$.rateName").value(DEFAULT_RATE_NAME.toString()))
            .andExpect(jsonPath("$.rateDesc").value(DEFAULT_RATE_DESC.toString()))
            .andExpect(jsonPath("$.fullRate").value(DEFAULT_FULL_RATE))
            .andExpect(jsonPath("$.idleRate").value(DEFAULT_IDLE_RATE));
    }

    @Test
    @Transactional
    public void getAllRatesByRateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateName equals to DEFAULT_RATE_NAME
        defaultRateShouldBeFound("rateName.equals=" + DEFAULT_RATE_NAME);

        // Get all the rateList where rateName equals to UPDATED_RATE_NAME
        defaultRateShouldNotBeFound("rateName.equals=" + UPDATED_RATE_NAME);
    }

    @Test
    @Transactional
    public void getAllRatesByRateNameIsInShouldWork() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateName in DEFAULT_RATE_NAME or UPDATED_RATE_NAME
        defaultRateShouldBeFound("rateName.in=" + DEFAULT_RATE_NAME + "," + UPDATED_RATE_NAME);

        // Get all the rateList where rateName equals to UPDATED_RATE_NAME
        defaultRateShouldNotBeFound("rateName.in=" + UPDATED_RATE_NAME);
    }

    @Test
    @Transactional
    public void getAllRatesByRateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateName is not null
        defaultRateShouldBeFound("rateName.specified=true");

        // Get all the rateList where rateName is null
        defaultRateShouldNotBeFound("rateName.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatesByRateDescIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateDesc equals to DEFAULT_RATE_DESC
        defaultRateShouldBeFound("rateDesc.equals=" + DEFAULT_RATE_DESC);

        // Get all the rateList where rateDesc equals to UPDATED_RATE_DESC
        defaultRateShouldNotBeFound("rateDesc.equals=" + UPDATED_RATE_DESC);
    }

    @Test
    @Transactional
    public void getAllRatesByRateDescIsInShouldWork() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateDesc in DEFAULT_RATE_DESC or UPDATED_RATE_DESC
        defaultRateShouldBeFound("rateDesc.in=" + DEFAULT_RATE_DESC + "," + UPDATED_RATE_DESC);

        // Get all the rateList where rateDesc equals to UPDATED_RATE_DESC
        defaultRateShouldNotBeFound("rateDesc.in=" + UPDATED_RATE_DESC);
    }

    @Test
    @Transactional
    public void getAllRatesByRateDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where rateDesc is not null
        defaultRateShouldBeFound("rateDesc.specified=true");

        // Get all the rateList where rateDesc is null
        defaultRateShouldNotBeFound("rateDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatesByFullRateIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where fullRate equals to DEFAULT_FULL_RATE
        defaultRateShouldBeFound("fullRate.equals=" + DEFAULT_FULL_RATE);

        // Get all the rateList where fullRate equals to UPDATED_FULL_RATE
        defaultRateShouldNotBeFound("fullRate.equals=" + UPDATED_FULL_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByFullRateIsInShouldWork() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where fullRate in DEFAULT_FULL_RATE or UPDATED_FULL_RATE
        defaultRateShouldBeFound("fullRate.in=" + DEFAULT_FULL_RATE + "," + UPDATED_FULL_RATE);

        // Get all the rateList where fullRate equals to UPDATED_FULL_RATE
        defaultRateShouldNotBeFound("fullRate.in=" + UPDATED_FULL_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByFullRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where fullRate is not null
        defaultRateShouldBeFound("fullRate.specified=true");

        // Get all the rateList where fullRate is null
        defaultRateShouldNotBeFound("fullRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatesByFullRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where fullRate greater than or equals to DEFAULT_FULL_RATE
        defaultRateShouldBeFound("fullRate.greaterOrEqualThan=" + DEFAULT_FULL_RATE);

        // Get all the rateList where fullRate greater than or equals to UPDATED_FULL_RATE
        defaultRateShouldNotBeFound("fullRate.greaterOrEqualThan=" + UPDATED_FULL_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByFullRateIsLessThanSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where fullRate less than or equals to DEFAULT_FULL_RATE
        defaultRateShouldNotBeFound("fullRate.lessThan=" + DEFAULT_FULL_RATE);

        // Get all the rateList where fullRate less than or equals to UPDATED_FULL_RATE
        defaultRateShouldBeFound("fullRate.lessThan=" + UPDATED_FULL_RATE);
    }


    @Test
    @Transactional
    public void getAllRatesByIdleRateIsEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where idleRate equals to DEFAULT_IDLE_RATE
        defaultRateShouldBeFound("idleRate.equals=" + DEFAULT_IDLE_RATE);

        // Get all the rateList where idleRate equals to UPDATED_IDLE_RATE
        defaultRateShouldNotBeFound("idleRate.equals=" + UPDATED_IDLE_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByIdleRateIsInShouldWork() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where idleRate in DEFAULT_IDLE_RATE or UPDATED_IDLE_RATE
        defaultRateShouldBeFound("idleRate.in=" + DEFAULT_IDLE_RATE + "," + UPDATED_IDLE_RATE);

        // Get all the rateList where idleRate equals to UPDATED_IDLE_RATE
        defaultRateShouldNotBeFound("idleRate.in=" + UPDATED_IDLE_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByIdleRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where idleRate is not null
        defaultRateShouldBeFound("idleRate.specified=true");

        // Get all the rateList where idleRate is null
        defaultRateShouldNotBeFound("idleRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatesByIdleRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where idleRate greater than or equals to DEFAULT_IDLE_RATE
        defaultRateShouldBeFound("idleRate.greaterOrEqualThan=" + DEFAULT_IDLE_RATE);

        // Get all the rateList where idleRate greater than or equals to UPDATED_IDLE_RATE
        defaultRateShouldNotBeFound("idleRate.greaterOrEqualThan=" + UPDATED_IDLE_RATE);
    }

    @Test
    @Transactional
    public void getAllRatesByIdleRateIsLessThanSomething() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        // Get all the rateList where idleRate less than or equals to DEFAULT_IDLE_RATE
        defaultRateShouldNotBeFound("idleRate.lessThan=" + DEFAULT_IDLE_RATE);

        // Get all the rateList where idleRate less than or equals to UPDATED_IDLE_RATE
        defaultRateShouldBeFound("idleRate.lessThan=" + UPDATED_IDLE_RATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRateShouldBeFound(String filter) throws Exception {
        restRateMockMvc.perform(get("/api/rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateName").value(hasItem(DEFAULT_RATE_NAME)))
            .andExpect(jsonPath("$.[*].rateDesc").value(hasItem(DEFAULT_RATE_DESC)))
            .andExpect(jsonPath("$.[*].fullRate").value(hasItem(DEFAULT_FULL_RATE)))
            .andExpect(jsonPath("$.[*].idleRate").value(hasItem(DEFAULT_IDLE_RATE)));

        // Check, that the count call also returns 1
        restRateMockMvc.perform(get("/api/rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRateShouldNotBeFound(String filter) throws Exception {
        restRateMockMvc.perform(get("/api/rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRateMockMvc.perform(get("/api/rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRate() throws Exception {
        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRate() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Update the rate
        Rate updatedRate = rateRepository.findById(rate.getId()).get();
        // Disconnect from session so that the updates on updatedRate are not directly saved in db
        em.detach(updatedRate);
        updatedRate
            .rateName(UPDATED_RATE_NAME)
            .rateDesc(UPDATED_RATE_DESC)
            .fullRate(UPDATED_FULL_RATE)
            .idleRate(UPDATED_IDLE_RATE);
        RateDTO rateDTO = rateMapper.toDto(updatedRate);

        restRateMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isOk());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getRateName()).isEqualTo(UPDATED_RATE_NAME);
        assertThat(testRate.getRateDesc()).isEqualTo(UPDATED_RATE_DESC);
        assertThat(testRate.getFullRate()).isEqualTo(UPDATED_FULL_RATE);
        assertThat(testRate.getIdleRate()).isEqualTo(UPDATED_IDLE_RATE);

        // Validate the Rate in Elasticsearch
        verify(mockRateSearchRepository, times(1)).save(testRate);
    }

    @Test
    @Transactional
    public void updateNonExistingRate() throws Exception {
        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Create the Rate
        RateDTO rateDTO = rateMapper.toDto(rate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRateMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rate in Elasticsearch
        verify(mockRateSearchRepository, times(0)).save(rate);
    }

    @Test
    @Transactional
    public void deleteRate() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);

        int databaseSizeBeforeDelete = rateRepository.findAll().size();

        // Delete the rate
        restRateMockMvc.perform(delete("/api/rates/{id}", rate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rate in Elasticsearch
        verify(mockRateSearchRepository, times(1)).deleteById(rate.getId());
    }

    @Test
    @Transactional
    public void searchRate() throws Exception {
        // Initialize the database
        rateRepository.saveAndFlush(rate);
        when(mockRateSearchRepository.search(queryStringQuery("id:" + rate.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rate), PageRequest.of(0, 1), 1));
        // Search the rate
        restRateMockMvc.perform(get("/api/_search/rates?query=id:" + rate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateName").value(hasItem(DEFAULT_RATE_NAME)))
            .andExpect(jsonPath("$.[*].rateDesc").value(hasItem(DEFAULT_RATE_DESC)))
            .andExpect(jsonPath("$.[*].fullRate").value(hasItem(DEFAULT_FULL_RATE)))
            .andExpect(jsonPath("$.[*].idleRate").value(hasItem(DEFAULT_IDLE_RATE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rate.class);
        Rate rate1 = new Rate();
        rate1.setId(1L);
        Rate rate2 = new Rate();
        rate2.setId(rate1.getId());
        assertThat(rate1).isEqualTo(rate2);
        rate2.setId(2L);
        assertThat(rate1).isNotEqualTo(rate2);
        rate1.setId(null);
        assertThat(rate1).isNotEqualTo(rate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateDTO.class);
        RateDTO rateDTO1 = new RateDTO();
        rateDTO1.setId(1L);
        RateDTO rateDTO2 = new RateDTO();
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
        rateDTO2.setId(rateDTO1.getId());
        assertThat(rateDTO1).isEqualTo(rateDTO2);
        rateDTO2.setId(2L);
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
        rateDTO1.setId(null);
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rateMapper.fromId(null)).isNull();
    }
}
