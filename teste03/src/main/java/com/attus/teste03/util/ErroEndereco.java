package com.attus.teste03.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEndereco {

    
    static final String PESSOA_JA_TEM_O_ENDERECO = "A pessoa com o id %d já possui o endereco.";
    static final String ENDERECO_NAO_CADASTRADO = "O endereço com o id %d não está cadastrado.";
    static final String PESSOA_NAO_TEM_O_ENDERECO = "A pessoa com o id %d não possui o endereco  de id %d.";
    static final String PESSOA_NAO_ESCOLHEU_O_ENDERECO = "A pessoa com o id %d não escolheu o endereco principal.";
    static final String NENHUM_ENDERECO_CADSTRADO = "Nenhum endereço foi casdtrado.";
    
    
    public static ResponseEntity<CustomErrorType> erroPessoaJaTemEndereco(Long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(PESSOA_JA_TEM_O_ENDERECO, id)),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> erroEnderecoNaoCadastrado(Long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ENDERECO_NAO_CADASTRADO, id)),HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> erroPessoaNaoTemEndereco(Long idPessoa, Long idEndereco) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(PESSOA_NAO_TEM_O_ENDERECO,idPessoa, idEndereco)),HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> erroPessoaNaoEscolheuEndereco(Long idPessoa) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(PESSOA_NAO_ESCOLHEU_O_ENDERECO, idPessoa)),HttpStatus.NOT_FOUND);
        
    }

    public static ResponseEntity<?> erroNenhumEnderecoCadastrado() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(NENHUM_ENDERECO_CADSTRADO),HttpStatus.NOT_FOUND);
    }

}
