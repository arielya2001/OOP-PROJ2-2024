package gym.Exception;

public class InvalidAgeException extends Exception {


    public InvalidAgeException (String message)
    {
        super(message);
    }

    public InvalidAgeException (Throwable e)
    {
        super(e);
    }

    public InvalidAgeException(String message, Throwable e)
    {
        super(message,e);
    }

}
