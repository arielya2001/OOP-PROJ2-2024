package gym;

import gym.customers.Client;
import gym.management.Instructor;
import gym.management.Secretary;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gym implements Observer{
    private static final Gym gym=new Gym();
    private Secretary secretary;
    private List<Client> clients=new ArrayList<>();
    private List<Instructor> instructors=new ArrayList<>();
    private List<Session>sessions=new ArrayList<>();
    private List<String>operations=new ArrayList<>();
    private int balance;
    private String name;

    private Gym(){

    }

    public static Gym getInstance()
    {
        return gym;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }



    public List<Client> getClients() {
        return clients;
    }

    public int getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addToBalance(int amount)
    {
        setBalance(balance+amount);

    }

    public List<String> getOperations() {
        return operations;
    }
    public void addOperations(String action)
    {
        operations.add(action);
    }
    public void disableSecretary() {
        if (this.secretary != null) {
            this.secretary.disabled();
            this.secretary = null;
        }
    }

    public void setSecretary(Person person, int salary)
    {
        disableSecretary();
        this.secretary=new Secretary(person,salary);
    }
    public List<Session> getSessions()
    {
        return sessions;
    }

    @Override
    public void update(String message) {
        operations.add(message);

    }
}
