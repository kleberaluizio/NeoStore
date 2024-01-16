package br.com.neostore.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDTOTest {


    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void shouldNotFailValidationIfCnpjIsValid() {
        // GIVEN
        String validCnpj = "12.345.000/0001-01";

        // WHEN & THEN
        assertDoesNotThrow(() -> {
            new SupplierDTO("Supplier", "email@gmail.com", "Comentário", validCnpj);
        });
    }
    @Test
    void shouldFailValidationIfCnpjIsInvalid() {

        // GIVEN
        String invalidCnpj = "12.345/679-01";

        // WHEN & THEN
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
        // WHEN
            new SupplierDTO("Supplier", "email@gmail.com", "Comentário", "12.345/679-01");
        });

        // THEN
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size());  // Verifica se há uma única violação
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = "Por favor, digite um cnpj válido";
        assertEquals(message, violation.getMessage()); // Verifica se a mensagem da violação é a esperada
    }
    @Test
    void shouldFailValidationIfCnpjIsBlank() {
        // GIVEN
        String invalidCnpj = "";

        // WHEN & THEN
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
        // WHEN
            new SupplierDTO("Supplier", "email@gmail.com", "Comentário", invalidCnpj);
        });

        // THEN
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(2, violations.size()); // Certifica-se de que há uma única violação
    }

    @Test
    void shouldFailValidationIfEmailIsInvalid() {

        // GIVEN
        String invalidEmail = "invalidemail";
        // WHEN & THEN
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            // WHEN
            new SupplierDTO("Supplier", invalidEmail, "Comentário", "12.001.345/6789-01");
        });


        // THEN
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        assertEquals(1, violations.size()); // Verifica se há uma única violação
        ConstraintViolation<?> violation = violations.iterator().next();
        assertEquals("Por favor, digite um e-mail válido", violation.getMessage()); // Verifica se a mensagem da violação é a esperada
    }
    @Test
    void shouldNotFailValidationIfEmailIsValid() {
        // GIVEN
        String validEmail = "email@gmail.com";
        // WHEN & THEN
        assertDoesNotThrow(() -> {
            new SupplierDTO("Supplier", validEmail, "Comentário", "12.001.345/6789-01");
        });
    }
}