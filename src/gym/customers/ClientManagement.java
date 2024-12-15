package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;
import gym.management.Secretary;
import java.util.List;
import java.util.Map;

public class ClientManagement {

    private Gym gym = Gym.getInstance();

    public void addClient(Client client) {
        Map<Integer, Person> people = gym.getPeopleMap();
        List<Client> clients = gym.getClients();

        Person existingPerson = people.get(client.getId());
        if (existingPerson != null) {
            if (!(existingPerson instanceof Client)) {
                client = new Client(existingPerson);
            }
        }
        people.put(client.getId(), client);
        if (!clients.contains(client)) {
            clients.add(client);
            gym.addObserver(client);
        }
    }

    public Client registerNewClient(Person person) throws InvalidAgeException, DuplicateClientException {
        for (Client client : gym.getClients()) {
            if (client.getId() == person.getId()) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }

        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        Client client = new Client(person);
        this.addClient(client);
        gym.getSecretary().addOperations("Registered new client: " + client.getName());
        return client;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        boolean removed = false;

        for (int i = 0; i < gym.getClients().size(); i++) {
            if (gym.getClients().get(i).equals(client)) {
                gym.getClients().remove(i);
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }

        gym.updateBalanceHistory(client.getId(), client.getAccountBalance());
        gym.getSecretary().addOperations("Unregistered client: " + client.getName());
    }

    public boolean isClient(Client client) {
        for (Client c : gym.getClients()) {
            if (c.equals(client)) {
                return true;
            }
        }
        return false;
    }
}
