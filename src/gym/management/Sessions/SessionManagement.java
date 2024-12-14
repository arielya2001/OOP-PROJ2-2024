package gym.management.Sessions;

import gym.Gender;
import gym.customers.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SessionManagement {


    public boolean isSessionInFuture(Session session) {
        return session.getDate().isAfter(LocalDateTime.now());
    }


    public static String isClientEligibleForForum(Client client, Session session) {
        ForumType forumType = session.getForumType();

        // ForumType.All allows any client
        if (forumType == ForumType.All) {
            return null;
        }

        // Check age for seniors
        if (forumType == ForumType.Seniors && !client.isOverAge(65)) {
            return "Failed registration: Client doesn't meet the age requirements for this session (Seniors)";
        }

        // Check gender compatibility
        if (forumType == ForumType.Female && client.getGender() != Gender.Female) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }
        if (forumType == ForumType.Male && client.getGender() != Gender.Male) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }

        // No issues
        return null;
    }



    public ArrayList<String>validateClientForSession(Client client,Session session)
    {
        ArrayList<String>errors=new ArrayList<>();
        if (!isSessionInFuture(session))
            errors.add("Failed registration: Session is not in the future");

        String eligibilityError = isClientEligibleForForum(client, session);
        if (eligibilityError != null) {
            errors.add(eligibilityError);
        }
        if (!client.clientHasSufficientBalance(session)) {
            errors.add("Failed registration: Insufficient balance for this session");
        }
        if (!session.isSessionAvailable()) {
            errors.add("Failed registration: No available places in the session");
        }
        return errors;
    }








}
