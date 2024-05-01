package com.attus.teste03.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    Long id ;

    String logradouro;

    Long cep;

    Long numero;

    String cidade;
    

    String estado;

    public Endereco(String logradouro, Long cep, Long numero, String cidade, String estado) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    

    

}