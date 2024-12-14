package gym.customers;

import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;

public class ClientManagement {

    private Gym gym=Gym.getInstance();

    public Client registerNewClient(Person person) throws InvalidAgeException, DuplicateClientException {
        if (person.getAge() <= 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        Client newClient = new Client(person);
        System.out.println("Attempting to register new client: " + newClient);

        if (gym.getClients().contains(newClient)) {
            System.out.println("Client already exists: " + newClient);
            throw new DuplicateClientException("Error: The client is already registered");
        }

        gym.getClients().add(newClient);
        System.out.println("Registered new client: " + newClient);
        return newClient;
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
