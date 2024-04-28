package com.attus.teste03.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPessoa {

    static final String PESSOA_JA_CADASTRADA = "Pessoa já cadastrada.";
    static final String PESSOA_NAO_CADASTRADA = "Pessoa não cadastrada";
    static final String ID_NAO_ENCONTRADO = "Pessoa com o id %d Nâo encontrado";


    public static ResponseEntity<CustomErrorType> erroPessoaJaCadastrada() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(PESSOA_JA_CADASTRADA), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static ResponseEntity<CustomErrorType> erroPessoaNaoCadastrada(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(PESSOA_NAO_CADASTRADA), HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<?> erroIdNaoEncontrado(Long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ID_NAO_ENCONTRADO,id)), HttpStatus.NOT_FOUND);
    }

}
