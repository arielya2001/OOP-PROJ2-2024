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
            client.update(message);
        }
    }
    public void notifyByDate(String date, String message) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String reformattedDate = LocalDate.parse(date, inputFormatter).format(outputFormatter);

        for (Session session : gym.getSessions()) {
            String sessionDateFormatted = session.getDate().toLocalDate().format(outputFormatter);
            if (sessionDateFormatted.equals(reformattedDate)) {
                notifySession(session, message);
            }
        }

        gym.getSecretary().addOperations("A message was sent to everyone registered for a session on " + reformattedDate + " : " + message);
    }





}
