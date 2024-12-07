package gym;

import gym.customers.Client;
import gym.management.Instructor;
import gym.management.Secretary;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gym {
    private static Gym gym=new Gym();
    private String name;
    private Secretary secretary;
    private List<Client> clients=new ArrayList<>();
    private List<Instructor> instructors=new ArrayList<>();
    private List<Session>sessions=new ArrayList<>();

    private Gym(){

    }

    public static Gym getInstance()
    {
        return gym;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Secretary getSecretary() {
        return secretary;
    }

    public List<Client> getClients() {
        return clients;
    }
    public void setSecretary(Person person, int salaryPerMonth) {
        if (!(person instanceof Secretary)) {
            Secretary secretary = new Secretary();
            this.secretary = secretary;
        }
    }

}
