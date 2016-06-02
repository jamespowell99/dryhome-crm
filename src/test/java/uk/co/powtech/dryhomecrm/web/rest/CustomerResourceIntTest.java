package uk.co.powtech.dryhomecrm.web.rest;

import uk.co.powtech.dryhomecrm.DryhomecrmApp;
import uk.co.powtech.dryhomecrm.domain.Customer;
import uk.co.powtech.dryhomecrm.repository.CustomerRepository;
import uk.co.powtech.dryhomecrm.repository.search.CustomerSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DryhomecrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_CONTACT_TITLE = "AAAAA";
    private static final String UPDATED_CONTACT_TITLE = "BBBBB";
    private static final String DEFAULT_CONTACT_FIRST = "AAAAA";
    private static final String UPDATED_CONTACT_FIRST = "BBBBB";
    private static final String DEFAULT_CONTACT_SURNAME = "AAAAA";
    private static final String UPDATED_CONTACT_SURNAME = "BBBBB";
    private static final String DEFAULT_TEL = "AAAAA";
    private static final String UPDATED_TEL = "BBBBB";
    private static final String DEFAULT_MOB = "AAAAA";
    private static final String UPDATED_MOB = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ADDRESS_1 = "AAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBB";
    private static final String DEFAULT_ADDRESS_2 = "AAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBB";
    private static final String DEFAULT_ADDRESS_3 = "AAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBB";
    private static final String DEFAULT_TOWN = "AAAAA";
    private static final String UPDATED_TOWN = "BBBBB";
    private static final String DEFAULT_POST_CODE = "AAAAA";
    private static final String UPDATED_POST_CODE = "BBBBB";
    private static final String DEFAULT_PRODUCTS = "AAAAA";
    private static final String UPDATED_PRODUCTS = "BBBBB";
    private static final String DEFAULT_INTERESTED = "AAAAA";
    private static final String UPDATED_INTERESTED = "BBBBB";
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerSearchRepository customerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerSearchRepository", customerSearchRepository);
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customerSearchRepository.deleteAll();
        customer = new Customer();
        customer.setName(DEFAULT_NAME);
        customer.setContactTitle(DEFAULT_CONTACT_TITLE);
        customer.setContactFirst(DEFAULT_CONTACT_FIRST);
        customer.setContactSurname(DEFAULT_CONTACT_SURNAME);
        customer.setTel(DEFAULT_TEL);
        customer.setMob(DEFAULT_MOB);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setAddress1(DEFAULT_ADDRESS_1);
        customer.setAddress2(DEFAULT_ADDRESS_2);
        customer.setAddress3(DEFAULT_ADDRESS_3);
        customer.setTown(DEFAULT_TOWN);
        customer.setPostCode(DEFAULT_POST_CODE);
        customer.setProducts(DEFAULT_PRODUCTS);
        customer.setInterested(DEFAULT_INTERESTED);
        customer.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getContactTitle()).isEqualTo(DEFAULT_CONTACT_TITLE);
        assertThat(testCustomer.getContactFirst()).isEqualTo(DEFAULT_CONTACT_FIRST);
        assertThat(testCustomer.getContactSurname()).isEqualTo(DEFAULT_CONTACT_SURNAME);
        assertThat(testCustomer.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCustomer.getMob()).isEqualTo(DEFAULT_MOB);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCustomer.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCustomer.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testCustomer.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testCustomer.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testCustomer.getProducts()).isEqualTo(DEFAULT_PRODUCTS);
        assertThat(testCustomer.getInterested()).isEqualTo(DEFAULT_INTERESTED);
        assertThat(testCustomer.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the Customer in ElasticSearch
        Customer customerEs = customerSearchRepository.findOne(testCustomer.getId());
        assertThat(customerEs).isEqualToComparingFieldByField(testCustomer);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contactTitle").value(hasItem(DEFAULT_CONTACT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].contactFirst").value(hasItem(DEFAULT_CONTACT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].contactSurname").value(hasItem(DEFAULT_CONTACT_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
                .andExpect(jsonPath("$.[*].mob").value(hasItem(DEFAULT_MOB.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
                .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3.toString())))
                .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
                .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
                .andExpect(jsonPath("$.[*].products").value(hasItem(DEFAULT_PRODUCTS.toString())))
                .andExpect(jsonPath("$.[*].interested").value(hasItem(DEFAULT_INTERESTED.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contactTitle").value(DEFAULT_CONTACT_TITLE.toString()))
            .andExpect(jsonPath("$.contactFirst").value(DEFAULT_CONTACT_FIRST.toString()))
            .andExpect(jsonPath("$.contactSurname").value(DEFAULT_CONTACT_SURNAME.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.mob").value(DEFAULT_MOB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.products").value(DEFAULT_PRODUCTS.toString()))
            .andExpect(jsonPath("$.interested").value(DEFAULT_INTERESTED.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customer.getId());
        updatedCustomer.setName(UPDATED_NAME);
        updatedCustomer.setContactTitle(UPDATED_CONTACT_TITLE);
        updatedCustomer.setContactFirst(UPDATED_CONTACT_FIRST);
        updatedCustomer.setContactSurname(UPDATED_CONTACT_SURNAME);
        updatedCustomer.setTel(UPDATED_TEL);
        updatedCustomer.setMob(UPDATED_MOB);
        updatedCustomer.setEmail(UPDATED_EMAIL);
        updatedCustomer.setAddress1(UPDATED_ADDRESS_1);
        updatedCustomer.setAddress2(UPDATED_ADDRESS_2);
        updatedCustomer.setAddress3(UPDATED_ADDRESS_3);
        updatedCustomer.setTown(UPDATED_TOWN);
        updatedCustomer.setPostCode(UPDATED_POST_CODE);
        updatedCustomer.setProducts(UPDATED_PRODUCTS);
        updatedCustomer.setInterested(UPDATED_INTERESTED);
        updatedCustomer.setNotes(UPDATED_NOTES);

        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getContactTitle()).isEqualTo(UPDATED_CONTACT_TITLE);
        assertThat(testCustomer.getContactFirst()).isEqualTo(UPDATED_CONTACT_FIRST);
        assertThat(testCustomer.getContactSurname()).isEqualTo(UPDATED_CONTACT_SURNAME);
        assertThat(testCustomer.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCustomer.getMob()).isEqualTo(UPDATED_MOB);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCustomer.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCustomer.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testCustomer.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testCustomer.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustomer.getProducts()).isEqualTo(UPDATED_PRODUCTS);
        assertThat(testCustomer.getInterested()).isEqualTo(UPDATED_INTERESTED);
        assertThat(testCustomer.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the Customer in ElasticSearch
        Customer customerEs = customerSearchRepository.findOne(testCustomer.getId());
        assertThat(customerEs).isEqualToComparingFieldByField(testCustomer);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);
        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean customerExistsInEs = customerSearchRepository.exists(customer.getId());
        assertThat(customerExistsInEs).isFalse();

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);

        // Search the customer
        restCustomerMockMvc.perform(get("/api/_search/customers?query=id:" + customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactTitle").value(hasItem(DEFAULT_CONTACT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].contactFirst").value(hasItem(DEFAULT_CONTACT_FIRST.toString())))
            .andExpect(jsonPath("$.[*].contactSurname").value(hasItem(DEFAULT_CONTACT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].mob").value(hasItem(DEFAULT_MOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].products").value(hasItem(DEFAULT_PRODUCTS.toString())))
            .andExpect(jsonPath("$.[*].interested").value(hasItem(DEFAULT_INTERESTED.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
}
