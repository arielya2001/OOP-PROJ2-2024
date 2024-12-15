package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Sessions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Secretary extends Person implements Subject {
    private ClientManagement clientManagement;
    private SessionManagement sessionManagement;
    private InstructorManagement instructorManagement;
    private int salary;

    private Gym gym = Gym.getInstance();
    private List<Observer> observers = new ArrayList<>();

    public Secretary(Person person, int salary) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salary = salary;
        this.clientManagement = new ClientManagement();
        this.sessionManagement = new SessionManagement();
        this.instructorManagement = new InstructorManagement();
        gym.addOperations("A new secretary has started working at the gym: " + this.getName());
    }

    public int getSalary() {
        return salary;
    }


    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        return clientManagement.registerNewClient(person);
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        clientManagement.unregisterClient(client);
        observers.remove(client);
    }


    public void registerClientToSession(Client client, Session session)
            throws DuplicateClientException, ClientNotRegisteredException {
        if (gym.getAllSecretaries().contains(this) && !this.equals(gym.getSecretary())) {
            System.out.println("Error: Former secretaries are not permitted to perform actions");
            return;
        }

        if (!clientManagement.isClient(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        if (session.isRegisteredForSession(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }
        ArrayList<String> validationErrors = sessionManagement.validateClientForSession(client, session);
        for (String error : validationErrors) {
            gym.addOperations(error);
        }
        if (!validationErrors.isEmpty()) {
            return;
        }
        int sessionPrice = session.getSessionPrice();
        client.deductBalance(sessionPrice);
        gym.addToBalance(sessionPrice);
        session.registerClient(client);
        gym.addOperations("Registered client: " + client.getName() + " to session: " +
                session.getSessionType() + " on " + session.getDate() + " for price: " + sessionPrice);
    }




    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        Instructor instructor = new Instructor(person, salaryPerHour, qualifications);
        instructorManagement.addInstructor(instructor);
        gym.addOperations("Hired new instructor: " + instructor.getName() + " with salary per hour: " + instructor.getSalaryPerHour());
        return instructor;
    }

    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        InstructorManagement instructorManagement = new InstructorManagement();
        if (!instructorManagement.isQualified(sessionType, instructor)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        Session session = SessionFactory.createSession(sessionType, date, forumType, instructor);

        SessionManagement.addToSessions(session);
        gym.addOperations("Created new session: " + session.getSessionType() +
                " on " + session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) +
                " with instructor: " + session.getInstructor().getName());
        return session;
    }



    public void paySalaries() {
        if (gym.getSecretary() != null) {
            Secretary secretary = gym.getSecretary();
            int secretarySalary = secretary.getSalary();
            gym.addToBalance(-secretarySalary);
        }
        int totalInstructorSalary=instructorManagement.calculateInstructorSalaries();
        gym.addToBalance(-totalInstructorSalary);
        gym.addOperations("Salaries have been paid to all employees");
    }

    public void notify(Session session, String message) {
        for (Client client : session.getRegisteredToSession()) {
            addObserver(client);
        }
        notifyObservers(message);
        observers.clear();
    }


    public void notify(String date, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate inputDate = LocalDate.parse(date, formatter);

        List<Session> sessions = SessionManagement.getSessions(); // קבלת כל השיעורים
        for (Session session : sessions) {
            if (session.getDate().toLocalDate().equals(inputDate)) {
                notify(session, message);
            }
        }
    }
    public void notify(String message) {
        notifyObservers(message);
    }



    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: Secretary | Salary per Month: %d",
                getId(), getName(), getGender(), getDateOfBirth(), getAge(), getAccountBalance(), salary);
    }

    public void printActions() {
        for (String action : gym.getOperations()) {
            System.out.println(action);
        }
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
}
