package gym.management.Sessions;

import gym.management.DateUtils;
import gym.customers.Client;
import gym.management.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Session {
    private final SessionType sessionType;
    private LocalDateTime date;
    private ForumType forumType;
    private Instructor instructor;
    private ArrayList<Client> registeredToSession;

    public Session(SessionType sessionType, String date, ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType;
        this.date = DateUtils.parseDateTime(date);
        this.forumType = forumType;
        this.instructor = instructor;
        this.registeredToSession = new ArrayList<>();
    }



    public abstract int getSessionPrice();
    public abstract int getCapacity();
    public abstract boolean isInstructorQualified(Instructor instructor);

    // Getter methods
    public SessionType getSessionType() {
        return sessionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ArrayList<Client> getRegisteredToSession() {
        return registeredToSession;
    }

    // Business logic methods
    public boolean isSessionAvailable() {
        return getRegisteredToSession().size() < getCapacity();
    }

    public void registerClient(Client client) {
        if (isRegisteredForSession(client)) {
            return; // Prevent duplicate registration
        }
        registeredToSession.add(client);
    }


    public boolean isRegisteredForSession(Client client) {
        boolean isRegistered = registeredToSession.stream().anyMatch(c -> c.getId() == client.getId());
        return isRegistered;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format("Session Type: %s | Date: %s | Forum: %s | Instructor: %s | Participants: %d/%d",
                sessionType, date.format(formatter), forumType, instructor.getName(), registeredToSession.size(), getCapacity());
    }

}
