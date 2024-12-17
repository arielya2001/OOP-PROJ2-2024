package gym.management;

import gym.customers.BalanceManager;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class InstructorManagement {

    private static InstructorManagement instructorManagement;
    private final List<Instructor> instructors;
    private final Gym gym;

    private InstructorManagement() {
        this.instructors = new ArrayList<>();
        this.gym = Gym.getInstance();
    }

    public static InstructorManagement getInstance() {
        if (instructorManagement == null) {
            instructorManagement = new InstructorManagement();
        }
        return instructorManagement;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public boolean isQualified(SessionType sessionType, Instructor instructor) {
        return instructor.getQualifications().contains(sessionType);
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public Instructor newInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        Client client = ClientManagement.getInstance().getClientFromPerson(person);
        Instructor instructor;

        if (client != null) {
            instructor = new Instructor(client, salaryPerHour, qualifications);
            BalanceManager.initializeBalance(instructor.getId(), BalanceManager.getBalance(client.getId()));
        } else {
            instructor = new Instructor(person, salaryPerHour, qualifications);
            BalanceManager.initializeBalance(instructor.getId(), BalanceManager.getBalance(person.getId()));
        }

        instructors.add(instructor);
        return instructor;
    }

    public int calculateInstructorSalaries() {
        int totalInstructorSalary = 0;

        for (Instructor i : instructors) {
            int sessionCount = i.getSessionsOfInstructor().size();
            int instructorSalary = sessionCount * i.getSalaryPerHour();

            totalInstructorSalary += instructorSalary;

            // עדכון היתרה דרך BalanceManager
            BalanceManager.updateBalance(i.getId(), instructorSalary);
        }

        return totalInstructorSalary;
    }
}
