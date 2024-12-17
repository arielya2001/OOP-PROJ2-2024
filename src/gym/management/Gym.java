package gym.management;

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
    private ClientManagement clientManagement;
    private InstructorManagement instructorManagement;
    private SessionManagement sessionManagement;
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

    public ClientManagement getClientManagement() {
        if (clientManagement == null) {
            clientManagement = ClientManagement.getInstance();
        }
        return clientManagement;
    }

    public InstructorManagement getInstructorManagement() {
        if (instructorManagement == null) {
            instructorManagement = InstructorManagement.getInstance();
        }
        return instructorManagement;
    }

    public SessionManagement getSessionManagement() {
        if (sessionManagement == null) {
            sessionManagement = SessionManagement.getInstance();
        }
        return sessionManagement;
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addToBalance(int amount) {
        setBalance(this.balance + amount);
    }

    public List<String> getOperations() {
        return Collections.unmodifiableList(operations); // Read-only access
    }

    public void addOperations(String action) {
        operations.add(action);
    }

    public void setSecretary(Person person, int salary) {
        if (this.secretary != null) {
            allSecretaries.add(this.secretary); // Save the previous secretary
        }
        int inheritedBalance = getClientManagement().getBalanceHistory().getOrDefault(person.getId(), person.getAccountBalance());
        this.secretary = new Secretary(person, salary);
        this.secretary.setAccountBalance(inheritedBalance);
        getClientManagement().addToClients(new Client(person)); // Ensure the person is added to clients
    }

    public boolean isCurrentSecretary(Secretary secretary) {
        return this.secretary != null && this.secretary.equals(secretary);
    }

    public List<Secretary> getAllSecretaries() {
        return new ArrayList<>(allSecretaries); // Return a copy for read-only access
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("Gym Name: " + this.name + "\n");

        if (this.getSecretary() != null) {
            details.append("Gym Secretary: ").append(this.getSecretary().toString()).append("\n");
        }

        details.append("Gym Balance: ").append(this.getBalance()).append("\n");

        details.append("\nClients Data:\n");
        List<Client> clients = new ArrayList<>(getClientManagement().getClients().values());
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                details.append(client.toString()).append("\n");
            }
        }

        details.append("\nEmployees Data:\n");
        List<Instructor> instructors = new ArrayList<>(getInstructorManagement().getInstructors().values());
        if (instructors != null && !instructors.isEmpty()) {
            for (Instructor instructor : instructors) {
                details.append(instructor.toString()).append("\n");
            }
        }

        details.append("\nSessions Data:\n");
        List<Session> sessions = new ArrayList<>(getSessionManagement().getSessions());
        if (sessions != null && !sessions.isEmpty()) {
            for (Session session : sessions) {
                details.append(session.toString()).append("\n");
            }
        }

        return details.toString();
    }
}
