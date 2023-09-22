package br.com.elotech.testejava.dtos;

import br.com.elotech.testejava.models.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTO {

    private UUID id;
    @NotBlank(message = "o campo 'nome' é obrigatório")
    private String name;
    @CPF(message = "o campo 'cpf' deve ser um cpf válido")
    private String cpf;
    @NotNull(message = "o campo 'dataNascimento' é obrigatório")
    private LocalDate dateBirth;
    @NotEmpty(message = "a pessoa deve ter ao menos um contato")
    private List<ContactDTO> contacts;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.cpf = person.getCpf();
        this.dateBirth = person.getDateBirth();
        this.contacts = person.getContacts().stream().map(ContactDTO::new).toList();
    }
}
