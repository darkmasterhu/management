package hu.challenges.management.machine.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.challenges.management.machine.model.MachineDTO;
import hu.challenges.management.machine.service.MachineService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MachineController {

	private final MachineService machineService;

	@GetMapping("/machines")
	public List<MachineDTO> listMachines() {
		return machineService.fetchAll().stream()
				.map(MachineDTO::from)
				.collect(Collectors.toList());
	}

	@GetMapping("/machine/{id}")
	public MachineDTO getMachine(@PathVariable UUID id) {
		return MachineDTO.from(machineService.findById(id));
	}

	@PostMapping("/machine")
	public MachineDTO createMachine(
			@RequestBody MachineDTO machine
	) {
		return MachineDTO.from(machineService.create(machine));
	}

	@PutMapping("/machine/{id}")
	public MachineDTO updateMachine(
			@PathVariable UUID id,
			@RequestBody MachineDTO machine) {
		return MachineDTO.from(machineService.update(machine, id));
	}

	@DeleteMapping("/machine/{id}")
	public ResponseEntity<String> removeMachine(@PathVariable UUID id) {
		machineService.remove(id);
		return ResponseEntity.ok().build();
	}

}
