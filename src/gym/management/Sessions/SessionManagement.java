package gym.management.Sessions;

import gym.DateUtils;
import gym.ForumType;
import gym.customers.Client;
import gym.management.Instructor;

import java.util.ArrayList;

public class SessionManagement {


    public boolean isSessionInFuture(Session session)
    {

        return DateUtils.dateNotPassed(session.getDate());
    }

    public static String isClientEligibleForForum(Client client, Session session) {
        ForumType forumType = session.getForumType();

        if (forumType == ForumType.All) {
            return null;
        }
        if (forumType == ForumType.Seniors && !client.isOverAge(65)) {
            return "Failed registration: Client doesn't meet the age requirements for this session (Seniors)";
        }
        if (forumType == ForumType.Female && client.getGender().equals("Female")) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }

        if (forumType == ForumType.Male && !client.getGender().equals("Male")) {
            return "Failed registration: Client's gender doesn't match the session's gender requirements";
        }
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
