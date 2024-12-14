package gym.management;

import gym.customers.Client;
import gym.management.Sessions.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Notify {

    private Gym gym = Gym.getInstance();

    // Notify all clients in the gym
    public void notifyAllClients(String message) {
        gym.notifyClients(message); // Use the observer mechanism
    }

    public void notifySession(Session session, String message) {
        for (Client client : session.getRegisteredToSession()) {
            client.update(message); // Notify specific clients
        }
    }

    // Notify all clients registered for sessions on a specific date
    public void notifyByDate(String date, String message) {
        // Ensure consistent formatting for date comparison and output
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String reformattedDate = LocalDate.parse(date, inputFormatter).format(outputFormatter);

        for (Session session : gym.getSessions()) {
            String sessionDateFormatted = session.getDate().toLocalDate().format(outputFormatter);
            if (sessionDateFormatted.equals(reformattedDate)) {
                // Use notifySession to utilize the observer mechanism for all session participants
                notifySession(session, message);
            }
        }

        // Use reformatted date for consistent logging
        gym.addOperations("A message was sent to everyone registered for a session on " + reformattedDate + " : " + message);
    }





}
