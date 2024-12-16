package gym.management.Sessions;

import gym.management.Instructor;

public class SessionFactory {

    public static Session createSession(SessionType type, String date, ForumType forumType, Instructor instructor) {
        switch (type) {
            case Pilates:
                return new PilatesSession(date, forumType, instructor);
            case MachinePilates:
                return new MachinePilatesSession(date, forumType, instructor);
            case ThaiBoxing:
                return new ThaiBoxingSession(date, forumType, instructor);
            case Ninja:
                return new NinjaSession(date, forumType, instructor);
            default:
                throw new IllegalArgumentException("Unknown session type: " + type);
        }
    }
}
