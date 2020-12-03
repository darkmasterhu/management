package hu.challenges.management.machine.service;

import java.util.List;
import java.util.UUID;

import hu.challenges.management.machine.entity.Machine;
import hu.challenges.management.machine.model.MachineDTO;

public interface MachineService {

	/**
	 * Fetch all machines (ordered by last modified first)
	 * @return All machines
	 */
	List<Machine> fetchAll();

	/**
	 * Fetch a single machine (by id)
	 *
	 * @param id Identifier
	 * @return Fetched machine
	 */
	Machine findById(UUID id);

	/**
	 * Create a single machine
	 *
	 * @param machineDto The new machine for create
	 * @return The created machine
	 */
	Machine create(MachineDTO machineDto);

	/**
	 * Update a single machine
	 *
	 * @param machineDto The machine for update
	 * @param id Identifier of machine
	 * @return The updated machine
	 */
	Machine update(MachineDTO machineDto, UUID id);

	/**
	 * Delete a single machine (soft-delete)
	 *
	 * @param id The identifier of removable machine
	 * @return The modified machine with inactive flag
	 */
	Machine remove(UUID id);

}
