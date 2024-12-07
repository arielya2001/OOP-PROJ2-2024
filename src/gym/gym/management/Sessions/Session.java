package gym.management.Sessions;

import gym.ForumType;
import gym.customers.Client;
import gym.management.Instructor;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private SessionType sessionType;
    private String date;
    private ForumType forumType;
    private Instructor instructor;
    private ArrayList<Client>registeredToSession;

    public Session(SessionType sessionType,String date,ForumType forumType,Instructor instructor){
        this.sessionType=sessionType;
        this.date=date;
        this.forumType=forumType;
        this.instructor=instructor;
        registeredToSession=new ArrayList<>();
    }

    public SessionType getSessionType() {
        return this.sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ForumType getForumType() {
        return this.forumType;
    }

    public void setForumType(ForumType forumType) {
        this.forumType = forumType;
    }

    public Instructor getInstructor() {
        return this.instructor;
    }

    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    public ArrayList<Client> getRegisteredToSession() {
        return registeredToSession;
    }

    public void registerClient(Client client) {
        registeredToSession.add(client);
    }
}
