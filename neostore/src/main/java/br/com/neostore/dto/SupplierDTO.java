package br.com.neostore.dto;

import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public class SupplierDTO {
    @NotBlank(message = "O nome não pode estar em branco")
    private String name;
    @Email(message = "Por favor, digite um e-mail válido")
    private String email;
    private String description;
    @NotBlank(message = "O cnpj não pode estar em branco")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "Por favor, digite um cnpj válido")
    private String cnpj;

    public SupplierDTO(){
    }
    public SupplierDTO(String name,String email,String description,String cnpj) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.cnpj = cnpj;
        validate();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    private void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
    }
}
