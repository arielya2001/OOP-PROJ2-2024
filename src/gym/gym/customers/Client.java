package gym.customers;

import gym.DateUtils;
import gym.Gender;
import gym.Observer;
import gym.Person;

import java.util.ArrayList;

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


    public String getNotifications() {
        return notifications.toString();
    }

    public void deductBalance(int price)
    {
        int newBalance=getAccountBalance()-price;
        if (newBalance>0)
        setAccountBalance(newBalance);
    }

}
