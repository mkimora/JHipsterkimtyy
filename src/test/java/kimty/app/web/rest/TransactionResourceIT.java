package kimty.app.web.rest;

import kimty.app.JHipsterkimtyApp;
import kimty.app.domain.Transaction;
import kimty.app.repository.TransactionRepository;
import kimty.app.service.TransactionService;
import kimty.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static kimty.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = JHipsterkimtyApp.class)
public class TransactionResourceIT {

    private static final LocalDate DEFAULT_DATE_T = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_T = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE_R = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_R = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_COMMISSION = 1;
    private static final Integer UPDATED_COMMISSION = 2;

    private static final Integer DEFAULT_COMM_SYSTEM = 1;
    private static final Integer UPDATED_COMM_SYSTEM = 2;

    private static final Integer DEFAULT_COMM_EXP = 1;
    private static final Integer UPDATED_COMM_EXP = 2;

    private static final Integer DEFAULT_TAX_2 = 1;
    private static final Integer UPDATED_TAX_2 = 2;

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMMRETRAIT = 1;
    private static final Integer UPDATED_COMMRETRAIT = 2;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

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

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionService);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .dateT(DEFAULT_DATE_T)
            .montant(DEFAULT_MONTANT)
            .dateR(DEFAULT_DATE_R)
            .commission(DEFAULT_COMMISSION)
            .commSystem(DEFAULT_COMM_SYSTEM)
            .commExp(DEFAULT_COMM_EXP)
            .tax2(DEFAULT_TAX_2)
            .statut(DEFAULT_STATUT)
            .code(DEFAULT_CODE)
            .commretrait(DEFAULT_COMMRETRAIT);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .dateT(UPDATED_DATE_T)
            .montant(UPDATED_MONTANT)
            .dateR(UPDATED_DATE_R)
            .commission(UPDATED_COMMISSION)
            .commSystem(UPDATED_COMM_SYSTEM)
            .commExp(UPDATED_COMM_EXP)
            .tax2(UPDATED_TAX_2)
            .statut(UPDATED_STATUT)
            .code(UPDATED_CODE)
            .commretrait(UPDATED_COMMRETRAIT);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getDateT()).isEqualTo(DEFAULT_DATE_T);
        assertThat(testTransaction.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTransaction.getDateR()).isEqualTo(DEFAULT_DATE_R);
        assertThat(testTransaction.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testTransaction.getCommSystem()).isEqualTo(DEFAULT_COMM_SYSTEM);
        assertThat(testTransaction.getCommExp()).isEqualTo(DEFAULT_COMM_EXP);
        assertThat(testTransaction.getTax2()).isEqualTo(DEFAULT_TAX_2);
        assertThat(testTransaction.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testTransaction.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransaction.getCommretrait()).isEqualTo(DEFAULT_COMMRETRAIT);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateT").value(hasItem(DEFAULT_DATE_T.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())))
            .andExpect(jsonPath("$.[*].dateR").value(hasItem(DEFAULT_DATE_R.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].commSystem").value(hasItem(DEFAULT_COMM_SYSTEM)))
            .andExpect(jsonPath("$.[*].commExp").value(hasItem(DEFAULT_COMM_EXP)))
            .andExpect(jsonPath("$.[*].tax2").value(hasItem(DEFAULT_TAX_2)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].commretrait").value(hasItem(DEFAULT_COMMRETRAIT)));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.dateT").value(DEFAULT_DATE_T.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()))
            .andExpect(jsonPath("$.dateR").value(DEFAULT_DATE_R.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION))
            .andExpect(jsonPath("$.commSystem").value(DEFAULT_COMM_SYSTEM))
            .andExpect(jsonPath("$.commExp").value(DEFAULT_COMM_EXP))
            .andExpect(jsonPath("$.tax2").value(DEFAULT_TAX_2))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.commretrait").value(DEFAULT_COMMRETRAIT));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .dateT(UPDATED_DATE_T)
            .montant(UPDATED_MONTANT)
            .dateR(UPDATED_DATE_R)
            .commission(UPDATED_COMMISSION)
            .commSystem(UPDATED_COMM_SYSTEM)
            .commExp(UPDATED_COMM_EXP)
            .tax2(UPDATED_TAX_2)
            .statut(UPDATED_STATUT)
            .code(UPDATED_CODE)
            .commretrait(UPDATED_COMMRETRAIT);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getDateT()).isEqualTo(UPDATED_DATE_T);
        assertThat(testTransaction.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTransaction.getDateR()).isEqualTo(UPDATED_DATE_R);
        assertThat(testTransaction.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testTransaction.getCommSystem()).isEqualTo(UPDATED_COMM_SYSTEM);
        assertThat(testTransaction.getCommExp()).isEqualTo(UPDATED_COMM_EXP);
        assertThat(testTransaction.getTax2()).isEqualTo(UPDATED_TAX_2);
        assertThat(testTransaction.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testTransaction.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransaction.getCommretrait()).isEqualTo(UPDATED_COMMRETRAIT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
