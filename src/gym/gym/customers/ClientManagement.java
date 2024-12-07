package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.Gym;
import gym.Person;

public class ClientManagement {

    private Gym gym;

    public Client registerNewClient(Person person) throws InvalidAgeException,DuplicateClientException{
        if (person.isOverAge(18))
        {
            Client newClient = new Client(person);
            if (gym.getClients().contains(newClient)) throw new DuplicateClientException("The client is already registered");
           else{
               gym.getClients().add(newClient);
            }
            return newClient;
        }
        else throw new InvalidAgeException("Client must be at least 18 years old to register");

    }

    public boolean isClient(Client client)
    {
        return gym.getClients().contains(client);
    }

    public void removeClient(Client client) throws ClientNotRegisteredException {
        if (gym.getClients().contains(client))
            gym.getClients().remove(client);
        else throw new ClientNotRegisteredException("Registration is required before attempting to unregister");

    }
}
