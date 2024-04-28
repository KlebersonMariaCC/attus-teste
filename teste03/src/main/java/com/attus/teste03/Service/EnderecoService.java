package com.attus.teste03.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.model.Endereco;

@Service
public interface EnderecoService {

    List<Endereco> getEnderecos(List<EnderecoDTO> enderecos);

    List<Endereco> updateEnderecos(List<EnderecoDTO> enderecos);

}
