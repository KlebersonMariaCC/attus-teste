package com.attus.teste03.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String nome;

    Date dataNascimento;
    
    @OneToMany
    List<Endereco> enderecos;

    Long IdEnderecoPrncipal;

}
