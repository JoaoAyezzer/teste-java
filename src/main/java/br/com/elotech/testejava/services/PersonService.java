package br.com.elotech.testejava.services;

import br.com.elotech.testejava.exceptions.DataIntegrityException;
import br.com.elotech.testejava.exceptions.ObjectNotfoundException;
import br.com.elotech.testejava.models.Person;
import br.com.elotech.testejava.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;
    private final ContactService contactService;


    public Person savePessoa(Person personResquest){
        if (personResquest.getDateBirth().isAfter(LocalDate.now())) throw new DataIntegrityException("A data de nascimento nao pode ser futura");
        Person person;
        try {
             person =  repository.save(personResquest);
        }catch (Exception e){
            throw new DataIntegrityException("Ocorreu um erro na gravação da Pessoa. Verifique os campos do cadastro e tente novamente");
        }
        contactService.saveAllContatos(personResquest.getContacts(), person);
        return person;
    }
    public Person getPessoaById(UUID id){
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado pessoa com o id: %s", id))
        );
    }
    public Person getPessoaByCpf(String cpf){
        return repository.findByCpf(cpf).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado pessoa com o cpf: %s", cpf))
        );
    }
    public void updatePessoa(Person personResquest) {
        var pessoa = repository.findById(personResquest.getId());
        if (pessoa.isEmpty()) throw new ObjectNotfoundException(String.format("Não encontrado pessoa com id: %s", personResquest.getId()));
        savePessoa(personResquest);
    }

    public void deletePessoaById(UUID id){
        repository.findById(id).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado Pessoa com o ID: %s", id))
        ).getContacts().forEach(
                contato -> contactService.deleteContatoById(contato.getId())
        );
        try {
            repository.deleteById(id);
        }catch (Exception e){
            throw new DataIntegrityException("Ocorreu um erro ao deletar a pessoa", e);
        }
    }
    public Page<Person> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}
