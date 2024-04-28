package com.attus.teste03.Service;

import java.util.Optional;

import com.attus.teste03.DTO.PessoaDTO;
import com.attus.teste03.model.Pessoa;


public interface PessoaService {

    Optional<Pessoa> getByNome(String nome);

    Pessoa cadastraPessoa(PessoaDTO pessoaDTO);

    Optional<Pessoa> getById(Long id);

    Pessoa editarPessoa(Pessoa pessoa, PessoaDTO pessoaDTO);

    Optional<Pessoa> consultaPessoa(Long id);

    
}
