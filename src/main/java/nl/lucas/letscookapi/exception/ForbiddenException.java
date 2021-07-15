package nl.lucas.letscookapi.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You are not authorized to visit this page");
    }
}
