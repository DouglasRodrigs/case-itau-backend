package com.example.demo.controller;

import com.example.demo.util.ValidatePass;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidatePassControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ValidatePassController validatePassController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(validatePassController).build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testValidatePass_ValidPassword() throws Exception {
        try (var validatePassMock = mockStatic(ValidatePass.class)) {
            validatePassMock.when(() -> ValidatePass.isValid(anyString())).thenReturn(true);

            ResponseEntity<String> response = validatePassController.validatePass("validPassword");
            assertEquals(ResponseEntity.ok(new Gson().toJson(true)), response);

            mockMvc.perform(post("/validate/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("\"validPassword\""))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testValidatePass_InvalidPassword() throws Exception {
        try (var validatePassMock = mockStatic(ValidatePass.class)) {
            validatePassMock.when(() -> ValidatePass.isValid(anyString())).thenReturn(false);

            ResponseEntity<String> response = validatePassController.validatePass("invalidPassword");
            assertEquals(ResponseEntity.ok(new Gson().toJson(false)), response);

            mockMvc.perform(post("/validate/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("\"invalidPassword\""))
                    .andExpect(status().isOk());
        }
    }
}
