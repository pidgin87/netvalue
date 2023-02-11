package nz.netvalue.exception;

/**
 * Throws when charge connector with number in charge point already exists
 */
public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException(String message) {
        super(message);
    }
}
