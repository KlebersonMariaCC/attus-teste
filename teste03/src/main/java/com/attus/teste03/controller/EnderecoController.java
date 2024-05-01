package com.attus.teste03.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.service.EnderecoService;
import com.attus.teste03.service.PessoaService;
import com.attus.teste03.util.ErroEndereco;
import com.attus.teste03.util.ErroPessoa;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api")
public class EnderecoController {
    
    @Autowired
    EnderecoService enderecoService;

    @Autowired
    PessoaService pessoaService;

    @PostMapping("/pessoa/{id}/endereco")
    public ResponseEntity<?> cadastraEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        Optional<Pessoa> optionalPessoa = pessoaService.consultaPessoa(id);
        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroIdNaoEncontrado(id);
        }
        Pessoa pessoa = optionalPessoa.get();
        
        ResponseEntity<?> pessoaAtualizada = enderecoService.cadastraEndereco(pessoa, enderecoDTO);

        return pessoaAtualizada;

    }

    @PutMapping("/endereco/{id}")
    public ResponseEntity<?> atualizaEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        
        Optional<Endereco> optionalEndereco = enderecoService.getEndereco(id);

        if(!optionalEndereco.isPresent()){
            return ErroEndereco.erroEnderecoNaoCadastrado(id);
        }
        Endereco endereco = optionalEndereco.get();

        enderecoService.atualizarEndereco(endereco, enderecoDTO);

        return new ResponseEntity<Endereco>(endereco,HttpStatus.OK);
    }

    @GetMapping("/endereco/{id}")
    public ResponseEntity<?> consultarEndereco(@PathVariable Long id) {
            Optional<Endereco> optionalEndereco = enderecoService.getEndereco(id);

            if (!optionalEndereco.isPresent()){
                return ErroEndereco.erroEnderecoNaoCadastrado(id);
            }
            Endereco endereco = optionalEndereco.get();
            
            return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);


    }

    @GetMapping("/enderecos")
    public ResponseEntity<?> consultarEnderecos() {
        List<Endereco> enderecos = enderecoService.consultaEnderecos();
        if(enderecos.isEmpty()){
            return ErroEndereco.erroNenhumEnderecoCadastrado();
        }
        return new ResponseEntity<List<Endereco>>(enderecos,HttpStatus.OK);
    }
    
    
    



}
