package gym.management.Sessions;

import gym.management.Instructor;

public class ThaiBoxingSession extends Session {

    public ThaiBoxingSession(String date, ForumType forumType, Instructor instructor) {
        super(SessionType.ThaiBoxing, date, forumType, instructor);
    }

    @Override
    public int getSessionPrice() {
        return 100;
    }

    @Override
    public int getCapacity() {
        return 20;
    }

    @Override
    public boolean isInstructorQualified(Instructor instructor) {
        return instructor.getQualifications().contains(getSessionType());
    }
}
