package com.attus.teste03.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Pessoa cadastraPessoa(PessoaDTO pessoaDTO) {

        

        List<Endereco> enderecos = enderecoService.getEnderecos(pessoaDTO.getEnderecos());
        Long idEnderecoPrincipal = -1L;
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
    public Pessoa editarPessoa( Pessoa pessoa, PessoaDTO pessoaDTO) {
        LocalDate data = LocalDate.parse(pessoaDTO.getDataNascimento(), dateFormat);
        pessoa.setNome(pessoaDTO.getNome());
        List<Endereco> enderecos = enderecoService.updateEnderecos(pessoaDTO.getEnderecos());
        pessoa.setEnderecos(enderecos);
        pessoa.setDataNascimento(data);

        return pessoaRepository.save(pessoa);

        
        
    }

    @Override
    public Optional<Pessoa> consultaPessoa(Long id) {
        return pessoaRepository.findById(id);
    }

    @Override
    public Optional<Pessoa> pessoaExiste(PessoaDTO pessoaDTO) {
        LocalDate data = LocalDate.parse(pessoaDTO.getDataNascimento(), dateFormat);        

        return pessoaRepository.findByNomeAndDataNascimento(
            pessoaDTO.getNome(),data
        );
    }

    @Override
    public Pessoa setEnderecoPrincipal(Pessoa pessoa, Long idEndereco) {
        pessoa.setIdEnderecoPrncipal(idEndereco);
        return pessoaRepository.save(pessoa);
        
    }

    @Override
    public List<Pessoa> consultaPessoas() {
        return pessoaRepository.findAll();
    }

}
