package br.com.neostore.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "O nome não pode estar em branco")
    private String name;
    @Email(message = "Por favor, digite um e-mail válido")
    private String email;
    private String comment;
    @NotBlank(message = "O cnpj não pode estar em branco")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "Por favor, digite um cnpj válido")
    private String cnpj;

    public Supplier(){
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Supplier that = (Supplier) object;
        return Objects.equals(this.cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, comment, cnpj);
    }
}
