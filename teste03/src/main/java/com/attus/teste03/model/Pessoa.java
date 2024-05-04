package com.attus.teste03.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    Long id;

    String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento;
    
    @OneToMany
    List<Endereco> enderecos;

    Long idEnderecoPrincipal;

    public Pessoa(String nome, LocalDate dataNascimento, List<Endereco> enderecos, Long idEnderecoPrincipal) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
        this.idEnderecoPrincipal = idEnderecoPrincipal;
    }

    

}
