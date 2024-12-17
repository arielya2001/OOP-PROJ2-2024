package gym.management;

import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorManagement {

    private static InstructorManagement instructorManagement;
    private final Map<Integer, Instructor> instructors; // Map instructors by their ID
    private final Gym gym;

    private InstructorManagement() {
        this.instructors = new HashMap<>();
        this.gym = Gym.getInstance();
    }

    public static InstructorManagement getInstance() {
        if (instructorManagement == null) {
            instructorManagement = new InstructorManagement();
        }
        return instructorManagement;
    }

    public Map<Integer, Instructor> getInstructors() {
        return instructors;
    }

    public boolean isQualified(SessionType sessionType, Instructor instructor) {
        return instructor.getQualifications().contains(sessionType);
    }

    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getId(), instructor);
    }

    public Instructor newInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        ClientManagement clientManagement = ClientManagement.getInstance();

        // שליפת הלקוח הקיים עם הבלאנס המעודכן
        Client existingClient = clientManagement.getClients().get(person.getId());

        // אם הלקוח לא קיים, ניצור חדש
        if (existingClient == null) {
            existingClient = new Client(person);
            clientManagement.addToClients(existingClient);
        }

        // יצירת המדריך עם יתרת הבלאנס הנוכחית
        Instructor instructor = new Instructor(person, salaryPerHour, qualifications);
        instructor.setAccountBalance(existingClient.getAccountBalance()); // הבלאנס המעודכן מהלקוח

        instructors.put(instructor.getId(), instructor);

        return instructor;
    }





    public int calculateInstructorSalaries() {
        int totalInstructorSalary = 0;

        for (Instructor i : instructors.values()) {
            int sessionCount = i.getSessionsOfInstructor().size();
            int instructorSalary = sessionCount * i.getSalaryPerHour();

            totalInstructorSalary += instructorSalary;
        }

        return totalInstructorSalary;
    }
}
