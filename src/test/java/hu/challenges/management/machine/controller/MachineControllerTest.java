package hu.challenges.management.machine.controller;

import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import hu.challenges.management.machine.model.MachineDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Remove disabled only, if database container exists.
 */
@Disabled
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MachineControllerTest {

	@LocalServerPort
	private int port;

	@Autowired

	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
		assertNotNull(restTemplate);
	}

	@Test
	public void putMachineAndRemove() {
		final String baseUrl = "http://localhost:" + port + "/management/machine";

		// Create body for create machine
		String randomName = String.valueOf(new Random().nextLong());
		String body = "{\"name\": \"" + randomName + "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(body, headers);

		HttpEntity<MachineDTO> response = restTemplate.exchange(
				baseUrl,
				HttpMethod.POST,
				entity,
				MachineDTO.class);

		MachineDTO machine = response.getBody();
		assertNotNull(machine);
		assertEquals(randomName, machine.getName());

		// Put machine id into query params for update

		randomName = String.valueOf(new Random().nextLong());
		body = "{\"name\": \"" + randomName + "\"}";
		entity = new HttpEntity<>(body, headers);
		response = restTemplate.exchange(
				baseUrl + "/" + machine.getId(),
				HttpMethod.PUT,
				entity,
				MachineDTO.class);

		machine = response.getBody();
		assertNotNull(machine);
		assertEquals(randomName, machine.getName());

		// Remove machine by setup active flag

		restTemplate.exchange(
				baseUrl + "/" + machine.getId(),
				HttpMethod.DELETE,
				entity,
				String.class);

		// Get active false

		response = restTemplate.exchange(
				baseUrl + "/" + machine.getId(),
				HttpMethod.GET,
				entity,
				MachineDTO.class);

		machine = response.getBody();
		assertNotNull(machine);
		assertNull(machine.getId());
	}


}