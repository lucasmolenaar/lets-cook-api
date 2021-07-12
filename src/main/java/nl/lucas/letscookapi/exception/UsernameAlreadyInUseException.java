package nl.lucas.letscookapi.exception;

public class UsernameAlreadyInUseException extends RuntimeException {

    public UsernameAlreadyInUseException() {
        super("This username is already taken.");
    }
}
