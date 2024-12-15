package gym.management;

import gym.management.Sessions.Session;

import java.util.List;

public class InstructorManagement {

    public boolean isQualified(String sessionType, Instructor instructor) {
        // Compare session type using the session class name (e.g., "Pilates", "Ninja")
        for (String qualification : instructor.getQualifications()) {
            if (qualification.equalsIgnoreCase(sessionType)) {
                return true;
            }
        }
        return false;
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
