package gym.management;

import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Person;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionManagement;

import java.util.*;

public class Gym  {
    private static final Gym gym = new Gym();
    private Secretary secretary;
    private List<String> operations = new ArrayList<>();
    private final Map<Integer, Integer> balanceHistory = new HashMap<>();
    private List<Observer> observers = new ArrayList<>(); // Observers list
    private List<Secretary> allSecretaries = new ArrayList<>();
    private ClientManagement clientManagement;
    private InstructorManagement instructorManagement;
    private SessionManagement sessionManagement;
    private int balance=0;
    private String name;
    private Gym() {
        this.name = "CrossFit"; //שם דיפולטיבי
        this.clientManagement = new ClientManagement();
        this.sessionManagement = new SessionManagement();
        this.instructorManagement = new InstructorManagement();

    }
    public static Gym getInstance() {
        return gym;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }


    public void updateBalanceHistory(int personId, int balance) {
        balanceHistory.put(personId, balance);
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

    public void addToBalance(int amount) {
        setBalance(this.balance+amount);
    }

    public List<String> getOperations() {
        return operations;
    }

    public void addOperations(String action) {
        operations.add(action);
    }

    public void setSecretary(Person person, int salary) {
        if (this.secretary != null) {
            allSecretaries.add(this.secretary);
        }

        // Create the new Secretary instance and assign the inherited balance
        this.secretary = new Secretary(person, salary);
    }
    public List<Secretary> getAllSecretaries() {
        return new ArrayList<>(allSecretaries); // Return a copy to preserve encapsulation
    }


    @Override
    public String toString() {
        String details = "Gym Name: " + this.name + "\n";


        if (this.getSecretary() != null) {
            details += "Gym Secretary: " + this.getSecretary().toString() + "\n";
        }

        details += "Gym Balance: " + this.getBalance() + "\n";

        details += "\nClients Data:\n";
        List<Client> clients = ClientManagement.getClients();
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                details += client.toString() + "\n";
            }
        }

        details += "\nEmployees Data:\n";
        List<Instructor> instructors = InstructorManagement.getInstructors();
        if (instructors != null && !instructors.isEmpty()) {
            for (Instructor instructor : instructors) {
                details += instructor.toString() + "\n";
            }
        }
        if (this.getSecretary() != null) {
            details += this.getSecretary().toString() + "\n";
        }

        details += "\nSessions Data:\n";
        List<Session> sessions = SessionManagement.getSessions();
        if (sessions != null && !sessions.isEmpty()) {
            for (Session session : sessions) {
                details += session.toString() + "\n";
            }
        }

        return details;
    }




}
