package gym.management;

import gym.*;
import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionManagement;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Secretary extends Person implements Subject {
    private ClientManagement clientManagement;
    private SessionManagement sessionManagement;
    private InstructorManagement instructorManagement;
    private List<Observer> observers = new ArrayList<>();
    private int salary;
    private Notify notification;
    private Gym gym = Gym.getInstance();
    private boolean isDisabled = false;


    public Secretary(Person person, int salary) {
        super(person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salary = salary;
        this.clientManagement = new ClientManagement();
        this.sessionManagement = new SessionManagement();
        this.instructorManagement = new InstructorManagement();
        this.notification = new Notify();


    }

    public Secretary(String name, int accountBalance, Gender gender, String dateOfBirth, int salary) {
        super(name, accountBalance, gender, dateOfBirth);
        this.salary = salary;
        this.clientManagement = new ClientManagement();
        this.sessionManagement = new SessionManagement();
        this.notification = new Notify();
    }

    public void disabled() {
        this.isDisabled = true;
    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        gym.addOperations("Registered new client: " + person.getName());
        return clientManagement.registerNewClient(person);
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        notifyObservers("Unregistered client: " + client.getName());
        clientManagement.removeClient(client);
    }


    public void registerClientToLesson(Client client, Session session) throws DuplicateClientException, ClientNotRegisteredException {
        if (isDisabled)
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        ArrayList<String> validate = sessionManagement.validateClientForSession(client, session);
        if (!validate.isEmpty()) {
            for (String error : validate) {
                notifyObservers(error);
            }
            return;
        }
        if (!clientManagement.isClient(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        if (session.isRegisteredForSession(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }

        session.registerClient(client);
        int sessionPrice = session.getSessionPrice();
        client.deductBalance(sessionPrice);
        notifyObservers(client.getName() + " to session: " + session.getSessionType() + " on " + session.getDate() + " for price: " + sessionPrice);
        gym.addToBalance(sessionPrice);
    }


    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        notifyObservers("Hired new instructor: " + person.getName() + " with salary per hour: " + salaryPerHour);
        return new Instructor(person, salaryPerHour, qualifications);
    }


    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        if (!instructorManagement.isQualified(sessionType, instructor)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        notifyObservers("Created new session: " + sessionType + " on " + date + " with instructor: " + instructor.getName());
        return new Session(sessionType, date, forumType, instructor);

    }

    public void paySalaries() {


    }

    public void notify(Session session, String message) {
        notification.notifySession(session, message);
        notifyObservers("A message was sent to everyone registered for session " + session.getSessionType() + " on " + session.getDate() + " : " + message);
    }

    public void notify(String date, String message) {
        notification.notifyByDate(date, message);
        notifyObservers("A message was sent to everyone registered for a session on " + date + " : " + message);

    }

    public void notify(String message) {
        notification.notifyAllClients(message);
        notifyObservers("A message was sent to all gym clients: " + message);

    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }

    }

    @Override
    public String toString() {

        return String.format("Gym Secretary: ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: Secretary | Salary per Month: %d", getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance(), salary);
    }

    public void printActions() {
        for (String action : gym.getOperations()) {
            System.out.println(action);
        }
    }
}
