package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplication2App;

import io.github.jhipster.application.domain.Payment;
import io.github.jhipster.application.repository.PaymentRepository;
import io.github.jhipster.application.repository.search.PaymentSearchRepository;
import io.github.jhipster.application.service.PaymentService;
import io.github.jhipster.application.service.dto.PaymentDTO;
import io.github.jhipster.application.service.mapper.PaymentMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.PaymentCriteria;
import io.github.jhipster.application.service.PaymentQueryService;

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

import io.github.jhipster.application.domain.enumeration.PaymentType;
/**
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplication2App.class)
public class PaymentResourceIntTest {

    private static final Double DEFAULT_TOTAL_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_PAYMENT_AMOUNT = 2D;

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.DEBIT;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.CREDIT;

    private static final Double DEFAULT_CONTRACTOR_AMOUNT = 1D;
    private static final Double UPDATED_CONTRACTOR_AMOUNT = 2D;

    private static final Double DEFAULT_BUSINESS_AMOUNT = 1D;
    private static final Double UPDATED_BUSINESS_AMOUNT = 2D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentService paymentService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PaymentSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentSearchRepository mockPaymentSearchRepository;

    @Autowired
    private PaymentQueryService paymentQueryService;

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

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentResource paymentResource = new PaymentResource(paymentService, paymentQueryService);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
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
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .totalPaymentAmount(DEFAULT_TOTAL_PAYMENT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .contractorAmount(DEFAULT_CONTRACTOR_AMOUNT)
            .businessAmount(DEFAULT_BUSINESS_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return payment;
    }

    @Before
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getTotalPaymentAmount()).isEqualTo(DEFAULT_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPayment.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPayment.getContractorAmount()).isEqualTo(DEFAULT_CONTRACTOR_AMOUNT);
        assertThat(testPayment.getBusinessAmount()).isEqualTo(DEFAULT_BUSINESS_AMOUNT);
        assertThat(testPayment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPayment.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).save(testPayment);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(0)).save(payment);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPaymentAmount").value(hasItem(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contractorAmount").value(hasItem(DEFAULT_CONTRACTOR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].businessAmount").value(hasItem(DEFAULT_BUSINESS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.totalPaymentAmount").value(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.contractorAmount").value(DEFAULT_CONTRACTOR_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.businessAmount").value(DEFAULT_BUSINESS_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPaymentsByTotalPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where totalPaymentAmount equals to DEFAULT_TOTAL_PAYMENT_AMOUNT
        defaultPaymentShouldBeFound("totalPaymentAmount.equals=" + DEFAULT_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentList where totalPaymentAmount equals to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentShouldNotBeFound("totalPaymentAmount.equals=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTotalPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where totalPaymentAmount in DEFAULT_TOTAL_PAYMENT_AMOUNT or UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentShouldBeFound("totalPaymentAmount.in=" + DEFAULT_TOTAL_PAYMENT_AMOUNT + "," + UPDATED_TOTAL_PAYMENT_AMOUNT);

        // Get all the paymentList where totalPaymentAmount equals to UPDATED_TOTAL_PAYMENT_AMOUNT
        defaultPaymentShouldNotBeFound("totalPaymentAmount.in=" + UPDATED_TOTAL_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTotalPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where totalPaymentAmount is not null
        defaultPaymentShouldBeFound("totalPaymentAmount.specified=true");

        // Get all the paymentList where totalPaymentAmount is null
        defaultPaymentShouldNotBeFound("totalPaymentAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultPaymentShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the paymentList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPaymentShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultPaymentShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the paymentList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPaymentShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentType is not null
        defaultPaymentShouldBeFound("paymentType.specified=true");

        // Get all the paymentList where paymentType is null
        defaultPaymentShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByContractorAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where contractorAmount equals to DEFAULT_CONTRACTOR_AMOUNT
        defaultPaymentShouldBeFound("contractorAmount.equals=" + DEFAULT_CONTRACTOR_AMOUNT);

        // Get all the paymentList where contractorAmount equals to UPDATED_CONTRACTOR_AMOUNT
        defaultPaymentShouldNotBeFound("contractorAmount.equals=" + UPDATED_CONTRACTOR_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByContractorAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where contractorAmount in DEFAULT_CONTRACTOR_AMOUNT or UPDATED_CONTRACTOR_AMOUNT
        defaultPaymentShouldBeFound("contractorAmount.in=" + DEFAULT_CONTRACTOR_AMOUNT + "," + UPDATED_CONTRACTOR_AMOUNT);

        // Get all the paymentList where contractorAmount equals to UPDATED_CONTRACTOR_AMOUNT
        defaultPaymentShouldNotBeFound("contractorAmount.in=" + UPDATED_CONTRACTOR_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByContractorAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where contractorAmount is not null
        defaultPaymentShouldBeFound("contractorAmount.specified=true");

        // Get all the paymentList where contractorAmount is null
        defaultPaymentShouldNotBeFound("contractorAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByBusinessAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where businessAmount equals to DEFAULT_BUSINESS_AMOUNT
        defaultPaymentShouldBeFound("businessAmount.equals=" + DEFAULT_BUSINESS_AMOUNT);

        // Get all the paymentList where businessAmount equals to UPDATED_BUSINESS_AMOUNT
        defaultPaymentShouldNotBeFound("businessAmount.equals=" + UPDATED_BUSINESS_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBusinessAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where businessAmount in DEFAULT_BUSINESS_AMOUNT or UPDATED_BUSINESS_AMOUNT
        defaultPaymentShouldBeFound("businessAmount.in=" + DEFAULT_BUSINESS_AMOUNT + "," + UPDATED_BUSINESS_AMOUNT);

        // Get all the paymentList where businessAmount equals to UPDATED_BUSINESS_AMOUNT
        defaultPaymentShouldNotBeFound("businessAmount.in=" + UPDATED_BUSINESS_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByBusinessAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where businessAmount is not null
        defaultPaymentShouldBeFound("businessAmount.specified=true");

        // Get all the paymentList where businessAmount is null
        defaultPaymentShouldNotBeFound("businessAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where startDate equals to DEFAULT_START_DATE
        defaultPaymentShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the paymentList where startDate equals to UPDATED_START_DATE
        defaultPaymentShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultPaymentShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the paymentList where startDate equals to UPDATED_START_DATE
        defaultPaymentShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where startDate is not null
        defaultPaymentShouldBeFound("startDate.specified=true");

        // Get all the paymentList where startDate is null
        defaultPaymentShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where endDate equals to DEFAULT_END_DATE
        defaultPaymentShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the paymentList where endDate equals to UPDATED_END_DATE
        defaultPaymentShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultPaymentShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the paymentList where endDate equals to UPDATED_END_DATE
        defaultPaymentShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where endDate is not null
        defaultPaymentShouldBeFound("endDate.specified=true");

        // Get all the paymentList where endDate is null
        defaultPaymentShouldNotBeFound("endDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPaymentAmount").value(hasItem(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contractorAmount").value(hasItem(DEFAULT_CONTRACTOR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].businessAmount").value(hasItem(DEFAULT_BUSINESS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .totalPaymentAmount(UPDATED_TOTAL_PAYMENT_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contractorAmount(UPDATED_CONTRACTOR_AMOUNT)
            .businessAmount(UPDATED_BUSINESS_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        PaymentDTO paymentDTO = paymentMapper.toDto(updatedPayment);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getTotalPaymentAmount()).isEqualTo(UPDATED_TOTAL_PAYMENT_AMOUNT);
        assertThat(testPayment.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayment.getContractorAmount()).isEqualTo(UPDATED_CONTRACTOR_AMOUNT);
        assertThat(testPayment.getBusinessAmount()).isEqualTo(UPDATED_BUSINESS_AMOUNT);
        assertThat(testPayment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPayment.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).save(testPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(0)).save(payment);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Payment in Elasticsearch
        verify(mockPaymentSearchRepository, times(1)).deleteById(payment.getId());
    }

    @Test
    @Transactional
    public void searchPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        when(mockPaymentSearchRepository.search(queryStringQuery("id:" + payment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(payment), PageRequest.of(0, 1), 1));
        // Search the payment
        restPaymentMockMvc.perform(get("/api/_search/payments?query=id:" + payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPaymentAmount").value(hasItem(DEFAULT_TOTAL_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contractorAmount").value(hasItem(DEFAULT_CONTRACTOR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].businessAmount").value(hasItem(DEFAULT_BUSINESS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = new Payment();
        payment1.setId(1L);
        Payment payment2 = new Payment();
        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);
        payment2.setId(2L);
        assertThat(payment1).isNotEqualTo(payment2);
        payment1.setId(null);
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDTO.class);
        PaymentDTO paymentDTO1 = new PaymentDTO();
        paymentDTO1.setId(1L);
        PaymentDTO paymentDTO2 = new PaymentDTO();
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
        paymentDTO2.setId(paymentDTO1.getId());
        assertThat(paymentDTO1).isEqualTo(paymentDTO2);
        paymentDTO2.setId(2L);
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
        paymentDTO1.setId(null);
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentMapper.fromId(null)).isNull();
    }
}
