package gym.customers;

import gym.management.Observer;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends Person implements Observer {
    private List<String> notifications;

    public Client(Person person) {
        super(person.getId(), person.getName(), person.getGender(), person.getDateOfBirth());
        this.notifications = new ArrayList<>();
    }

    public boolean clientHasSufficientBalance(Session session) {
        return getAccountBalance() >= session.getSessionPrice();
    }

    public List<String> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        return notifications;
    }

    public void addNotification(String message) {
        getNotifications().add(message);
    }

    @Override
    public void update(String message) {
        addNotification(message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return getId() == client.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance());
    }
}
