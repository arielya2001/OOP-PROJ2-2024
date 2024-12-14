package gym.customers;

import gym.management.Gym;
import gym.management.Instructor;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Client extends Person {
    private List<String> notifications;


    public Client(Person person) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.notifications = new ArrayList<>();
    }






    public boolean clientHasSufficientBalance(Session session) {
        return getAccountBalance() >= session.getSessionPrice();
    }




    public List<String> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<>(); // Safeguard against null
        }
        return notifications;
    }
    public void addNotification(String message) {
        getNotifications().add(message);
    }

    public void deductBalance(int price) {
        int newBalance = getAccountBalance() - price;
        if (newBalance >= 0) {
            setAccountBalance(newBalance);
            // Sync balance with the Instructor list if this person is also an instructor
            Gym gym = Gym.getInstance();
            Instructor instructor = gym.getInstructors().stream()
                    .filter(inst -> inst.getId() == this.getId())
                    .findFirst()
                    .orElse(null);
            if (instructor != null) {
                instructor.setAccountBalance(newBalance);
            }
        } else {
            System.out.println("Insufficient balance for: " + getName());
        }
    }




    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Client client = (Client) obj;
        return getId() == client.getId() && getName().equals(client.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance());
    }



}
