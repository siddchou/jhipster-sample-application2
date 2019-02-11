package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.Services;
import io.github.jhipster.application.repository.ServicesRepository;
import io.github.jhipster.application.repository.search.ServicesSearchRepository;
import io.github.jhipster.application.service.ServicesService;
import io.github.jhipster.application.service.dto.ServicesDTO;
import io.github.jhipster.application.service.mapper.ServicesMapper;
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
 * Test class for the ServicesResource REST controller.
 *
 * @see ServicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class ServicesResourceIntTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesMapper servicesMapper;

    @Autowired
    private ServicesService servicesService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ServicesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServicesSearchRepository mockServicesSearchRepository;

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

    private MockMvc restServicesMockMvc;

    private Services services;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicesResource servicesResource = new ServicesResource(servicesService);
        this.restServicesMockMvc = MockMvcBuilders.standaloneSetup(servicesResource)
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
    public static Services createEntity(EntityManager em) {
        Services services = new Services()
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION);
        return services;
    }

    @Before
    public void initTest() {
        services = createEntity(em);
    }

    @Test
    @Transactional
    public void createServices() throws Exception {
        int databaseSizeBeforeCreate = servicesRepository.findAll().size();

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);
        restServicesMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate + 1);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testServices.getServiceDescription()).isEqualTo(DEFAULT_SERVICE_DESCRIPTION);

        // Validate the Services in Elasticsearch
        verify(mockServicesSearchRepository, times(1)).save(testServices);
    }

    @Test
    @Transactional
    public void createServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicesRepository.findAll().size();

        // Create the Services with an existing ID
        services.setId(1L);
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicesMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Services in Elasticsearch
        verify(mockServicesSearchRepository, times(0)).save(services);
    }

    @Test
    @Transactional
    public void getAllServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList
        restServicesMockMvc.perform(get("/api/services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get the services
        restServicesMockMvc.perform(get("/api/services/{id}", services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(services.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServices() throws Exception {
        // Get the services
        restServicesMockMvc.perform(get("/api/services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services
        Services updatedServices = servicesRepository.findById(services.getId()).get();
        // Disconnect from session so that the updates on updatedServices are not directly saved in db
        em.detach(updatedServices);
        updatedServices
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION);
        ServicesDTO servicesDTO = servicesMapper.toDto(updatedServices);

        restServicesMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testServices.getServiceDescription()).isEqualTo(UPDATED_SERVICE_DESCRIPTION);

        // Validate the Services in Elasticsearch
        verify(mockServicesSearchRepository, times(1)).save(testServices);
    }

    @Test
    @Transactional
    public void updateNonExistingServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Create the Services
        ServicesDTO servicesDTO = servicesMapper.toDto(services);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Services in Elasticsearch
        verify(mockServicesSearchRepository, times(0)).save(services);
    }

    @Test
    @Transactional
    public void deleteServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeDelete = servicesRepository.findAll().size();

        // Delete the services
        restServicesMockMvc.perform(delete("/api/services/{id}", services.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Services in Elasticsearch
        verify(mockServicesSearchRepository, times(1)).deleteById(services.getId());
    }

    @Test
    @Transactional
    public void searchServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);
        when(mockServicesSearchRepository.search(queryStringQuery("id:" + services.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(services), PageRequest.of(0, 1), 1));
        // Search the services
        restServicesMockMvc.perform(get("/api/_search/services?query=id:" + services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Services.class);
        Services services1 = new Services();
        services1.setId(1L);
        Services services2 = new Services();
        services2.setId(services1.getId());
        assertThat(services1).isEqualTo(services2);
        services2.setId(2L);
        assertThat(services1).isNotEqualTo(services2);
        services1.setId(null);
        assertThat(services1).isNotEqualTo(services2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicesDTO.class);
        ServicesDTO servicesDTO1 = new ServicesDTO();
        servicesDTO1.setId(1L);
        ServicesDTO servicesDTO2 = new ServicesDTO();
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO2.setId(servicesDTO1.getId());
        assertThat(servicesDTO1).isEqualTo(servicesDTO2);
        servicesDTO2.setId(2L);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO1.setId(null);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servicesMapper.fromId(null)).isNull();
    }
}
