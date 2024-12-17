package gym.management.Sessions;

import gym.customers.Gender;
import gym.customers.Client;
import gym.management.Gym;
import gym.management.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessionManagement {

    private static SessionManagement sessionManagement ;

    private final List<Session> sessions;
    private final Gym gym;

    private SessionManagement() {
        this.sessions = new ArrayList<>();
        this.gym = Gym.getInstance();
    }

    public static SessionManagement getInstance() {
        if (sessionManagement == null)

            sessionManagement = new SessionManagement();

        return sessionManagement;
    }


    public List<Session> getSessions() {
        return sessions;
    }

    public void addToSessions(Session session) {
        sessions.add(session);
        session.getInstructor().addToSessionList(session);
    }

    public boolean isSessionInFuture(Session session) {
        return session.getDate().isAfter(LocalDateTime.now());
    }

    public static String isClientEligibleForForum(Client client, Session session) {
        ForumType forumType = session.getForumType();

        if (forumType == ForumType.All) {
            return null;
        }

        if (forumType == ForumType.Seniors && !client.isOverAge(65)) {
            return "Failed registration: Client doesn't meet the age requirements for this session (Seniors)";
        }

        if (forumType == ForumType.Female && client.getGender() != Gender.Female) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }
        if (forumType == ForumType.Male && client.getGender() != Gender.Male) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }

        return null;
    }

    public ArrayList<String> validateClientForSession(Client client, Session session) {
        ArrayList<String> errors = new ArrayList<>();
        if (!isSessionInFuture(session)) {
            errors.add("Failed registration: Session is not in the future");
        }

        String eligibilityError = isClientEligibleForForum(client, session);
        if (eligibilityError != null) {
            errors.add(eligibilityError);
        }
        if (!client.clientHasSufficientBalance(session)) {
            errors.add("Failed registration: Client doesn't have enough balance");
        }
        if (!session.isSessionAvailable()) {
            errors.add("Failed registration: No available spots for session");
        }
        return errors;
    }
}




