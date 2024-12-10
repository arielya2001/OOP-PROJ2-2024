package gym.Exception;

public class DuplicateClientException extends Exception {


    public DuplicateClientException (String message)
    {
        super(message);
    }

    public DuplicateClientException (Throwable e)
    {
        super(e);
    }

    public DuplicateClientException(String message, Throwable e)
    {
        super(message,e);
    }

}