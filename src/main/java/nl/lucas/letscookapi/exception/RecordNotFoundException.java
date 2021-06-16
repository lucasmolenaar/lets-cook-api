package nl.lucas.letscookapi.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super("Could not find record");
    }
}
