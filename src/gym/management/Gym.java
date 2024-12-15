package gym.management;

import gym.customers.Client;
import gym.customers.Person;
import gym.management.Sessions.Session;

import java.util.*;

public class Gym implements Subject {
    private static final Gym gym = new Gym();
    private Secretary secretary;
    private final Map<Integer, Person> people = new HashMap<>();
    private List<Client> clients = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private List<Session> sessions = new ArrayList<>();
    private List<String> operations = new ArrayList<>();
    private final Map<Integer, Integer> balanceHistory = new HashMap<>();
    private List<Observer> observers = new ArrayList<>(); // Observers list
    private List<Secretary> allSecretaries = new ArrayList<>();
    private int balance=0;
    private String name;
    private Gym() {
        this.name = "CrossFit"; //שם דיפולטיבי

    }
    public List<Person> getPeople() {
        return new ArrayList<>(people.values());
    }
    public Map<Integer, Person> getPeopleMap() {
        return people;
    }
    public void updateBalanceHistory(int personId, int balance) {
        balanceHistory.put(personId, balance);
    }

    public int getLastKnownBalance(int personId) {
        return balanceHistory.getOrDefault(personId, 0); // Default to 0 if no record exists
    }

    public static Gym getInstance() {
        return gym;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }


    public List<Client> getClients() {
        return clients;
    }

    public List<Instructor> getInstructors() {
        return instructors;
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

    public List<String> getOperations() {
        return operations;
    }

    public void setSecretary(Person person, int salary) {
        if (this.secretary != null) {
            allSecretaries.add(this.secretary);

        }
        int inheritedBalance = gym.getLastKnownBalance(person.getId());


        this.secretary = new Secretary(person, salary);
        this.secretary.setAccountBalance(inheritedBalance);

        gym.people.put(this.secretary.getId(), this.secretary);
    }
    public List<Secretary> getAllSecretaries() {
        return new ArrayList<>(allSecretaries);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Add gym name
        sb.append("Gym Name: ").append(name).append("\n");

        // Add secretary details
        if (secretary != null && secretary instanceof Secretary) {
            Secretary sec = (Secretary) secretary;
            sb.append("Gym Secretary: ").append(sec.toString()).append("\n");
        } else {
            sb.append("Gym Secretary: None\n");
        }

        // Add gym balance
        sb.append("Gym Balance: ").append(balance).append("\n\n");

        // Add clients
        sb.append("Clients Data:\n");
        if (clients.isEmpty()) {
            sb.append("No clients registered.\n");
        } else {
            for (Client c : clients) {
                sb.append(c.toString()).append("\n");
            }
        }

        sb.append("\nEmployees Data:\n");

        // Add instructors
        if (instructors.isEmpty()) {
            sb.append("No instructors hired.\n");
        } else {
            for (Instructor i : instructors) {
                sb.append(i.toString()).append("\n");
            }
        }

        // Add secretary as an employee if applicable
        if (secretary != null && secretary instanceof Secretary) {
            Secretary sec = (Secretary) secretary;
            sb.append(sec.toString()).append("\n");
        }

        // Add sessions
        sb.append("\nSessions Data:\n");
        if (sessions.isEmpty()) {
            sb.append("No sessions available.\n");
        } else {
            for (Session s : sessions) {
                sb.append(s.toString()).append("\n");
            }
        }

        return sb.toString();
    }
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void notifyClients(String message) {
        notifyObservers(message);
        operations.add("Notified all observers: " + message);
    }
}
