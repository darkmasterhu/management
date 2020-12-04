package hu.challenges.management.machine.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import hu.challenges.management.ManagementApplication;
import hu.challenges.management.machine.model.MachineDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MachineRestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private UUID baseMachineId;

	@BeforeEach
	public void init() {
		final String baseUrl = getBaseUrl() + "/machine";
		final String randomName = RandomStringUtils.randomAlphanumeric(12);

		String body = "{\"name\": \"" + randomName + "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(body, headers);

		HttpEntity<MachineDTO> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.POST,
				entity,
				MachineDTO.class
		);

		MachineDTO machine = response.getBody();

		assertNotNull(machine);
		assertEquals(randomName, machine.getName());
		assertNotNull(machine.getId());
		assertNotNull(machine.getCreatedAt());
		assertNotNull(machine.getUpdatedAt());

		baseMachineId = machine.getId();
	}

	@AfterEach
	public void clear() {
		final String baseUrl = getBaseUrl() + "/machine/" + baseMachineId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		restTemplate.exchange(
				baseUrl,
				HttpMethod.DELETE,
				entity,
				MachineDTO.class
		);
	}

	@Test
	void getMachine() {
		final String baseUrl = getBaseUrl() + "/machine/" + baseMachineId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<MachineDTO> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.GET,
				entity,
				MachineDTO.class
		);

		MachineDTO machine = response.getBody();

		assertNotNull(machine);
		assertEquals(baseMachineId, machine.getId());
	}

	@Test
	void updateMachine() {
		final String baseUrl = getBaseUrl() + "/machine/" + baseMachineId;
		final String randomName = RandomStringUtils.randomAlphanumeric(12);

		String body = "{\"name\": \"" + randomName + "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(body, headers);

		HttpEntity<MachineDTO> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.PUT,
				entity,
				MachineDTO.class
		);

		MachineDTO machine = response.getBody();

		assertNotNull(machine);
		assertEquals(baseMachineId, machine.getId());
		assertEquals(randomName, machine.getName());
	}

	@Test
	@SuppressWarnings("rawtypes")
	void removeMachine() {
		final String baseUrl = getBaseUrl() + "/machine/" + baseMachineId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.DELETE,
				entity,
				String.class
		);

		assertEquals(HttpStatus.OK, ((ResponseEntity) response).getStatusCode());
	}

	@Test
	@SuppressWarnings("rawtypes")
	void getMachines() {
		final String baseUrl = getBaseUrl() + "/machines";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<List> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.GET,
				entity,
				List.class
		);

		assertNotNull(response.getBody());

		// 2: MyMachine test data from V3__init_datas.sql and one from init()
		assertEquals(2, response.getBody().size());
	}

	private String getBaseUrl() {
		return "http://localhost:" + port + "/management";
	}
}