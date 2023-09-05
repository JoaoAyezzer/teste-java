package br.com.elotech.testejava.services;

import br.com.elotech.testejava.exceptions.DataIntegrityException;
import br.com.elotech.testejava.models.Contato;
import br.com.elotech.testejava.models.Pessoa;
import br.com.elotech.testejava.repositories.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContatoService {


    private final ContatoRepository repository;

    public void saveAllContatos(List<Contato> contatoList, Pessoa pessoa){
        try {
            contatoList.forEach(contato -> contato.setPessoa(pessoa));
            repository.saveAll(contatoList);
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
