package exceptions;

public class NoSuchAssetException extends Exception {

    public NoSuchAssetException() {
        super("No such asset exists");
    }

    public NoSuchAssetException(String message) {
        super(message);
    }

}
