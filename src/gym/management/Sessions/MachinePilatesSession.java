package gym.management.Sessions;

import gym.management.Instructor;

public class MachinePilatesSession extends Session {

    public MachinePilatesSession(String date, ForumType forumType, Instructor instructor) {
        super(date, forumType, instructor);
    }

    @Override
    public int getSessionPrice() {
        return 80;
    }

    @Override
    public int getCapacity() {
        return 10;
    }

    @Override
    public boolean isInstructorQualified(Instructor instructor) {
        return instructor.getQualifications().contains("MachinePilates");
    }
}
