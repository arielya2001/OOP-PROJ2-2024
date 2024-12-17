package gym.management.Sessions;

import gym.management.Instructor;

public class SessionFactory {

    public static Session createSession(String sessionType, String date, ForumType forumType, Instructor instructor) {
        switch (sessionType.toLowerCase()) {
            case "pilates":
                return new PilatesSession(date, forumType, instructor);
            case "machinepilates":
                return new MachinePilatesSession(date, forumType, instructor);
            case "thaiboxing":
                return new ThaiBoxingSession(date, forumType, instructor);
            case "ninja":
                return new NinjaSession(date, forumType, instructor);
            default:
                throw new IllegalArgumentException("Unknown session type: " + sessionType);
        }
    }
}