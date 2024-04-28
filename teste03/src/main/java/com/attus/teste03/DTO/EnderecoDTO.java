package com.attus.teste03.DTO;

import lombok.Data;

@Data
public class EnderecoDTO {

    String logradouro;

    Long cep;

    Long numero;

    String cidade;
    
    String estado;

}
