package gym.Exception;

public class InstructorNotQualifiedException extends Exception {
    public InstructorNotQualifiedException  (String message)
    {
        super(message);
    }


    public InstructorNotQualifiedException (Throwable e)
    {
        super(e);
    }

    public InstructorNotQualifiedException(String message, Throwable e)
    {
        super(message,e);
    }

}
