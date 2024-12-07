package gym.Exception;

public class DuplicateClientException extends Exception {
    public DuplicateClientException(String message) {
        super(message);
    }

    public DuplicateClientException(Throwable cause) {
        super(cause);
    }

    public DuplicateClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
