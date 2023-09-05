package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.models.Pessoa;
import br.com.elotech.testejava.services.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Pessoa pessoaRequest){
        var pessoa = service.savePessoa(pessoaRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable UUID id){
        return ResponseEntity.ok(service.getPessoaById(id));
    }
    @GetMapping(value = "cpf/{cpf}")
    public ResponseEntity<Pessoa> getPessoaByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.getPessoaByCpf(cpf));
    }
    @PutMapping
    public ResponseEntity<Void> updatePessoa(@RequestBody Pessoa pessoa){
        service.updatePessoa(pessoa);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<Pessoa>> findPessoaPerPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        return ResponseEntity.ok().body(service.findPage(page, linesPerPage, orderBy, direction));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoaById(@PathVariable UUID id){
        service.deletePessoaById(id);
        return ResponseEntity.noContent().build();
    }


}
