package com.attus.teste03.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.repository.EnderecoRepository;
import com.attus.teste03.repository.PessoaRepository;
import com.attus.teste03.util.ErroEndereco;

@Service
public class EnderecoServiceImpl implements EnderecoService{

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaRepository pessoaRepository;


    @Override
    public List<Endereco> getEnderecos(List<EnderecoDTO> enderecos) {
        List<Endereco> result = new ArrayList<Endereco>();
        for (EnderecoDTO enderecoDTO : enderecos) {
           
            Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
            enderecoDTO.getCep(), enderecoDTO.getNumero(),
            enderecoDTO.getCidade(),enderecoDTO.getEstado());
            
            Endereco enderecoSalvo = enderecoRepository.save(endereco);

            result.add(enderecoSalvo);
                
                
        }
        return result;

    }

    @Override
    public List<Endereco> updateEnderecos(List<EnderecoDTO> enderecos) {
        List<Endereco> result = new ArrayList<Endereco>();

        for (EnderecoDTO enderecoDTO : enderecos) {
            
                Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
                enderecoDTO.getCep(), enderecoDTO.getNumero(),
                enderecoDTO.getCidade(),enderecoDTO.getEstado());

                Endereco enderecoSalvo = enderecoRepository.save(endereco);

                result.add(enderecoSalvo);
               
        }
        return result;
    }

    @Override
    public ResponseEntity<?> cadastraEndereco(Pessoa pessoa, EnderecoDTO enderecoDTO) {
        Optional<Endereco> enderecoExiste = enderecoRepository.findByLogradouroAndCepAndNumeroAndCidadeAndEstado(
            enderecoDTO.getLogradouro(), enderecoDTO.getCep(), enderecoDTO.getNumero()
            ,enderecoDTO.getCidade(),enderecoDTO.getEstado());
        if (enderecoExiste.isPresent()) {
            Endereco endereco = enderecoExiste.get();
            if(pessoa.getEnderecos().contains(endereco)){
                return ErroEndereco.erroPessoaJaTemEndereco(pessoa.getId());
            }
            pessoa.getEnderecos().add(endereco);
            pessoaRepository.save(pessoa);
        } else {
            Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), 
                enderecoDTO.getCep(), enderecoDTO.getNumero(),
                enderecoDTO.getCidade(),enderecoDTO.getEstado());
            enderecoRepository.save(endereco);
            pessoa.getEnderecos().add(endereco);
            pessoaRepository.save(pessoa);

        }
        return new ResponseEntity<Pessoa>(pessoa,HttpStatus.CREATED);
    }

    @Override
    public Optional<Endereco> getEndereco(Long id) {
       return enderecoRepository.findById(id);
    }

    @Override
    public void atualizarEndereco(Endereco endereco, EnderecoDTO enderecoDTO) {
       
        endereco.setLogradouro(enderecoDTO.getLogradouro());
                endereco.setCep(enderecoDTO.getCep());
                endereco.setCidade(enderecoDTO.getCidade());
                endereco.setEstado(enderecoDTO.getEstado());
                endereco.setNumero(enderecoDTO.getNumero());
        
        enderecoRepository.save(endereco);

    }

    @Override
    public List<Endereco> consultaEnderecos() {
        return enderecoRepository.findAll();
    }

}
