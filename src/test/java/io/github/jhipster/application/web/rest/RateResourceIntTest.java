package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.Rate;
import io.github.jhipster.application.repository.RateRepository;
import io.github.jhipster.application.repository.search.RateSearchRepository;
import io.github.jhipster.application.service.RateService;
import io.github.jhipster.application.service.dto.RateDTO;
import io.github.jhipster.application.service.mapper.RateMapper;
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
        final RateResource rateResource = new RateResource(rateService);
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
