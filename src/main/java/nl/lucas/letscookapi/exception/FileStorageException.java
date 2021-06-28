package nl.lucas.letscookapi.exception;

public class FileStorageException  extends RuntimeException{

    private final static long serialVersionUID = 1L;
    private String msg;

    public FileStorageException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
