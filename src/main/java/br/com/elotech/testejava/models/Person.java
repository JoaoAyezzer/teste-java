package br.com.elotech.testejava.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank(message = "o campo 'nome' é obrigatório")
    private String name;
    @Column(unique = true)
    @CPF(message = "o campo 'cpf' deve ser um cpf válido")
    private String cpf;
    @NotNull(message = "o campo 'dataNascimento' é obrigatório")
    private LocalDate dateBirth;
    @NotEmpty(message = "a pessoa deve ter ao menos um contato")
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
    private List<Contact> contacts;

}
