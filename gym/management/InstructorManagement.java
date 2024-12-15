package gym.management;

import gym.management.Sessions.Session;
import gym.management.Sessions.SessionManagement;
import gym.management.Sessions.SessionType;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class InstructorManagement {
    private static List<Instructor> instructors = new ArrayList<>();

    public static List<Instructor> getInstructors() {
        return instructors;
    }

    public boolean isQualified(SessionType sessionType, Instructor instructor) {
        return instructor.getQualifications().contains(sessionType);
    }

    public void addInstructor(Instructor instructor)
    {

        instructors.add(instructor);
    }

    public int calculateInstructorSalaries() {

        int totalInstructorSalary = 0;
        for (Instructor i :instructors)
        {
            int sessionCount=i.getSessionsOfInstructor().size();
            int instructorSalary=sessionCount*i.getSalaryPerHour();

            totalInstructorSalary+=instructorSalary;
            i.setAccountBalance(i.getAccountBalance() + totalInstructorSalary);
        }
        return totalInstructorSalary;
    }

}

