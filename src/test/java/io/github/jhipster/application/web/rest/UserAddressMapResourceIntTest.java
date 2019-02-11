package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.UserAddressMap;
import io.github.jhipster.application.repository.UserAddressMapRepository;
import io.github.jhipster.application.repository.search.UserAddressMapSearchRepository;
import io.github.jhipster.application.service.UserAddressMapService;
import io.github.jhipster.application.service.dto.UserAddressMapDTO;
import io.github.jhipster.application.service.mapper.UserAddressMapMapper;
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
 * Test class for the UserAddressMapResource REST controller.
 *
 * @see UserAddressMapResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class UserAddressMapResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserAddressMapRepository userAddressMapRepository;

    @Autowired
    private UserAddressMapMapper userAddressMapMapper;

    @Autowired
    private UserAddressMapService userAddressMapService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.UserAddressMapSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserAddressMapSearchRepository mockUserAddressMapSearchRepository;

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

    private MockMvc restUserAddressMapMockMvc;

    private UserAddressMap userAddressMap;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAddressMapResource userAddressMapResource = new UserAddressMapResource(userAddressMapService);
        this.restUserAddressMapMockMvc = MockMvcBuilders.standaloneSetup(userAddressMapResource)
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
    public static UserAddressMap createEntity(EntityManager em) {
        UserAddressMap userAddressMap = new UserAddressMap()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return userAddressMap;
    }

    @Before
    public void initTest() {
        userAddressMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAddressMap() throws Exception {
        int databaseSizeBeforeCreate = userAddressMapRepository.findAll().size();

        // Create the UserAddressMap
        UserAddressMapDTO userAddressMapDTO = userAddressMapMapper.toDto(userAddressMap);
        restUserAddressMapMockMvc.perform(post("/api/user-address-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressMapDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAddressMap in the database
        List<UserAddressMap> userAddressMapList = userAddressMapRepository.findAll();
        assertThat(userAddressMapList).hasSize(databaseSizeBeforeCreate + 1);
        UserAddressMap testUserAddressMap = userAddressMapList.get(userAddressMapList.size() - 1);
        assertThat(testUserAddressMap.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testUserAddressMap.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the UserAddressMap in Elasticsearch
        verify(mockUserAddressMapSearchRepository, times(1)).save(testUserAddressMap);
    }

    @Test
    @Transactional
    public void createUserAddressMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAddressMapRepository.findAll().size();

        // Create the UserAddressMap with an existing ID
        userAddressMap.setId(1L);
        UserAddressMapDTO userAddressMapDTO = userAddressMapMapper.toDto(userAddressMap);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAddressMapMockMvc.perform(post("/api/user-address-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddressMap in the database
        List<UserAddressMap> userAddressMapList = userAddressMapRepository.findAll();
        assertThat(userAddressMapList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserAddressMap in Elasticsearch
        verify(mockUserAddressMapSearchRepository, times(0)).save(userAddressMap);
    }

    @Test
    @Transactional
    public void getAllUserAddressMaps() throws Exception {
        // Initialize the database
        userAddressMapRepository.saveAndFlush(userAddressMap);

        // Get all the userAddressMapList
        restUserAddressMapMockMvc.perform(get("/api/user-address-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddressMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserAddressMap() throws Exception {
        // Initialize the database
        userAddressMapRepository.saveAndFlush(userAddressMap);

        // Get the userAddressMap
        restUserAddressMapMockMvc.perform(get("/api/user-address-maps/{id}", userAddressMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAddressMap.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAddressMap() throws Exception {
        // Get the userAddressMap
        restUserAddressMapMockMvc.perform(get("/api/user-address-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAddressMap() throws Exception {
        // Initialize the database
        userAddressMapRepository.saveAndFlush(userAddressMap);

        int databaseSizeBeforeUpdate = userAddressMapRepository.findAll().size();

        // Update the userAddressMap
        UserAddressMap updatedUserAddressMap = userAddressMapRepository.findById(userAddressMap.getId()).get();
        // Disconnect from session so that the updates on updatedUserAddressMap are not directly saved in db
        em.detach(updatedUserAddressMap);
        updatedUserAddressMap
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        UserAddressMapDTO userAddressMapDTO = userAddressMapMapper.toDto(updatedUserAddressMap);

        restUserAddressMapMockMvc.perform(put("/api/user-address-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressMapDTO)))
            .andExpect(status().isOk());

        // Validate the UserAddressMap in the database
        List<UserAddressMap> userAddressMapList = userAddressMapRepository.findAll();
        assertThat(userAddressMapList).hasSize(databaseSizeBeforeUpdate);
        UserAddressMap testUserAddressMap = userAddressMapList.get(userAddressMapList.size() - 1);
        assertThat(testUserAddressMap.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testUserAddressMap.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the UserAddressMap in Elasticsearch
        verify(mockUserAddressMapSearchRepository, times(1)).save(testUserAddressMap);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAddressMap() throws Exception {
        int databaseSizeBeforeUpdate = userAddressMapRepository.findAll().size();

        // Create the UserAddressMap
        UserAddressMapDTO userAddressMapDTO = userAddressMapMapper.toDto(userAddressMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAddressMapMockMvc.perform(put("/api/user-address-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddressMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddressMap in the database
        List<UserAddressMap> userAddressMapList = userAddressMapRepository.findAll();
        assertThat(userAddressMapList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserAddressMap in Elasticsearch
        verify(mockUserAddressMapSearchRepository, times(0)).save(userAddressMap);
    }

    @Test
    @Transactional
    public void deleteUserAddressMap() throws Exception {
        // Initialize the database
        userAddressMapRepository.saveAndFlush(userAddressMap);

        int databaseSizeBeforeDelete = userAddressMapRepository.findAll().size();

        // Delete the userAddressMap
        restUserAddressMapMockMvc.perform(delete("/api/user-address-maps/{id}", userAddressMap.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAddressMap> userAddressMapList = userAddressMapRepository.findAll();
        assertThat(userAddressMapList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserAddressMap in Elasticsearch
        verify(mockUserAddressMapSearchRepository, times(1)).deleteById(userAddressMap.getId());
    }

    @Test
    @Transactional
    public void searchUserAddressMap() throws Exception {
        // Initialize the database
        userAddressMapRepository.saveAndFlush(userAddressMap);
        when(mockUserAddressMapSearchRepository.search(queryStringQuery("id:" + userAddressMap.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userAddressMap), PageRequest.of(0, 1), 1));
        // Search the userAddressMap
        restUserAddressMapMockMvc.perform(get("/api/_search/user-address-maps?query=id:" + userAddressMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddressMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddressMap.class);
        UserAddressMap userAddressMap1 = new UserAddressMap();
        userAddressMap1.setId(1L);
        UserAddressMap userAddressMap2 = new UserAddressMap();
        userAddressMap2.setId(userAddressMap1.getId());
        assertThat(userAddressMap1).isEqualTo(userAddressMap2);
        userAddressMap2.setId(2L);
        assertThat(userAddressMap1).isNotEqualTo(userAddressMap2);
        userAddressMap1.setId(null);
        assertThat(userAddressMap1).isNotEqualTo(userAddressMap2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddressMapDTO.class);
        UserAddressMapDTO userAddressMapDTO1 = new UserAddressMapDTO();
        userAddressMapDTO1.setId(1L);
        UserAddressMapDTO userAddressMapDTO2 = new UserAddressMapDTO();
        assertThat(userAddressMapDTO1).isNotEqualTo(userAddressMapDTO2);
        userAddressMapDTO2.setId(userAddressMapDTO1.getId());
        assertThat(userAddressMapDTO1).isEqualTo(userAddressMapDTO2);
        userAddressMapDTO2.setId(2L);
        assertThat(userAddressMapDTO1).isNotEqualTo(userAddressMapDTO2);
        userAddressMapDTO1.setId(null);
        assertThat(userAddressMapDTO1).isNotEqualTo(userAddressMapDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAddressMapMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAddressMapMapper.fromId(null)).isNull();
    }
}
