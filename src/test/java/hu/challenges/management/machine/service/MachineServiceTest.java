package hu.challenges.management.machine.service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.challenges.management.machine.entity.Machine;
import hu.challenges.management.machine.model.MachineDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineServiceTest {

	private static final UUID randomActiveMachineId = UUID.randomUUID();
	private static final UUID randomInactiveMachineId = UUID.randomUUID();

	@Mock
	private MachineRepository machineRepository;

	@InjectMocks
	private MachineServiceImpl machineService;


	@Test
	void fetchAll() {
		when(machineRepository.findByActiveOrderByUpdatedAtDesc(anyBoolean()))
				.thenReturn(createMachines());

		assertEquals(2, machineService.fetchAll().size());
		assertEquals(
				randomActiveMachineId,
				machineService.fetchAll().stream()
						.filter(m -> Boolean.TRUE.equals(m.getActive()))
						.findFirst()
						.get()
						.getId()
		);

		assertEquals(
				randomInactiveMachineId,
				machineService.fetchAll().stream()
						.filter(m -> Boolean.FALSE.equals(m.getActive()))
						.findFirst()
						.get()
						.getId()
		);
	}

	@Test
	void findById() {
		when(machineRepository.findById(any()))
				.thenReturn(Optional.of(createActiveMachine()));

		assertEquals(
				randomActiveMachineId,
				machineService.findById(UUID.randomUUID()).getId()
		);
	}

	@Test
	void create() {
		when(machineRepository.save(any()))
				.thenReturn(Optional.of(createActiveMachine()).get());

		assertEquals(
				randomActiveMachineId,
				machineService.create(new MachineDTO()).getId()
		);
	}

	@Test
	void update() {
		when(machineRepository.findById(any()))
				.thenReturn(Optional.of(createActiveMachine()));

		when(machineRepository.save(any()))
				.thenReturn(Optional.of(createActiveMachine()).get());

		assertEquals(
				randomActiveMachineId,
				machineService.update(new MachineDTO(), UUID.randomUUID()).getId()
		);
	}

	@Test
	void remove() {
		when(machineRepository.findById(any()))
				.thenReturn(Optional.of(createActiveMachine()));

		when(machineRepository.save(any()))
				.thenReturn(Optional.of(createActiveMachine()).get());

		assertEquals(
				randomActiveMachineId,
				machineService.remove(UUID.randomUUID()).getId()
		);
	}

	private List<Machine> createMachines() {
		return Arrays.asList(createActiveMachine(), createInactiveMachine());
	}

	private Machine createActiveMachine() {
		final Machine randomActiveMachine = new Machine();

		randomActiveMachine.setId(randomActiveMachineId);
		randomActiveMachine.setCreatedAt(OffsetDateTime.now());
		randomActiveMachine.setUpdatedAt(OffsetDateTime.now());
		randomActiveMachine.setName(String.valueOf(new Random().nextLong()));
		randomActiveMachine.setActive(true);

		return randomActiveMachine;
	}

	private Machine createInactiveMachine() {
		final Machine randomInactiveMachine = new Machine();

		randomInactiveMachine.setId(randomInactiveMachineId);
		randomInactiveMachine.setCreatedAt(OffsetDateTime.now());
		randomInactiveMachine.setUpdatedAt(OffsetDateTime.now());
		randomInactiveMachine.setName(String.valueOf(new Random().nextLong()));
		randomInactiveMachine.setActive(false);

		return randomInactiveMachine;
	}
}