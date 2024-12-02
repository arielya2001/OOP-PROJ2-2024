public class Gym {
    private static Gym gym=new Gym();
    private Secretary secretary;

    private Gym(){

    }

    public static Gym getInstance()
    {
        return gym;
    }

    public Secretary getSecretary() {
        return secretary;
    }
}
