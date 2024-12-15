package gym.management;

import gym.customers.Person;

import java.util.ArrayList;

public class Instructor extends Person {

    private int salaryPerHour;
    private ArrayList<String> qualifications;
    public Instructor(Person person, int salaryPerHour, ArrayList<String> qualifications) {
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

    public ArrayList<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<String> qualifications) {
        this.qualifications = qualifications;
    }


    @Override
    public String toString() {
        StringBuilder certifiedClassesStr = new StringBuilder();
        for (String qualification : qualifications) {
            if (certifiedClassesStr.length() > 0) {
                certifiedClassesStr.append(", ");
            }
            certifiedClassesStr.append(qualification);
        }
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: %s | Salary per Hour: %d | Certified Classes: %s",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance(), "Instructor", salaryPerHour, certifiedClassesStr.toString());
    }
}
