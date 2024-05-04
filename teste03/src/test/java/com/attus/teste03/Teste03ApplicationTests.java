package com.attus.teste03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
	PessoaDTO pessoa6;	
	@LocalServerPort
	int port;

	private static Long idCounter = 1L;
	private static Long idEnderecoCounter = 1L;


	/* @Autowired
	EntityManager entityManager; */

	@BeforeAll
	void setup() {
		enderecos1 = new ArrayList<EnderecoDTO>();
		enderecos1.add(new EnderecoDTO(idEnderecoCounter++,"Rua ABC",12345678L,123L,"São Paulo","SP"));
		enderecos1.add(new EnderecoDTO(idEnderecoCounter++,"Rua DEF",8765432L,321L,"São Paulo","SP"));



		enderecosVazio = new ArrayList<>();
		pessoa1 = new PessoaDTO(idCounter++,"Fulano de Tal", "01/01/1990",enderecos1);
		pessoa2 = new PessoaDTO(idCounter++,"Beltrano da Silva", "10/02/1994",null);
		/* pessoa3 = new PessoaDTO(idCounter++,"Adam SIlva", "02/05/1997", enderecos1); */
		/* pessoa4 = new PessoaDTO(idCounter++,"Maurilio dos Anjos","01/10/1980",enderecos1); */
		/* pessoa5 = new PessoaDTO(idCounter++,"Maurilio dos Anjos","01/10/1980",null); */
		/* pessoa6 =  new PessoaDTO(idCounter++,"Renan de Almeida ","14/05/1966",enderecos1); */
	}
	
	/* @AfterEach
	void cleanupDatabase() {
		entityManager.createQuery("DELETE FROM Pessoa").executeUpdate();
		entityManager.createQuery("DELETE FROM Endereco").executeUpdate();
		
	} */


	
	@Test
	@Order(1)
	void testaCadastrarPessoa() {
		
        // Criar um objeto HttpEntity com os dados a serem enviados
        HttpEntity<PessoaDTO> requestPessoa = new HttpEntity<>(pessoa1);

        // Enviar a solicitação POST
        ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		
        // Verificar se a resposta foi OK
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		response = restTemplate.postForEntity(url + port + "/api/pessoa", requestPessoa, PessoaDTO.class);
		
     	assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

		



		
		
	}
	
	
	@Test
	@Order(2)
	void testaEditarPessoa() {

        // Enviar a solicitação GET
        ResponseEntity<PessoaDTO> response = restTemplate.getForEntity(url + port + "/api/pessoa/{id}", PessoaDTO.class,pessoa1.getId());
		PessoaDTO pessoaAlterada = response.getBody();

		pessoaAlterada.setNome("Fulano de Tal 1");

		HttpEntity<PessoaDTO> request = new HttpEntity<>(pessoaAlterada);

		response = restTemplate.exchange(url + port + "/api/pessoa/{id}" 
		, HttpMethod.PUT, request, PessoaDTO.class,pessoaAlterada.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

		response = restTemplate.exchange(url + port + "/api/pessoa/" 
		+ 42, HttpMethod.PUT, request, PessoaDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		

	}

	
	@Test
	@Order(3)
	void testaConsultaPessoa() {
		
		ResponseEntity<PessoaDTO> response = restTemplate.getForEntity
		(url + port + "/api/pessoa/{id}", PessoaDTO.class,pessoa1.getId());

		
		assertEquals(HttpStatus.OK, response.getStatusCode());

		response = restTemplate.getForEntity(url + port + "/api/pessoa/{id}",
		 PessoaDTO.class,42L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Order(4)
	void testaConsultarEnderecosPessoa() {
		
		 
		 
		ResponseEntity<EnderecoDTO[]> responseGet = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/enderecos",
        EnderecoDTO[].class,pessoa1.getId());

		assertEquals(HttpStatus.OK, responseGet.getStatusCode());

		ResponseEntity<PessoaDTO>response = restTemplate.getForEntity(url + port + 
		"/api/pessoa/{id}/enderecos", PessoaDTO.class, 42L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		HttpEntity<PessoaDTO> request = new HttpEntity<>(pessoa2);
		response = restTemplate.postForEntity(
			url + port + "/api/pessoa", request, PessoaDTO.class);
		
		response = restTemplate.getForEntity(url + port + 
			"/api/pessoa/{id}/enderecos", PessoaDTO.class, pessoa2.getId());


		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	@Order(5)
	void testaEscolheEnderecoPrincipal(){
		
		
        // Enviar a solicitação GET

		EnderecoDTO[] enderecosArray = restTemplate.getForObject(url + port + "/api/pessoa/{id}/enderecos",
		 EnderecoDTO[].class,pessoa1.getId()); 
		System.out.println(enderecosArray);  
		List<EnderecoDTO> enderecos  = Arrays.asList(enderecosArray);
		System.out.println(enderecos);

		HttpEntity<PessoaDTO> request = new HttpEntity<>(new PessoaDTO());

		ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,request,
        PessoaDTO.class,pessoa1.getId(),enderecos.get(0).getId());
		
		
		assertEquals(enderecos.get(0).getId(),response.getBody().getIdEnderecoPrincipal());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		
		System.out.println(pessoa2);
		
		response = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}",
        PessoaDTO.class,pessoa2.getId());
		

		response = restTemplate.postForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,request,
        PessoaDTO.class,42L,enderecos.get(0).getId());

		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

		response = restTemplate.postForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,request,
        PessoaDTO.class,pessoa1.getId(),42);

		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

	}

	@Test
	@Order(6)
	void testaconsultaEnderecoPrincipal() {

		EnderecoDTO[] enderecosArray = restTemplate.getForObject(url + port + "/api/pessoa/{id}/enderecos",
		 EnderecoDTO[].class,pessoa1.getId()); 
		System.out.println(enderecosArray);  
		List<EnderecoDTO> enderecos  = Arrays.asList(enderecosArray);
		System.out.println(enderecos);

		

		ResponseEntity<EnderecoDTO> response = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal"
		,EnderecoDTO.class,pessoa1.getId());

		assertEquals(enderecos.get(0).getId(), response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		response = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,
        EnderecoDTO.class,pessoa2.getId(),enderecos.get(0).getId());

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}

