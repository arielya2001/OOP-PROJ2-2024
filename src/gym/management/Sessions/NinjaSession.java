package gym.management.Sessions;

import gym.management.Instructor;

public class NinjaSession extends Session {

    public NinjaSession(String date, ForumType forumType, Instructor instructor) {
        super(date, forumType, instructor);
    }

    @Override
    public int getSessionPrice() {
        return 150;
    }

    @Override
    public int getCapacity() {
        return 5;
    }

    @Override
    public boolean isInstructorQualified(Instructor instructor) {
        return instructor.getQualifications().contains("Ninja");
    }

}