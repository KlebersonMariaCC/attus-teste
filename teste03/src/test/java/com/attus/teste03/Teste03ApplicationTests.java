package com.attus.teste03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.attus.teste03.util.CustomErrorType;

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


	EnderecoDTO endereco1;

	EnderecoDTO endereco2;
	@LocalServerPort
	int port;

	private static Long idCounter = 1L;
	private static Long idEnderecoCounter = 1L;



	@BeforeAll
	void setup() {
		enderecos1 = new ArrayList<EnderecoDTO>();
		enderecos1.add(new EnderecoDTO(idEnderecoCounter++,"Rua ABC",12345678L,123L,"São Paulo","SP"));
		enderecos1.add(new EnderecoDTO(idEnderecoCounter++,"Rua DEF",8765432L,321L,"São Paulo","SP"));



		enderecosVazio = new ArrayList<>();
		pessoa1 = new PessoaDTO(idCounter++,"Fulano de Tal", "01/01/1990",enderecos1);
		pessoa2 = new PessoaDTO(idCounter++,"Beltrano da Silva", "10/02/1994",enderecosVazio);
		
		endereco1 = new EnderecoDTO("Rua FGH",1357902L,213L,"São Paulo","SP");
		endereco2 = new EnderecoDTO("Rua IJK",1357902L,213L,"São Paulo","SP");
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
		
		 
		 
		ResponseEntity<EnderecoDTO[]> responseEnderecos = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/enderecos",
        EnderecoDTO[].class,pessoa1.getId());

		assertEquals(HttpStatus.OK, responseEnderecos.getStatusCode());

		ResponseEntity<PessoaDTO> response = restTemplate.getForEntity(url + port + 
		"/api/pessoa/{id}/enderecos", PessoaDTO.class, 42L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		HttpEntity<PessoaDTO> request = new HttpEntity<>(pessoa2);
		response = restTemplate.postForEntity(
			url + port + "/api/pessoa", request, PessoaDTO.class);
		/* System.out.println(response);
		ResponseEntity<CustomErrorType> responseError = restTemplate.getForEntity(url + port + 
			"/api/pessoa/{id}/enderecos", CustomErrorType.class, pessoa2.getId());
		System.out.println(responseError);

		assertEquals(HttpStatus.NOT_FOUND, responseError.getStatusCode()); */
	}
	
	@Test
	@Order(5)
	void testaEscolheEnderecoPrincipal(){
		
		
        // Enviar a solicitação GET

		EnderecoDTO[] enderecosArray = restTemplate.getForObject(url + port + "/api/pessoa/{id}/enderecos",
		 EnderecoDTO[].class,pessoa1.getId()); 
		  
		List<EnderecoDTO> enderecos  = Arrays.asList(enderecosArray);

		HttpEntity<PessoaDTO> request = new HttpEntity<>(new PessoaDTO());

		ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,request,
        PessoaDTO.class,pessoa1.getId(),enderecos.get(0).getId());
		
		
		assertEquals(enderecos.get(0).getId(),response.getBody().getIdEnderecoPrincipal());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	
		
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
		  
		List<EnderecoDTO> enderecos  = Arrays.asList(enderecosArray);
		

		

		ResponseEntity<EnderecoDTO> response = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal"
		,EnderecoDTO.class,pessoa1.getId());


		// aqui subtraimos o tamanho da lista pois fizemos um update e os ids de endereco são auto incrementaveis, cada save, um novo id

		assertEquals(enderecos.get(0).getId() - enderecos.size(), response.getBody().getId());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		response = restTemplate.getForEntity(
        url + port + "/api/pessoa/{id}/endereco/principal?idEndereco={idEndereco}"
		 ,
        EnderecoDTO.class,pessoa2.getId(),enderecos.get(0).getId());

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Order(7)
	void testaConsultaPessoas() {

		ResponseEntity<PessoaDTO[]> response = restTemplate.getForEntity(url + port + "/api/pessoas", PessoaDTO[].class);

		List<PessoaDTO> pessoas = Arrays.asList(response.getBody());

		assertNotNull(pessoas);
	}


	@Test
	@Order(8)
	void testaCadastraEndereco() {
		ResponseEntity<PessoaDTO> response = restTemplate.postForEntity(url + port + 
		"/api/pessoa/{id}/endereco", endereco1, PessoaDTO.class,pessoa2.getId());
		PessoaDTO pessoa = response.getBody();

		assertNotNull(pessoa.getEnderecos());
		
		response = restTemplate.postForEntity(url + port + 
		"/api/pessoa/{id}/endereco", endereco1, PessoaDTO.class,42L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	@Order(9)
	void testaEditarEndereco() {
		ResponseEntity<EnderecoDTO[]> responseArray = restTemplate.getForEntity(url + port + 
		"/api/pessoa/{id}/enderecos", EnderecoDTO[].class,pessoa2.getId());
		List<EnderecoDTO> enderecos = Arrays.asList(responseArray.getBody());
		HttpEntity<EnderecoDTO> request = new HttpEntity<EnderecoDTO>(endereco2);
		 ResponseEntity<EnderecoDTO> response = restTemplate.exchange(url + port + 
		"/api/endereco/{id}",HttpMethod.PUT,request, EnderecoDTO.class,enderecos.get(0).getId());

		assertNotEquals(enderecos.get(0),response.getBody());

	}
}

