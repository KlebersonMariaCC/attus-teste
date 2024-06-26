package com.attus.teste03.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.model.Pessoa;

@Service
public interface EnderecoService {

    //List<Endereco> getEnderecos(List<EnderecoDTO> enderecos);

    List<Endereco> updateEnderecos(List<EnderecoDTO> enderecos);

    ResponseEntity<?> cadastraEndereco(Pessoa pessoa, EnderecoDTO enderecoDTO);

    Optional<Endereco> getEndereco(Long id);

    void atualizarEndereco(Endereco endereco, EnderecoDTO enderecoDTO);

    List<Endereco> consultaEnderecos();

    List<Endereco> cadastraEnderecos(List<EnderecoDTO> enderecos);

}
