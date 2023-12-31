package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.services.ContactService;
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
@RequestMapping(value = "contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContatoById(@PathVariable UUID id){
        service.deleteContatoById(id);
        return ResponseEntity.noContent().build();
    }
}
