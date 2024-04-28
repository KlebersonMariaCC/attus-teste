package com.attus.teste03.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService{

    @Autowired
    EnderecoRepository enderecoRepository;

    @Override
    public List<Endereco> getEnderecos(List<EnderecoDTO> enderecos) {
      List<Endereco> result = new ArrayList<Endereco>();
      List<Endereco> enderecosSalvos = enderecoRepository.findAll();

      for (EnderecoDTO enderecoDTO : enderecos) {
        
        Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
        enderecoDTO.getCep(), enderecoDTO.getNumero(),
        enderecoDTO.getCidade(),enderecoDTO.getEstado());

        result.add(endereco);
        
        if (!enderecosSalvos.contains(endereco)){
            enderecoRepository.save(endereco);
        }
        

      }
      return result;
    }

}
