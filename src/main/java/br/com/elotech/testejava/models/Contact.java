package br.com.elotech.testejava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank(message = "o campo 'nome' é obrigatório")
    private String name;
    @NotBlank(message = "o campo 'telefone' é obrigatório")
    private String fone;
    @Email(message = "o email deve ser um email válido")
    private String email;
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Person person;
}
