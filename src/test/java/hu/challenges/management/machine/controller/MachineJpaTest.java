package hu.challenges.management.machine.controller;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.challenges.management.ManagementApplication;
import hu.challenges.management.machine.entity.Machine;
import hu.challenges.management.machine.service.MachineRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ManagementApplication.class)
@ActiveProfiles("test")
public class MachineJpaTest {

	@Resource
	private MachineRepository machineRepository;

	private Machine baseMachine;

	@BeforeEach
	void initBaseMachine() {
		OffsetDateTime now = OffsetDateTime.now();

		Machine machine = new Machine();
		machine.setName(RandomStringUtils.randomAlphanumeric(10));
		machine.setActive(true);
		machine.setCreatedAt(now);
		machine.setUpdatedAt(now);

		baseMachine = machineRepository.save(machine);
	}

	@AfterEach
	void cleanupMachines() {
		machineRepository.deleteAll();
	}

	@Test
	void checkCreatedMachine() {
		Optional<Machine> optionalMachine = machineRepository.findById(baseMachine.getId());

		assertTrue(optionalMachine.isPresent());
		assertEquals(baseMachine.getName(), optionalMachine.get().getName());
	}

	@Test
	void updateMachine() {
		String randomName = RandomStringUtils.randomAlphanumeric(10);
		OffsetDateTime now = OffsetDateTime.now();

		baseMachine.setName(randomName);
		baseMachine.setUpdatedAt(now);
		baseMachine.setActive(false);

		Machine savedMachine = machineRepository.save(baseMachine);

		// Find for changes

		Optional<Machine> optionalMachine = machineRepository.findById(savedMachine.getId());

		assertTrue(optionalMachine.isPresent());
		assertEquals(randomName, optionalMachine.get().getName());
		assertEquals(now, optionalMachine.get().getUpdatedAt());
		assertFalse(optionalMachine.get().getActive());
		assertNotEquals(baseMachine.getCreatedAt(), optionalMachine.get().getUpdatedAt());
	}

	@Test
	void findByActiveOrderByUpdatedAtDesc() {
		assertEquals(1, machineRepository.findByActiveOrderByUpdatedAtDesc(true).size());

		baseMachine.setActive(false);
		machineRepository.save(baseMachine);

		assertEquals(0, machineRepository.findByActiveOrderByUpdatedAtDesc(true).size());
		assertEquals(1, machineRepository.findByActiveOrderByUpdatedAtDesc(false).size());
	}
}