package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;

import java.util.HashMap;
import java.util.Map;

public class ClientManagement {

    private static ClientManagement clientManagement;
    private final Map<Integer, Client> clients; // Clients mapped by their ID
    private final Map<Integer, Integer> balanceHistory; // Balance history mapped by client ID
    private final Gym gym;

    private ClientManagement() {
        this.clients = new HashMap<>();
        this.balanceHistory = new HashMap<>();
        this.gym = Gym.getInstance();
    }

    public static ClientManagement getInstance() {
        if (clientManagement == null) {
            clientManagement = new ClientManagement();
        }
        return clientManagement;
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public void addToClients(Client client) {
        clients.put(client.getId(), client);
    }

    public boolean isClient(Client client) {
        return clients.containsKey(client.getId());
    }

    public boolean isClient(int personId) {
        return clients.containsKey(personId);
    }

    public Client registerNewClient(Person person) throws InvalidAgeException, DuplicateClientException {
        if (isClient(person.getId())) {
            throw new DuplicateClientException("Error: The client is already registered");
        }

        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        Client client = new Client(person);
        clients.put(client.getId(), client);
        gym.addOperations("Registered new client: " + client.getName());

        return client;
    }

    public void removeClient(Client client) throws ClientNotRegisteredException {
        if (isClient(client)) {
            balanceHistory.put(client.getId(), client.getAccountBalance());
            clients.remove(client.getId());
        } else {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
    }

    public Map<Integer, Integer> getBalanceHistory() {
        return balanceHistory;
    }

    public Client restoreClientFromHistory(Person person) {
        int restoredBalance = balanceHistory.getOrDefault(person.getId(), 0);
        Client restoredClient = new Client(person);
        restoredClient.setAccountBalance(restoredBalance);
        return restoredClient;
    }

    public Client getClientFromList(Person person) {
        if (clients.containsKey(person.getId())) {
            return clients.get(person.getId());
        }
        return restoreClientFromHistory(person);
    }
    public void saveBalance(Person person, int balance) {
        balanceHistory.put(person.getId(), balance);
    }


}
