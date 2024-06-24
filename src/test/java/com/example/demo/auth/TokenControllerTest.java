package com.example.demo.auth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TokenControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToken() {
        TokenController.User user = new TokenController.User("password", "clientId", "password", "username");

        ResponseEntity<String> mockResponse = new ResponseEntity<>("{\"access_token\":\"mock_token\"}", HttpStatus.OK);
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            tokenController.token(user);
        });

    }
}
