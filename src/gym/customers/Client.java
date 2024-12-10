package gym.customers;

import gym.DateUtils;
import gym.Gender;
import gym.Observer;
import gym.Person;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.Objects;

public class Client extends Person {
    private ArrayList<String>notifications;


    public Client(String name, int accountBalance, Gender gender, String dateOfBirth) {
        super(name, accountBalance, gender, dateOfBirth);
        notifications=new ArrayList<>();
    }

    public Client(Person person) {
        super(person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        notifications=new ArrayList<>();
    }

    public boolean clientHasSufficientBalance(Session session) {
        return getAccountBalance() >= session.getSessionPrice();
    }




    public ArrayList<String> getNotifications() {

        return notifications;
    }

    public void deductBalance(int price)
    {
        int newBalance=getAccountBalance()-price;
        if (newBalance>0)
        setAccountBalance(newBalance);
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Client client = (Client) obj;
            return getId() == client.getId() && getName().equals(client.getName());

    }

}
