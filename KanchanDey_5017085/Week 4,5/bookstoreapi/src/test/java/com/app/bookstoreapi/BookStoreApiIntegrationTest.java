package com.app.bookstoreapi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookStoreApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllCustomers() {
        String url = "http://localhost:" + port + "/customer";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert that the status code is 200 (OK)
        assert(response.getStatusCode() == HttpStatus.OK);
    }
}
