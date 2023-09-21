package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.models.Contact;
import br.com.elotech.testejava.models.Person;
import br.com.elotech.testejava.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @InjectMocks
    PersonController personController;

    @Mock
    PersonService personService;

    MockMvc mockMvc;
    private Person person;
    private UUID id;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .alwaysDo(print())
                .build();
        var contato = Contact
                .builder()
                .name("Diane")
                .fone("44998999116")
                .email("mockemail@gmail.com")
                .build();
        person = Person
                .builder()
                .name("Joao Ayezzer")
                .cpf("17013265071")
                .dateBirth(LocalDate.of(1994,3,3))
                .contacts(Collections.singletonList(contato))
                .build();
        id = UUID.randomUUID();
    }

    @Test
    void deveCadastrarPessoaComSucesso() throws Exception {
        Mockito.when(personService.savePessoa(person)).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(converteObjetoEmJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(personService).savePessoa(person);
        Mockito.verifyNoMoreInteractions(personService);

    }
    @Test
    void deveBuscarPessoaPorIdComSucesso() throws Exception {
        person.setId(id);
        person.getContacts().forEach(contact -> contact.setId(UUID.randomUUID()));

        Mockito.when(personService.getPessoaById(id)).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/person/%s", id))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(personService).getPessoaById(id);
        Mockito.verifyNoMoreInteractions(personService);

    }
    @Test
    void deveBuscarPessoaPorCpfComSucesso() throws Exception {
        person.setId(id);
        person.getContacts().forEach(contact -> contact.setId(UUID.randomUUID()));

        Mockito.when(personService.getPessoaByCpf(person.getCpf())).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/person/cpf/%s", person.getCpf()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(personService).getPessoaByCpf(person.getCpf());
        Mockito.verifyNoMoreInteractions(personService);

    }
    @Test
    void deveAtualizarPessoaComSucesso() throws Exception {
        person.setId(id);
        person.getContacts().forEach(contact -> contact.setId(UUID.randomUUID()));
        System.out.println(person);
        Mockito.doNothing().when(personService).updatePessoa(person);

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                        .content(converteObjetoEmJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(personService).updatePessoa(person);
        Mockito.verifyNoMoreInteractions(personService);

    }
    @Test
    void deveDeletarPessoaPorIdComSucesso() throws Exception {
        person.setId(id);
        person.getContacts().forEach(contact -> contact.setId(UUID.randomUUID()));

        Mockito.doNothing().when(personService).deletePessoaById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/person/%s", id))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(personService).deletePessoaById(id);
        Mockito.verifyNoMoreInteractions(personService);

    }
    public static String converteObjetoEmJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
