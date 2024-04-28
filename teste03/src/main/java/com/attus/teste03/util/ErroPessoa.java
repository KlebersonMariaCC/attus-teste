package com.attus.teste03.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ErroPessoa {

    static final String PESSOA_JA_CADASTRADA = "Pessoa já cadastrada.";
    static final String PESSOA_NAO_CADASTRADA = "Pessoa não cadastrada";


    public static ResponseEntity<CustomErrorType> erroPessoaJaCadastrada() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(PESSOA_JA_CADASTRADA), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static ResponseEntity<CustomErrorType> erroPEssoaNaoCadastrada(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(PESSOA_NAO_CADASTRADA), HttpStatus.NOT_FOUND);
    }

}
