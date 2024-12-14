package gym.management;

import gym.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

public class Instructor extends Person {

    private int salaryPerHour;
    private ArrayList<SessionType> qualifications;
    public Instructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salaryPerHour = salaryPerHour;
        this.qualifications = qualifications;
    }
    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public ArrayList<SessionType> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<SessionType> qualifications) {
        this.qualifications = qualifications;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: Instructor | Salary per Hour: %d | Certified Classes: %s",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance(), salaryPerHour, qualifications);
    }

}
