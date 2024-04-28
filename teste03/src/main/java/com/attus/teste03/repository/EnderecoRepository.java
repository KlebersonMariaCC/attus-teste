package com.attus.teste03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attus.teste03.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco,Long>{

    Optional<Endereco> findByCepAndLogradouroAndNumero(Long cep, String logradouro, Long numero);

    Optional<Endereco> findByLogradouroAndCepAndNumeroAndCidadeAndEstado(String logradouro, Long cep, Long numero,
            String cidade, String estado);

}
