package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.customers.Person;
import gym.management.Sessions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Secretary extends Person implements Subject {
    private final ClientManagement clientManagement = ClientManagement.getInstance();
    private final SessionManagement sessionManagement = SessionManagement.getInstance();
    private final InstructorManagement instructorManagement = InstructorManagement.getInstance();
    private final Gym gym = Gym.getInstance();
    private final List<Observer> observers = new ArrayList<>();
    private int salary;

    public Secretary(Person person, int salary) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salary = salary;
        gym.addOperations("A new secretary has started working at the gym: " + this.getName());
    }

    public int getSalary() {
        return salary;
    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        return clientManagement.registerNewClient(person);
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        if (!gym.isCurrentSecretary(this)) {
            System.out.println("Error: Former secretaries are not permitted to perform actions");
            return;
        }
        gym.addOperations("Unregistered client: " + client.getName());
        clientManagement.removeClient(client);
    }

    public void registerClientToSession(Client client, Session session)
            throws DuplicateClientException, ClientNotRegisteredException {
        if (!gym.isCurrentSecretary(this)) {
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
        ClientManagement.getInstance().saveBalance(client, client.getAccountBalance()); // Save updated balance
        gym.addToBalance(sessionPrice);
        session.registerClient(client);
        gym.addOperations("Registered client: " + client.getName() + " to session: " +
                session.getSessionType() + " on " + session.getDate() + " for price: " + sessionPrice);
    }

    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {
        Instructor instructor = instructorManagement.newInstructor(person, salaryPerHour, qualifications);
        gym.addOperations("Hired new instructor: " + instructor.getName() + " with salary per hour: " + instructor.getSalaryPerHour());
        return instructor;
    }

    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        if (!instructorManagement.isQualified(sessionType, instructor)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }

        Session session = SessionFactory.createSession(sessionType, date, forumType, instructor);
        sessionManagement.addToSessions(session);
        gym.addOperations("Created new session: " + session.getSessionType() +
                " on " + session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) +
                " with instructor: " + session.getInstructor().getName());
        return session;
    }

    public void paySalaries() {
        if (gym.getSecretary() != null) {
            Secretary secretary = gym.getSecretary();
            gym.addToBalance(-secretary.getSalary()); // הפחתת משכורת המזכיר
            secretary.setAccountBalance(secretary.getAccountBalance() + secretary.getSalary()); // עדכון הבלאנס של המזכיר
        }

        int totalInstructorSalary = instructorManagement.calculateInstructorSalaries();

        // עדכון יתרת הבלאנס של כל מדריך
        for (Instructor instructor : instructorManagement.getInstructors().values()) {
            int sessionCount = instructor.getSessionsOfInstructor().size();
            int salary = sessionCount * instructor.getSalaryPerHour();

            // שמירת הבלאנס לפני העדכון
            int balanceBefore = instructor.getAccountBalance();

            // עדכון הבלאנס של המדריך
            instructor.setAccountBalance(balanceBefore + salary);

            // הדפסת נתונים לאחר העדכון
            System.out.println("Instructor ID: " + instructor.getId());
            System.out.println("Balance Before: " + balanceBefore);
            System.out.println("Salary Added: " + salary);
            System.out.println("Balance After: " + instructor.getAccountBalance());
        }


        gym.addToBalance(-totalInstructorSalary); // הפחתת השכר הכולל מבלאנס המכון
        gym.addOperations("Salaries have been paid to all employees");
    }


    public void notify(Session session, String message) {
        for (Client client : session.getRegisteredToSession()) {
            if (!observers.contains(client)) {
                addObserver(client);
            }
        }
        notifyObservers(message);
    }

    public void notify(String date, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate inputDate = LocalDate.parse(date, formatter);

        List<Session> sessions = sessionManagement.getSessions();
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
