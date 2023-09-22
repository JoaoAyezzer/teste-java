package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.dtos.PersonDTO;
import br.com.elotech.testejava.models.Person;
import br.com.elotech.testejava.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Person personRequest){
        var pessoa = service.savePessoa(personRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getPessoaById(@PathVariable UUID id){
        return ResponseEntity.ok(service.getPessoaById(id));
    }
    @GetMapping(value = "cpf/{cpf}")
    public ResponseEntity<Person> getPessoaByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.getPessoaByCpf(cpf));
    }
    @PutMapping
    public ResponseEntity<Void> updatePessoa(@RequestBody Person person){
        service.updatePessoa(person);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<Person>> findPessoaPerPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        return ResponseEntity.ok().body(service.findPage(page, linesPerPage, orderBy, direction));
    }
    @GetMapping(value = "all")
    public ResponseEntity<List<PersonDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoaById(@PathVariable UUID id){
        service.deletePessoaById(id);
        return ResponseEntity.noContent().build();
    }


}
