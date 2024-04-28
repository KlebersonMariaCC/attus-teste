package com.attus.teste03.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    Long id;

    String logradouro;

    Long cep;

    Long numero;

    String cidade;
    
    String estado;

    public EnderecoDTO(String logradouro, Long cep, Long numero, String cidade, String estado) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    

}
