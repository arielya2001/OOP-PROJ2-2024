package gym.management;

import gym.Gender;
import gym.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.Collections;

public class Instructor extends Person {

    private int salaryPerHour;
    private ArrayList<SessionType>qualifications;

    public Instructor(String name, int accountBalance, Gender gender, String dateOfBirth, int salaryPerHour, ArrayList<SessionType>qualifications) {
        super(name, accountBalance, gender, dateOfBirth);
        this.salaryPerHour=salaryPerHour;
        this.qualifications=new ArrayList<>(qualifications);
    }

    public Instructor(Person person,int salaryPerHour,ArrayList<SessionType>qualifications) {
        super(person.getName(),person.getAccountBalance(),person.getGender(),person.getDateOfBirth());
        this.salaryPerHour=salaryPerHour;
        this.qualifications=new ArrayList<>(qualifications);
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

        String certifiedClassesString = "";
        for (int i = 0; i < qualifications.size(); i++) {
            certifiedClassesString += qualifications.get(i);
            if (i < qualifications.size() - 1) {
                certifiedClassesString += ", ";
            }
        }

        return "ID: " + getId() +
                " | Name: " + getName() +
                " | Gender: " + getGender() +
                " | Birthday: " + getDateOfBirth() +
                " | Age: " + getAge() +
                " | Balance: " + getAccountBalance() +
                " | Role: Instructor" +
                " | Salary per Hour: " + salaryPerHour +
                " | Certified Classes: " + certifiedClassesString;
    }

}
