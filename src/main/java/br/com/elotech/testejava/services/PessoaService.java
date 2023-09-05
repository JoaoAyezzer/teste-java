package br.com.elotech.testejava.services;

import br.com.elotech.testejava.exceptions.DataIntegrityException;
import br.com.elotech.testejava.exceptions.ObjectNotfoundException;
import br.com.elotech.testejava.models.Pessoa;
import br.com.elotech.testejava.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final ContatoService contatoService;


    public Pessoa savePessoa(Pessoa pessoaResquest){
        if (pessoaResquest.getDataNascimento().isAfter(LocalDate.now())) throw new DataIntegrityException("A data de nascimento nao pode ser futura");
        Pessoa pessoa;
        try {
             pessoa =  repository.save(pessoaResquest);
        }catch (Exception e){
            throw new DataIntegrityException("Ocorreu um erro na gravação da Pessoa. Verifique os campos do cadastro e tente novamente");
        }
        contatoService.saveAllContatos(pessoaResquest.getContatos(), pessoa);
        return pessoa;
    }
    public Pessoa getPessoaById(UUID id){
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado pessoa com o id: %s", id))
        );
    }
    public Pessoa getPessoaByCpf(String cpf){
        return repository.findByCpf(cpf).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado pessoa com o cpf: %s", cpf))
        );
    }
    public void updatePessoa(Pessoa pessoaResquest) {
        var pessoa = repository.findById(pessoaResquest.getId());
        if (pessoa.isEmpty()) throw new ObjectNotfoundException(String.format("Não encontrado pessoa com id: %s", pessoaResquest.getId()));
        savePessoa(pessoaResquest);
    }

    public void deletePessoaById(UUID id){
        repository.findById(id).orElseThrow(
                () -> new ObjectNotfoundException(String.format("Não encontrado Pessoa com o ID: %s", id))
        ).getContatos().forEach(
                contato -> contatoService.deleteContatoById(contato.getId())
        );
        try {
            repository.deleteById(id);
        }catch (Exception e){
            throw new DataIntegrityException("Ocorreu um erro ao deletar a pessoa", e);
        }
    }
    public Page<Pessoa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}
