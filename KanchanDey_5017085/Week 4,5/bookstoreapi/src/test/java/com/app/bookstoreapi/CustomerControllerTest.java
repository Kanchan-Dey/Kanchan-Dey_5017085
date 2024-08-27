package com.app.bookstoreapi;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.app.bookstoreapi.controller.CustomerController;
import com.app.bookstoreapi.entity.Customer;
import com.app.bookstoreapi.security.JwtUtil;
import com.app.bookstoreapi.service.CustomMetricsService;
import com.app.bookstoreapi.service.CustomUserDetailsService;
import com.app.bookstoreapi.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Arrays;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
@WithMockUser(username="mohit")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private CustomMetricsService customMetricsService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAge(30);
        customer.setNumber(1234567890L);
    }

    @Test
    @WithMockUser(username="mohit")
    public void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    @WithMockUser(username="mohit")
    public void testSaveCustomer() throws Exception {
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(ResponseEntity.ok("Customer saved with id: 1"));

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30,\"number\":1234567890}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer saved with id: 1"));
    }

    @Test
    @WithMockUser(username="mohit")
    public void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(ResponseEntity.ok("Customer with id: 1 updated"));

        mockMvc.perform(put("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":31,\"number\":9876543210}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer with id: 1 updated"));
    }

    @Test
    @WithMockUser(username="mohit")
    public void testDeleteCustomer() throws Exception {
        when(customerService.deleteById(1L)).thenReturn(ResponseEntity.ok("Customer with id: 1 deleted"));

        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer with id: 1 deleted"));
    }
}
