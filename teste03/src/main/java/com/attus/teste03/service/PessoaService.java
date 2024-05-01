package com.attus.teste03.service;

import java.util.List;
import java.util.Optional;

import com.attus.teste03.DTO.PessoaDTO;
import com.attus.teste03.model.Pessoa;


public interface PessoaService {


    Pessoa cadastraPessoa(PessoaDTO pessoaDTO);

    Optional<Pessoa> getById(Long id);

    Pessoa editarPessoa(Pessoa pessoa, PessoaDTO pessoaDTO);

    Optional<Pessoa> consultaPessoa(Long id);

    Optional<Pessoa> pessoaExiste(PessoaDTO pessoaDTO);

    Pessoa setEnderecoPrincipal(Pessoa pessoa, Long idEndereco);

    List<Pessoa> consultaPessoas();

    
}
