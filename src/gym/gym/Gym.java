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
        return secretary;
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

    public void setSecretary(Person person, int salary)
    {
        addOperations("A new secretary has started working at the gym: "+person.getName());
        this.secretary=new Secretary(person,salary);
    }
}
