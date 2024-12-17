package gym.management.Sessions;

import gym.management.Instructor;

public class PilatesSession extends Session {

    public PilatesSession(String date, ForumType forumType, Instructor instructor) {
        super(date, forumType, instructor);
    }

    @Override
    public int getSessionPrice() {
        return 60;
    }

    @Override
    public int getCapacity() {
        return 30;
    }

    @Override
    public boolean isInstructorQualified(Instructor instructor) {
        return instructor.getQualifications().contains("Pilates");
    }
}