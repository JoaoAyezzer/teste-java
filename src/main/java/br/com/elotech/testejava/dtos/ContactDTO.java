package br.com.elotech.testejava.dtos;

import br.com.elotech.testejava.models.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ContactDTO {

    private UUID id;
    private String name;
    private String fone;
    private String email;

    public ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.fone = contact.getFone();
        this.email = contact.getEmail();
    }
}
