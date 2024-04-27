package com.attus.teste03.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attus.teste03.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa,Long>{

}
