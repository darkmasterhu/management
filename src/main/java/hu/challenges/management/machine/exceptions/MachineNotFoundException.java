package hu.challenges.management.machine.exceptions;

public class MachineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1990395990988244016L;

	public MachineNotFoundException() {
		super();
	}

	public MachineNotFoundException(final String message) {
		super(message);
	}

	public MachineNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public MachineNotFoundException(Throwable cause) {
		super(cause);
	}
}