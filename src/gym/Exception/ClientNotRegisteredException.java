package gym.Exception;

public class ClientNotRegisteredException extends Exception {

    public ClientNotRegisteredException (String message)
    {
        super(message);
    }


    public ClientNotRegisteredException (Throwable e)
    {
        super(e);
    }

    public ClientNotRegisteredException(String message, Throwable e)
    {
        super(message,e);
    }

}