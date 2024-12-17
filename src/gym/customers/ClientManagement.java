package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;

import java.util.ArrayList;
import java.util.List;

public class ClientManagement {

    private static ClientManagement clientManagement;

    private final List<Client> clients;
    private final Gym gym;

    private ClientManagement() {
        this.clients = new ArrayList<>();
        this.gym = Gym.getInstance();
    }

    public static ClientManagement getInstance() {
        if (clientManagement == null) {
            clientManagement = new ClientManagement();
        }
        return clientManagement;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addToClients(Client client) {
        clients.add(client);
    }

    public boolean isClient(Client client) {
        return clients.contains(client);
    }

    public boolean isClient(Person person) {
        for (Client c : clients) {
            if (c.getId() == person.getId()) {
                return true;
            }
        }
        return false;
    }

    public Client registerNewClient(Person person) throws InvalidAgeException, DuplicateClientException {
        if (isClient(person)) {
            throw new DuplicateClientException("Error: The client is already registered");
        }

        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        Client client = new Client(person);
        clients.add(client);
        gym.addOperations("Registered new client: " + client.getName());
        return client;
    }

    public Client getClientFromPerson(Person person) {
        for (Client client : clients) {
            if (client.getId() == person.getId()) {
                return client;
            }
        }
        return null;
    }

    public void removeClient(Client client) throws ClientNotRegisteredException {
        if (isClient(client)) {
            clients.remove(client);
        } else {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
    }
}