package nl.lucas.letscookapi.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super("Could not handle request");
    }
}
