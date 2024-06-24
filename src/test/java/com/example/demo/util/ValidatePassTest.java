package com.example.demo.util;
import com.example.demo.util.ValidatePass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatePassTest {

    @Test
    public void testValidPassword() {
        String validPassword = "StrongP@ssw0rd";
        assertTrue(ValidatePass.isValid(validPassword));
    }

    @Test
    public void testInvalidPassword() {
        String invalidPassword = "weakpassword";
        assertFalse(ValidatePass.isValid(invalidPassword));
    }

    // Adicione mais cenários de teste conforme necessário
}