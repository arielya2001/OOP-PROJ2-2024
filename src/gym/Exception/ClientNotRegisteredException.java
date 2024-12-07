package gym.Exception;

public class ClientNotRegisteredException extends Exception {
    public ClientNotRegisteredException(String message) {
        super(message);
    }

    public ClientNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public ClientNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
