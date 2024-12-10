package gym;

import gym.customers.Client;
import gym.management.Sessions.Session;

public class Notify {

    private Gym gym = Gym.getInstance();

    public void notifyAllClients(String message) {
        for (Client client : gym.getClients()) {
            client.getNotifications().add(message);
        }

    }

    public void notifySession(Session session,String message)
    {
        for (Client client : session.getRegisteredToSession())
        {
            client.getNotifications().add(message);
        }

    }

    public void notifyByDate(String date,String message)
    {
        for (Session session : gym.getSessions())
        {
            if (session.getDate().substring(0,10).equals(date))
            for (Client client : session.getRegisteredToSession())
            {
                client.getNotifications().add(message);
            }
        }

    }


}
