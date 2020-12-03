package hu.challenges.management.machine.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import hu.challenges.management.machine.entity.Machine;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineDTO implements Serializable {

	private static final long serialVersionUID = 3515897495611471719L;

	/**
	 * Unique identifier
	 */
	private UUID id;

	/**
	 * Creation date of machine
	 */
	private OffsetDateTime createdAt;

	/**
	 * Date of update
	 */
	private OffsetDateTime updatedAt;

	/**
	 * Name of current machine
	 */
	private String name;

	public static MachineDTO from(final Machine machine) {
		if (Boolean.TRUE.equals(machine.getActive())) {
			return new MachineDTO(
					machine.getId(),
					machine.getCreatedAt(),
					machine.getUpdatedAt(),
					machine.getName()
			);
		} else {
			return new MachineDTO();
		}
	}

}
