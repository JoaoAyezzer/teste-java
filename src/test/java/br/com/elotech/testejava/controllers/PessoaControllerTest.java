package br.com.elotech.testejava.controllers;

import br.com.elotech.testejava.models.Contato;
import br.com.elotech.testejava.models.Pessoa;
import br.com.elotech.testejava.services.PessoaService;
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
public class PessoaControllerTest {

    @InjectMocks
    PessoaController pessoaController;

    @Mock
    PessoaService pessoaService;

    MockMvc mockMvc;
    private Pessoa pessoa;
    private UUID id;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(pessoaController)
                .alwaysDo(print())
                .build();
        var contato = Contato
                .builder()
                .nome("Diane")
                .telefone("44998999116")
                .email("mockemail@gmail.com")
                .build();
        pessoa = Pessoa
                .builder()
                .nome("Joao Ayezzer")
                .cpf("17013265071")
                .dataNascimento(LocalDate.of(1994,3,3))
                .contatos(Collections.singletonList(contato))
                .build();
        id = UUID.randomUUID();
    }

    @Test
    void deveCadastrarPessoaComSucesso() throws Exception {
        Mockito.when(pessoaService.savePessoa(pessoa)).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .content(converteObjetoEmJsonString(pessoa))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(pessoaService).savePessoa(pessoa);
        Mockito.verifyNoMoreInteractions(pessoaService);

    }
    @Test
    void deveBuscarPessoaPorIdComSucesso() throws Exception {
        pessoa.setId(id);
        pessoa.getContatos().forEach(contato -> contato.setId(UUID.randomUUID()));

        Mockito.when(pessoaService.getPessoaById(id)).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/pessoas/%s", id))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(pessoaService).getPessoaById(id);
        Mockito.verifyNoMoreInteractions(pessoaService);

    }
    @Test
    void deveBuscarPessoaPorCpfComSucesso() throws Exception {
        pessoa.setId(id);
        pessoa.getContatos().forEach(contato -> contato.setId(UUID.randomUUID()));

        Mockito.when(pessoaService.getPessoaByCpf(pessoa.getCpf())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/pessoas/cpf/%s", pessoa.getCpf()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(pessoaService).getPessoaByCpf(pessoa.getCpf());
        Mockito.verifyNoMoreInteractions(pessoaService);

    }
    @Test
    void deveAtualizarPessoaComSucesso() throws Exception {
        pessoa.setId(id);
        pessoa.getContatos().forEach(contato -> contato.setId(UUID.randomUUID()));
        System.out.println(pessoa);
        Mockito.doNothing().when(pessoaService).updatePessoa(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.put("/pessoas")
                        .content(converteObjetoEmJsonString(pessoa))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(pessoaService).updatePessoa(pessoa);
        Mockito.verifyNoMoreInteractions(pessoaService);

    }
    @Test
    void deveDeletarPessoaPorIdComSucesso() throws Exception {
        pessoa.setId(id);
        pessoa.getContatos().forEach(contato -> contato.setId(UUID.randomUUID()));

        Mockito.doNothing().when(pessoaService).deletePessoaById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/pessoas/%s", id))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(pessoaService).deletePessoaById(id);
        Mockito.verifyNoMoreInteractions(pessoaService);

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
