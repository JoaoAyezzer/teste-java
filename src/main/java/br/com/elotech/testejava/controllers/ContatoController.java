package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.services.ContatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Service
@RestController
@RequestMapping(value = "contatos")
@RequiredArgsConstructor
public class    ContatoController {

    private final ContatoService service;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContatoById(@PathVariable UUID id){
        service.deleteContatoById(id);
        return ResponseEntity.noContent().build();
    }
}
