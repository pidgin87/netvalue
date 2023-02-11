package nz.netvalue.exception;

/**
 * Throws when updated entity is old
 */
public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException(String message) {
        super(message);
    }
}
