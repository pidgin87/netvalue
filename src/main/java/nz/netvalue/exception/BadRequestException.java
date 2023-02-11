package nz.netvalue.exception;

/**
 * Throws when request is incorrect
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(Exception exception) {
        super(exception);
    }
}
