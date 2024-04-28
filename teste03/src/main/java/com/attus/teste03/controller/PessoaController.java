package com.attus.teste03.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.attus.teste03.DTO.PessoaDTO;
import com.attus.teste03.Service.PessoaService;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.util.ErroPessoa;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
;

@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

   @PostMapping("/pessoa")
    public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaDTO pessoaDTO){

        Optional<Pessoa>  optionalPessoa = pessoaService.getByNome(pessoaDTO.getNome());

        if(optionalPessoa.isPresent()){
            return ErroPessoa.erroPessoaJaCadastrada();
        }
        
        Pessoa pessoa = pessoaService.cadastraPessoa(pessoaDTO);

        return new ResponseEntity<Pessoa>(pessoa, HttpStatus.CREATED);
    }
    
    @PutMapping("pessoa/{id}")
    public ResponseEntity<?> editarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        
        Optional<Pessoa> optionalPessoa = pessoaService.getById(id);

        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroPEssoaNaoCadastrada();
        }
        
        Pessoa pessoa = optionalPessoa.get();
        
        Pessoa pessoaAtualizada = pessoaService.editarPessoa(id, pessoa, pessoaDTO);

        return new ResponseEntity<Pessoa>(pessoaAtualizada, HttpStatus.OK);
    }

}
