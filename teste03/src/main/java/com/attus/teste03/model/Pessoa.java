package com.attus.teste03.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String nome;

    LocalDate dataNascimento;
    
    @OneToMany
    List<Endereco> enderecos;

    Long IdEnderecoPrncipal;

    public Pessoa(String nome, LocalDate dataNascimento, List<Endereco> enderecos, Long idEnderecoPrncipal) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
        IdEnderecoPrncipal = idEnderecoPrncipal;
    }

    

}
