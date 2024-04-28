package com.attus.teste03.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (EnderecoDTO enderecoDTO : enderecos) {
            Optional<Endereco> enderecoExiste = enderecoRepository.findByLogradouroAndCepAndNumeroAndCidadeAndEstado(
                enderecoDTO.getLogradouro(), enderecoDTO.getCep(), enderecoDTO.getNumero()
                ,enderecoDTO.getCidade(),enderecoDTO.getEstado());
            if (enderecoExiste.isPresent()) {
                Endereco endereco = enderecoExiste.get();
                result.add(endereco);
            }else {
                Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
                enderecoDTO.getCep(), enderecoDTO.getNumero(),
                enderecoDTO.getCidade(),enderecoDTO.getEstado());
                
                result.add(endereco);
                
                enderecoRepository.save(endereco);
            }
            
        }
        return result;

    }

    @Override
    public List<Endereco> updateEnderecos(List<EnderecoDTO> enderecos) {
        List<Endereco> result = new ArrayList<Endereco>();

        for (EnderecoDTO enderecoDTO : enderecos) {

            Optional<Endereco> enderecoExiste = enderecoRepository.findById(enderecoDTO.getId());
            if (enderecoExiste.isPresent()) {
                Endereco endereco = enderecoExiste.get();
                endereco.setLogradouro(enderecoDTO.getLogradouro());
                endereco.setCep(enderecoDTO.getCep());
                endereco.setCidade(enderecoDTO.getCidade());
                endereco.setEstado(enderecoDTO.getEstado());
                endereco.setNumero(enderecoDTO.getNumero());

                result.add(endereco);
                enderecoRepository.save(endereco);

            } else {
                Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
                enderecoDTO.getCep(), enderecoDTO.getNumero(),
                enderecoDTO.getCidade(),enderecoDTO.getEstado());

                result.add(endereco);
                enderecoRepository.save(endereco);
            }   
        }
        return result;
    }
}
