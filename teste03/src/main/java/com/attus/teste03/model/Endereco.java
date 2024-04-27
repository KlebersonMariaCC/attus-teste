package com.attus.teste03.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id ;

    String logradouro;

    Long cep;

    Long numero;

    String cidade;
    
    String estado;
    

}
