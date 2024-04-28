package com.attus.teste03.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEndereco {

    
    static final String PESSOA_JA_TEM_O_ENDERECO = "A pessoa com o id %d já possui o endereco.";
    static final String ENDERECO_NAO_CADASTRADO = "O endereço com o id %d não está cadastrado";

    public static ResponseEntity<CustomErrorType> erroPessoaJaTemEndereco(Long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(PESSOA_JA_TEM_O_ENDERECO, id)),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> erroEnderecoNaoCadastrado(Long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ENDERECO_NAO_CADASTRADO, id)),HttpStatus.NOT_FOUND);
    }


}
