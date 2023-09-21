package br.com.elotech.testejava.services;

import br.com.elotech.testejava.exceptions.DataIntegrityException;
import br.com.elotech.testejava.models.Contact;
import br.com.elotech.testejava.models.Person;
import br.com.elotech.testejava.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {


    private final ContactRepository repository;

    public void saveAllContatos(List<Contact> contactList, Person person){
        try {
            contactList.forEach(contact -> contact.setPerson(person));
            repository.saveAll(contactList);
        }catch (Exception e){
            throw new DataIntegrityException("Erro ao Cadastrar os contatos", e);
        }
    }
    public void deleteContatoById(UUID id){
        try {
            repository.deleteById(id);
        }catch (Exception e){
            throw new DataIntegrityException("Ocorreu um erro ao deletar o contato.", e);
        }
    }

}
