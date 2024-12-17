package gym.management;

import gym.customers.BalanceManager;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Person;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gym {
    private static Gym gym;
    private Secretary secretary;
    private final List<String> operations = new ArrayList<>();
    private final List<Secretary> allSecretaries = new ArrayList<>();
    private int balance = 0;
    private String name;

    private Gym() {
        this.name = "CrossFit"; // Default name
    }

    public static Gym getInstance() {
        if (gym == null) {
            gym = new Gym();
        }
        return gym;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }

    public int getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToBalance(int amount) {
        BalanceManager.updateBalance(0, amount); // BalanceManager לטיפול מרכזי
        this.balance += amount;
    }

    public List<String> getOperations() {
        return Collections.unmodifiableList(operations); // Read-only access
    }

    public void addOperations(String action) {
        operations.add(action);
    }

    public void setSecretary(Person person, int salary) {
        if (this.secretary != null) {
            allSecretaries.add(this.secretary); // Save previous secretary
        }

        // Create a new Secretary instance (preserves original ID)
        this.secretary = new Secretary(person, salary);

        addOperations("A new secretary has started working at the gym: " + person.getName());
    }

    public boolean isCurrentSecretary(Secretary secretary) {
        return this.secretary != null && this.secretary.equals(secretary);
    }

    public List<Secretary> getAllSecretaries() {

        return new ArrayList<>(allSecretaries); // Copy for safety
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("Gym Name: " + this.name + "\n");

        if (this.getSecretary() != null) {
            details.append("Gym Secretary: ").append(this.getSecretary().toString()).append("\n");
        }

        details.append("Gym Balance: ").append(this.getBalance()).append("\n");

        details.append("\nClients Data:\n");
        List<Client> clients = ClientManagement.getInstance().getClients();
        for (Client client : clients) {
            details.append(client.toString()).append("\n");
        }

        details.append("\nEmployees Data:\n");
        List<Instructor> instructors = InstructorManagement.getInstance().getInstructors();
        for (Instructor instructor : instructors) {
            details.append(instructor.toString()).append("\n");
        }
        details.append(this.getSecretary().toString()).append("\n");


        details.append("\nSessions Data:\n");
        List<Session> sessions = SessionManagement.getInstance().getSessions();
        for (Session session : sessions) {
            details.append(session.toString()).append("\n");
        }

        return details.toString();
    }
}