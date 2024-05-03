package com.attus.teste03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
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
import jakarta.transaction.Transactional;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Teste03ApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;


	String url = "http://localhost:";
	List<EnderecoDTO> enderecos1;
	List<EnderecoDTO> enderecosVazio;
	PessoaDTO pessoa1;
	PessoaDTO pessoa2;
	PessoaDTO pessoa3;
	PessoaDTO pessoa4;
	PessoaDTO pessoa5;
	
	@LocalServerPort
	int port;

	private static Long idCounter = 1L;

	/* @Autowired
	EntityManager entityManager; */

	@BeforeEach
	void setup() {
		enderecos1 = new ArrayList<EnderecoDTO>();
		enderecos1.add(new EnderecoDTO(idCounter,"Rua ABC",12345678L,123L,"São Paulo","SP"));

		enderecosVazio = new ArrayList<>();
		pessoa1 = new PessoaDTO(idCounter++,"Fulano de Tal", "01/01/1990",enderecos1);
		pessoa2 = new PessoaDTO(idCounter++,"Beltrano da Silva", "10/02/1994",enderecos1);
		pessoa3 = new PessoaDTO(idCounter++,"Adam SIlva", "02/05/1997", enderecos1);
		pessoa4 = new PessoaDTO(idCounter++,"Maurilio dos Anjos","01/10/1980",enderecos1);
		pessoa5 = new PessoaDTO(idCounter++,"Maurilio dos Anjos","01/10/1980",null);
	}
	
	/* @AfterEach
	void cleanupDatabase() {
		entityManager.createQuery("DELETE FROM Pessoa").executeUpdate();
		entityManager.createQuery("DELETE FROM Endereco").executeUpdate();
		
	} */


	@Transactional
	@Test
	@Order(1)
	void testaCadastrarPesssoa() {
		
        // Criar um objeto HttpEntity com os dados a serem enviados
        HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa1);

        // Enviar a solicitação POST
        ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		
        // Verificar se a resposta foi OK
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		
     	assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

		



		
		
	}
	
	@Transactional
	@Test
	@Order(2)
	void testaEditarPessoa() {
		// Criar um objeto HttpEntity 
        HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa2);

        // Enviar a solicitação POST
        ResponseEntity<PessoaDTO> responsePost = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		PessoaDTO pessoaAlterada = responsePost.getBody();
		pessoaAlterada.setNome("Fulano de Tal 1");

		HttpEntity<PessoaDTO> requestPessoaAlterada = new HttpEntity<>(pessoaAlterada);

		ResponseEntity<PessoaDTO> response = restTemplate.exchange(url + port + "/api/pessoa/" 
		+ pessoaAlterada.getId(), HttpMethod.PUT, requestPessoaAlterada, PessoaDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
		pessoa2 = requestPessoa.getBody();
		HttpEntity<PessoaDTO> requestPessoa2 = new HttpEntity<>(pessoa2);

		response = restTemplate.exchange(url + port + "/api/pessoa/" 
		+ 42, HttpMethod.PUT, requestPessoa2, PessoaDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		

	}

	@Transactional
	@Test
	@Order(3)
	void testaConsultaPessoa() {
		HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa3);

		ResponseEntity<PessoaDTO> responsePost = restTemplate.postForEntity
		(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);

		ResponseEntity<PessoaDTO> responseGet = restTemplate.getForEntity
		(url + port + "/api/pessoa/" + responsePost.getBody().getId(), PessoaDTO.class);

		assertEquals(responsePost.getBody(), responseGet.getBody());
		assertEquals(HttpStatus.OK, responseGet.getStatusCode());
		ResponseEntity<PessoaDTO>response = restTemplate.getForEntity(url + port + "/api/pessoa/" 
		+ 42, PessoaDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Transactional
	@Test
	@Order(4)
	void testaConsultarEnderecosPessoa() {
		HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa4);
		ResponseEntity<PessoaDTO> responsePost = restTemplate.postForEntity(
			url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		 
		PessoaDTO pessoa = responsePost.getBody(); 
		ResponseEntity<EnderecoDTO[]> responseGet = restTemplate.getForEntity(
        url + port + "/api/pessoa/" + pessoa.getId() + "/enderecos",
        EnderecoDTO[].class);

		List<EnderecoDTO> enderecos = new ArrayList<EnderecoDTO>(Arrays.asList(responseGet.getBody()));

		assertEquals(responsePost.getBody().getEnderecos(), enderecos);
		assertEquals(HttpStatus.OK, responseGet.getStatusCode());

		ResponseEntity<PessoaDTO>response = restTemplate.getForEntity(url + port + "/api/pessoa/" 
		+ 42, PessoaDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		requestPessoa = new HttpEntity<>(pessoa5);
		responsePost = restTemplate.postForEntity(
			url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		
		System.out.println(response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}

