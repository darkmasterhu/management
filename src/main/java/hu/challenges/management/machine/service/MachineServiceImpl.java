package hu.challenges.management.machine.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.challenges.management.machine.entity.Machine;
import hu.challenges.management.machine.exceptions.MachineNotFoundException;
import hu.challenges.management.machine.model.MachineDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MachineServiceImpl
		implements MachineService {

	private final MachineRepository machineRepository;

	@Override
	public List<Machine> fetchAll() {
		return machineRepository.findByActiveOrderByUpdatedAtDesc(true);
	}

	@Override
	public Machine findById(UUID id) {
		return machineRepository
				.findById(id)
				.orElseThrow(
						() -> new MachineNotFoundException("Unable to find machine with id '" + id.toString() + "'")
				);
	}

	@Override
	public Machine create(final MachineDTO machineDto) {
		final Machine machine = new Machine();
		final OffsetDateTime creationDate = OffsetDateTime.now();

		machine.setName(machineDto.getName());
		machine.setCreatedAt(creationDate);
		machine.setUpdatedAt(creationDate);
		machine.setActive(true);

		return machineRepository.save(machine);
	}

	@Override
	public Machine update(
			final MachineDTO machineDto,
			final UUID uuid
	) {
		final Machine machine = findById(uuid);

		machine.setName(machineDto.getName());
		machine.setUpdatedAt(OffsetDateTime.now());

		return machineRepository.save(machine);
	}

	@Override
	public Machine remove(UUID id) {
		final Machine machine = findById(id);

		machine.setActive(false);
		machine.setUpdatedAt(OffsetDateTime.now());

		return machineRepository.save(machine);
	}
}
