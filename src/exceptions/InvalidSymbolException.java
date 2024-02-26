package exceptions;

public class InvalidSymbolException extends RuntimeException {
    public InvalidSymbolException() {
    }

    public InvalidSymbolException(String message) {
        super(message);
    }
}
