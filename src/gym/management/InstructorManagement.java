package gym.management;

import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.List;

public class InstructorManagement {

    public boolean isQualified(SessionType sessionType, Instructor instructor) {
        return instructor.getQualifications().contains(sessionType);
    }

    public boolean isInstructorAvailable(Instructor instructor, List<Session> sessions, String sessionDateTime) {
        for (Session session : sessions) {
            if (session.getInstructor().equals(instructor) && session.getDate().toString().equals(sessionDateTime)) {
                return false;
            }
        }
        return true;
    }

    public void addInstructor(Instructor instructor, List<Instructor> instructors) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
        }
    }
}
