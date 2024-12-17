package gym.management;

import gym.customers.Person;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private int salaryPerHour;
    private List<Session> sessionsOfInstructor;
    private ArrayList<SessionType> qualifications;




    public Instructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salaryPerHour = salaryPerHour;
        this.qualifications = qualifications;
        this.sessionsOfInstructor=new ArrayList<>();
    }
    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public List<Session> getSessionsOfInstructor() {
        return sessionsOfInstructor;
    }
    public void addToSessionList(Session session)
    {
        sessionsOfInstructor.add(session);
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
        StringBuilder certifiedClassesStr = new StringBuilder();
        for (SessionType sessionType : qualifications) {
            if (certifiedClassesStr.length() > 0) {
                certifiedClassesStr.append(", ");
            }
            certifiedClassesStr.append(sessionType.name());
        }
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: %s | Salary per Hour: %d | Certified Classes: %s",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance(), "Instructor", salaryPerHour, certifiedClassesStr.toString());
    }
}
