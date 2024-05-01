package com.attus.teste03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.attus.teste03.DTO.EnderecoDTO;
import com.attus.teste03.DTO.PessoaDTO;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class Teste03ApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	EntityManager entityManager;

	String url = "http://localhost:";
	List<EnderecoDTO> enderecos1;
	PessoaDTO pessoa1;
	PessoaDTO pessoa2;
	
	@LocalServerPort
	int port;

	@Test
	void contextLoads(){

	}

	@BeforeEach
	void setup() {
		enderecos1 = new ArrayList<EnderecoDTO>();
		enderecos1.add(new EnderecoDTO("Rua ABC",12345678L,123L,"São Paulo","SP"));
		pessoa1 = new PessoaDTO("Fulano de Tal", "01/01/1990",enderecos1);
		pessoa2 = new PessoaDTO("Beltrano da Silva", "10/02/1994",enderecos1);
		
	}
	
	/* @AfterEach
	public void cleanupDatabase() {
		entityManager.createQuery("DELETE FROM Pessoa").executeUpdate();
		entityManager.createQuery("DELETE FROM Endereco").executeUpdate();
		
	} */



	@Test
	void testaCadastrarPesssoa() {
		
        // Criar um objeto HttpEntity com os dados a serem enviados
        HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa2);

        // Enviar a solicitação POST
        ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		System.out.println(response.getBody());
        // Verificar se a resposta foi OK
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		System.out.println(response.getBody());
     	assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

		



		
		
	}
	
	@Test
	void testaEditarPessoa() {
		 // Criar um objeto HttpEntity cojakarta.persistence.TransactionRequiredException: Executing an update/delete querym os dados a serem enviados
        HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa1);

        // Enviar a solicitação POST
        ResponseEntity<PessoaDTO> responsePost = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		PessoaDTO pessoaAlterada = responsePost.getBody();
		pessoaAlterada.setNome("Fulano de Tal 1");

		HttpEntity<PessoaDTO> requestPessoaAlterada = new HttpEntity<>(pessoaAlterada);

		ResponseEntity<PessoaDTO> response = restTemplate.exchange(url + port + "/api/pessoa/" 
		+ pessoaAlterada.getId(), HttpMethod.PUT, requestPessoaAlterada, PessoaDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

		HttpEntity<PessoaDTO> requestPessoa2 = new HttpEntity<>(this.pessoa2);


		
		/* response = restTemplate.exchange(url + port + "/api/pessoa/"
		 + 42, HttpMethod.PUT, requestPessoa2, PessoaDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); */
		

	}
}

