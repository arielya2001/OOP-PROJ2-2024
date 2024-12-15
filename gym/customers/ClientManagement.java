package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;

import java.util.ArrayList;
import java.util.List;

public class ClientManagement {

    private static List<Client> clients = new ArrayList<>();
    private Gym gym = Gym.getInstance();

    public static List<Client> getClients() {
        return clients;
    }

    public boolean isClient(Client client) {
        for (Client c : clients) {
            if (c.equals(client)) {
                return true;
            }
        }
        return false;
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

    public void unregisterClient(Client client) throws ClientNotRegisteredException {

            if (isClient(client))
            {
                clients.remove(client);
                gym.addOperations("Unregistered client: " + client.getName());
            }
            else throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
             gym.addOperations("Unregistered client: " + client.getName());

    }
}
