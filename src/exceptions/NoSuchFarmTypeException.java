package exceptions;

public class NoSuchFarmTypeException extends Exception {

    public NoSuchFarmTypeException() {
        super("No such farm type exists.");
    }

    public NoSuchFarmTypeException(String message) {
        super(message);
    }

}
