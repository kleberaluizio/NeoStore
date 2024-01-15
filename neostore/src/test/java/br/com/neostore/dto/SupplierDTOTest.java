package br.com.neostore.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    void shouldFailValidationIfCnpjIsInvalid() {

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> new SupplierDTO("Supplier", "email@gmail.com", "Comentário", "12.345/679-01"));


        // Agora você pode acessar a exceção diretamente
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        // Aqui, você pode realizar verificações específicas sobre as mensagens associadas a cada violação
        assertEquals(1, violations.size()); // Certifica-se de que há uma única violação
        ConstraintViolation<?> violation = violations.iterator().next();
        assertEquals( "Por favor, digite um cnpj válido", violation.getMessage());
    }
    @Test
    void shouldFailValidationIfCnpjIsBlank() {

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> new SupplierDTO("Supplier", "email@gmail.com", "Comentário", ""));

        // Agora você pode acessar a exceção diretamente
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        // Aqui, você pode realizar verificações específicas sobre as mensagens associadas a cada violação
        assertEquals(2, violations.size()); // Certifica-se de que há uma única violação
    }

    @Test
    void shouldFailValidationIfEmailIsInvalid() {

        jakarta.validation.ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> new SupplierDTO("Supplier", "invalidemail", "Comentário", "12.001.345/6789-01"));


        // Agora você pode acessar a exceção diretamente
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        // Aqui, você pode realizar verificações específicas sobre as mensagens associadas a cada violação
        assertEquals(1, violations.size()); // Certifica-se de que há uma única violação
        ConstraintViolation<?> violation = violations.iterator().next();
        assertEquals("Por favor, digite um e-mail válido", violation.getMessage());

    }
}