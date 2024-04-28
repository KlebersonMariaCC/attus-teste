package com.attus.teste03.Service;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.attus.teste03.DTO.PessoaDTO;
import com.attus.teste03.model.Endereco;
import com.attus.teste03.model.Pessoa;
import com.attus.teste03.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService{
    
    @Autowired
    PessoaRepository pessoaRepository;
    
    @Autowired
    EnderecoService enderecoService;

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Optional<Pessoa> getByNome(String nome) {
        return pessoaRepository.findByNome(nome);
    }

    @Override
    public Pessoa cadastraPessoa(PessoaDTO pessoaDTO) {

        

        List<Endereco> enderecos = enderecoService.getEnderecos(pessoaDTO.getEnderecos());
        Long idEnderecoPrincipal = Long.valueOf(-1);
        LocalDate data = LocalDate.parse(pessoaDTO.getDataNascimento(), dateFormat);
        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(),
       data , enderecos,idEnderecoPrincipal);

       pessoaRepository.save(pessoa);

       return pessoa;
        
        

    }

    @Override
    public Optional<Pessoa> getById(Long id) {
        return pessoaRepository.findById(id);
    }

    @Override
    public Pessoa editarPessoa(Long id, Pessoa pessoa, PessoaDTO pessoaDTO) {
        LocalDate data = LocalDate.parse(pessoaDTO.getDataNascimento(), dateFormat);
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEnderecos(enderecoService.getEnderecos(pessoaDTO.getEnderecos()));
        pessoa.setDataNascimento(data);

        pessoaRepository.save(pessoa);

        return pessoa;
        
    }

}
