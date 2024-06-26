package com.attus.teste03.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    Long id;
    String nome;
    String dataNascimento;
    List<EnderecoDTO> enderecos;
    Long idEnderecoPrincipal;
    
    public PessoaDTO(String nome, String dataNascimento, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
        this.idEnderecoPrincipal = -1L;
    }

    public PessoaDTO(Long id, String nome, String dataNascimento, List<EnderecoDTO> enderecos) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
        this.idEnderecoPrincipal = -1L;
    }

    

}
