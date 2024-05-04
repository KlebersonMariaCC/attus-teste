package com.attus.teste03.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.attus.teste03.DTO.PessoaDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.service.EnderecoService;
import com.attus.teste03.service.PessoaService;
import com.attus.teste03.util.ErroEndereco;
import com.attus.teste03.util.ErroPessoa;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    EnderecoService enderecoService;

   @PostMapping("/pessoa")
    public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaDTO pessoaDTO){

        Optional<Pessoa>  optionalPessoa = pessoaService.pessoaExiste(pessoaDTO);

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
            return ErroPessoa.erroPessoaNaoCadastrada();
        }
        
        Pessoa pessoa = optionalPessoa.get();
        
        pessoaService.editarPessoa(pessoa, pessoaDTO);

        return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
    }
    @GetMapping("pessoa/{id}")
    public ResponseEntity<?> consultaPessoa(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa 
        = pessoaService.consultaPessoa(id);
        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroIdNaoEncontrado(id);
        }
        Pessoa pessoa = optionalPessoa.get();

        return new ResponseEntity<Pessoa>(pessoa,HttpStatus.OK);
    }
    @GetMapping("/pessoa/{id}/enderecos")
    public ResponseEntity<?> consultarEnderecosPessoa(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = pessoaService.consultaPessoa(id);
        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroIdNaoEncontrado(id);
        }
        Pessoa pessoa = optionalPessoa.get();
        
        return new ResponseEntity<List<Endereco>>(pessoa.getEnderecos(),HttpStatus.OK);
    }

    @PostMapping("/pessoa/{idPessoa}/endereco/principal")
    public ResponseEntity<?> escolheEnderecoPrincipal(@PathVariable Long idPessoa, @RequestParam Long idEndereco) {
        Optional<Pessoa> optionalPessoa = pessoaService.consultaPessoa(idPessoa);
        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroIdNaoEncontrado(idPessoa);
        }
        Pessoa pessoa = optionalPessoa.get();
        Optional <Endereco> optionalEndereco = enderecoService.getEndereco(idEndereco);
        if(!optionalEndereco.isPresent()){
            return ErroEndereco.erroPessoaNaoTemEndereco(pessoa.getId(),idEndereco);
        }
        
        Pessoa pessoaAlterada = pessoaService.setEnderecoPrincipal(pessoa, idEndereco);
        
        return new ResponseEntity<Pessoa>(pessoaAlterada,HttpStatus.OK);
    }

    @GetMapping("/pessoa/{idPessoa}/endereco/principal")
    public ResponseEntity<?> consultaEnderecoPrincipal(@PathVariable Long idPessoa) {
        Optional<Pessoa> optionalPessoa = pessoaService.consultaPessoa(idPessoa);
        if(!optionalPessoa.isPresent()){
            return ErroPessoa.erroIdNaoEncontrado(idPessoa);
        }
        Pessoa pessoa = optionalPessoa.get();
        if (pessoa.getIdEnderecoPrincipal() <=0){
            return ErroEndereco.erroPessoaNaoEscolheuEndereco(idPessoa);
        }
        Endereco endereco = enderecoService.getEndereco(idPessoa).get();

        return new ResponseEntity<Endereco>(endereco,HttpStatus.OK);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<?> consultaPessoas() {
        List<Pessoa> pessoas = pessoaService.consultaPessoas();
        if(pessoas.isEmpty()){
            return ErroPessoa.erroNenhumaPessoaCadastrada();
        }
        return new ResponseEntity<List<Pessoa>>(pessoas,HttpStatus.OK);
    }
    
    
    
    

}
