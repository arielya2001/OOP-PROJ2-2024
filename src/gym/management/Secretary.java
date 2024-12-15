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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Secretary extends Person {
    private ClientManagement clientManagement;
    private SessionManagement sessionManagement;
    private InstructorManagement instructorManagement;
    private int salary;
    private Notify notification = new Notify();
    private Gym gym = Gym.getInstance();

    public Secretary(Person person, int salary) {
        super(person.getId(), person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salary = salary;
        this.clientManagement = new ClientManagement();
        this.sessionManagement = new SessionManagement();
        this.instructorManagement = new InstructorManagement();
        gym.addOperations("A new secretary has started working at the gym: " + this.getName());
    }

    public Secretary(String name, int accountBalance, Gender gender, String dateOfBirth, int salary) {
        super(name, accountBalance, gender, dateOfBirth);
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
        Client c = clientManagement.registerNewClient(person);
        return c;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        clientManagement.unregisterClient(client);
    }


    public void registerClientToLesson(Client client, Session session)
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

        Person existingPerson = gym.getPeopleMap().get(person.getId());
        if (existingPerson == null) {
            existingPerson = person;
        }

        Instructor instructor = new Instructor(existingPerson, salaryPerHour, qualifications);

        instructorManagement.addInstructor(instructor, gym.getInstructors());
        gym.addOperations("Hired new instructor: " + instructor.getName() + " with salary per hour: " + instructor.getSalaryPerHour());

        gym.getPeopleMap().put(instructor.getId(), instructor);

        return instructor;
    }


    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        InstructorManagement instructorManagement = new InstructorManagement();
        if (!instructorManagement.isQualified(sessionType, instructor)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        if (!instructorManagement.isInstructorAvailable(instructor, gym.getSessions(), date)) {
            throw new InstructorNotQualifiedException("Error: Instructor is already booked for another session at this time.");
        }

        Session session = SessionFactory.createSession(sessionType, date, forumType, instructor);

        gym.getSessions().add(session);
        gym.addOperations("Created new session: " + session.getSessionType() +
                " on " + session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) +
                " with instructor: " + instructor.getName());
        return session;
    }



    public void paySalaries() {
        // Deduct secretary's salary if the gym has an active secretary
        if (gym.getSecretary() != null) {
            Secretary secretary = gym.getSecretary();
            int secretarySalary = secretary.getSalary();
            gym.addToBalance(-secretarySalary);
        }
        int totalInstructorSalary = 0;

        for (Instructor instructor : gym.getInstructors()) {
            int sessionCount = 0;

            for (Session session : gym.getSessions()) {
                if (session.getInstructor().equals(instructor)) {
                    sessionCount++;
                }
            }

            int instructorSalary = sessionCount * instructor.getSalaryPerHour();
            totalInstructorSalary += instructorSalary;

            int oldBalance = instructor.getAccountBalance();
            instructor.setAccountBalance(oldBalance + instructorSalary);

            Client correspondingClient = null;
            for (Client client : gym.getClients()) {
                if (client.getId() == instructor.getId()) {
                    correspondingClient = client;
                    break;
                }
            }

            if (correspondingClient != null) {
                correspondingClient.setAccountBalance(instructor.getAccountBalance());
            }
        }

        gym.addToBalance(-totalInstructorSalary);
        gym.addOperations("Salaries have been paid to all employees");
    }


    public void notify(Session session, String message) {
        if (session != null) {
            notification.notifySession(session, message);
            gym.addOperations("A message was sent to everyone registered for session " + session.getSessionType() +
                    " on " + session.getDate() +
                    " : " + message);
        } else {
            gym.addOperations("Failed to notify: Session is null.");
        }
    }

    public void notify(String date, String message) {
        notification.notifyByDate(date, message);
    }

    public void notify(String message) {
        notification.notifyAllClients(message);
        gym.addOperations("A message was sent to all gym clients: " + message);
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


}
