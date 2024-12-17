package gym.customers;

import gym.customers.BalanceManager;
import gym.management.DateUtils;

import java.util.Objects;

public class Person {
    private String name;
    private Gender gender;
    private String dateOfBirth;
    private static int nextId = 1110;
    private final int id;

    public Person(String name, int initialBalance, Gender gender, String dateOfBirth) {
        this.id = ++nextId;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        BalanceManager.initializeBalance(this.id, initialBalance);
    }
    public Person(int id, String name, Gender gender, String dateOfBirth) {
        this.id = id; // Use the existing ID
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return this.name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public int getAccountBalance() {
        return BalanceManager.getBalance(this.id);
    }

    public void deductBalance(int price) {
        BalanceManager.updateBalance(this.id, -price);
    }
    public int getAge() {
        if (dateOfBirth == null || dateOfBirth.length() < 10) {
            throw new IllegalStateException("Invalid dateOfBirth: " + dateOfBirth);
        }
        int currentYear = 2024;
        String birthYear = dateOfBirth.substring(6, 10);
        int year = Integer.parseInt(birthYear);
        return currentYear - year;

    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public int getId() {
        return id;
    }
    public boolean isOverAge(int Age)
    {
        return DateUtils.isOverAge(getDateOfBirth(),Age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return this.getId() == person.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Balance: %d",
                id, name, gender, dateOfBirth, getAccountBalance());
    }
}
