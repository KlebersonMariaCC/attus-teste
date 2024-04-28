package com.attus.teste03.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.Service.EnderecoService;
import com.attus.teste03.Service.PessoaService;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.util.ErroPessoa;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    



}
