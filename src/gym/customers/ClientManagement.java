package gym.customers;

import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;

public class ClientManagement {

    private Gym gym=Gym.getInstance();

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
        gym.addClient(client);
        gym.addOperations("Registered new client: " + client.getName());
        return client;
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
