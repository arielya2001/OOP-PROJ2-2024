package gym.customers;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.Gym;
import gym.Person;
import gym.management.Sessions.Session;

public class ClientManagement {

    private Gym gym=Gym.getInstance();

    public Client registerNewClient(Person person) throws InvalidAgeException,DuplicateClientException{
        if (person.getAge()>18)
        {
            Client newClient = new Client(person);
            if (isClient(newClient)) throw new DuplicateClientException("Error: The client is already registered");
           else{
               gym.getClients().add(newClient);
            }
            return newClient;
        }
        else throw new InvalidAgeException("Error: Client must be at least 18 years old to register");

    }

    public boolean isClient(Client client)
    {
       for (Client c: gym.getClients())
       {
           if (c.getId()==client.getId())
               return true;
       }
       return false;
    }



    public void removeClient(Client client) throws ClientNotRegisteredException {
        if (gym.getClients().contains(client))
            gym.getClients().remove(client);
        else throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");

    }

}
