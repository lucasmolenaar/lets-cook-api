package nl.lucas.letscookapi.exception;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
