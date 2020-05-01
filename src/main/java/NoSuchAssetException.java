package main.java;

/**
 * Exception if you are looking for an asset that does not exist.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class NoSuchAssetException extends Exception {

    public NoSuchAssetException() {
        super("No such asset exists");
    }

    public NoSuchAssetException(String message) {
        super(message);
    }

}
