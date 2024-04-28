package com.attus.teste03.DTO;

import java.util.List;

import lombok.Data;


@Data
public class PessoaDTO {

    String nome;
    String dataNascimento;
    List<EnderecoDTO> enderecos;

}
