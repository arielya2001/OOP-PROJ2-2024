package gym.management;

import gym.Gym;
import gym.customers.Client;
import gym.management.Sessions.Session;

import java.time.format.DateTimeFormatter;

public class Notify {

    private Gym gym = Gym.getInstance();

    // Notify all clients in the gym
    public void notifyAllClients(String message) {
        for (Client client : gym.getClients()) {
            client.addNotification(message); // Use the safe method to add notifications
        }
    }

    // Notify all clients registered to a specific session
    public void notifySession(Session session, String message) {
        for (Client client : session.getRegisteredToSession()) {
            client.addNotification(message); // Use the safe method to add notifications
        }
    }

    // Notify all clients registered for sessions on a specific date
    public void notifyByDate(String date, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Session session : gym.getSessions()) {
            String sessionDateFormatted = session.getDate().toLocalDate().format(formatter);
            if (sessionDateFormatted.equals(date)) {
                for (Client client : session.getRegisteredToSession()) {
                    client.addNotification(message); // Add notification to the client
                }
            }
        }
    }

}
