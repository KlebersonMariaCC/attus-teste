package com.attus.teste03.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attus.teste03.model.Pessoa;
import java.util.Optional;


public interface PessoaRepository  extends JpaRepository<Pessoa,Long>{

    public Optional<Pessoa> findByNome(String nome);

}
