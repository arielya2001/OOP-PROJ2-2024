package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.Gender;
import gym.Gym;
import gym.Person;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.management.Sessions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Secretary extends Person {
    private ClientManagement clientManagement;
    private SessionManagement sessionManagement;
    private InstructorManagement instructorManagement;
    private int salary;
    private Notify notification = new Notify();
    private Gym gym = Gym.getInstance();
    private boolean isDisabled = false;


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




    public void disabled() {
        this.isDisabled = true;
    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {

        // Check for duplicate registration
        if (gym.getClients().stream().anyMatch(client -> client.getId() == person.getId())) {
            throw new DuplicateClientException("Error: The client is already registered.");
        }

        // Validate age
        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register.");
        }

        // Create client using the existing Person ID
        Client client = new Client(person);
        gym.addClient(client);
        gym.addOperations("Registered new client: " + client.getName());
        return client;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        boolean removed = gym.getClients().removeIf(c -> c.equals(client));
        if (!removed) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }

        // Update balance history before removing the client
        gym.updateBalanceHistory(client.getId(), client.getAccountBalance());

        gym.addOperations("Unregistered client: " + client.getName());
    }

    public void registerClientToLesson(Client client, Session session)
            throws DuplicateClientException, ClientNotRegisteredException {
        // Check if this secretary is the active one
        if (!this.equals(gym.getSecretary())) {
            System.out.println("Error: Former secretaries are not permitted to perform actions");
            return; // Immediately stop execution
        }

        // Validate if the client is registered with the gym
        if (!gym.getClients().contains(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        // Validate if the client is already registered for the session
        if (session.isRegisteredForSession(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }


        // Perform all validations using `validateClientForSession`
        ArrayList<String> validationErrors = sessionManagement.validateClientForSession(client, session);

        // Log all validation errors in the gym's action history
        for (String error : validationErrors) {
            gym.addOperations(error); // Standardize message prefix
        }

        // If any validation errors exist, stop further processing
        if (!validationErrors.isEmpty()) {
            return;
        }

        // Deduct session price, add to gym balance, and register the client
        int sessionPrice = session.getSessionPrice();
        client.deductBalance(sessionPrice);
        gym.addToBalance(sessionPrice);
        session.registerClient(client);
        gym.addOperations("Registered client: " + client.getName() + " to session: " +
                session.getSessionType() + " on " + session.getDate() + " for price: " + sessionPrice);
    }



    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType> qualifications) {

        // Reuse the existing person instance from the global map
        Person existingPerson = gym.getPeopleMap().get(person.getId());
        if (existingPerson == null) {
            existingPerson = person; // If not found, use the given person
        }

        // Transform into an instructor
        Instructor instructor = new Instructor(existingPerson, salaryPerHour, qualifications);

        // Add to instructors list
        gym.addInstructor(instructor);
        gym.addOperations("Hired new instructor: " + instructor);

        // Ensure the balance is in sync
        gym.getPeopleMap().put(instructor.getId(), instructor);

        return instructor;
    }





    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        InstructorManagement instructorManagement = new InstructorManagement();
        if (!instructorManagement.isQualified(sessionType, instructor)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        Session session = SessionFactory.createSession(sessionType, date, forumType, instructor);

        // Add the session to the gym's list and the instructor's history
        gym.getSessions().add(session);
        gym.addOperations("Created new session: " + session.getSessionType() +
                " on " + session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) +
                " with instructor: " + instructor.getName());
        return session;
    }



    public void paySalaries() {
        if (gym.getSecretary() != null) {
            Secretary secretary = gym.getSecretary();
            int secretarySalary = secretary.getSalary();
            gym.addToBalance(-secretarySalary); // Deduct secretary salary from gym balance
        }
        int sum=0;
        for (Instructor instructor : gym.getInstructors()) {
            long sessionCount = gym.getSessions().stream()
                    .filter(session -> session.getInstructor().equals(instructor))
                    .count();
            int salary = (int) sessionCount * instructor.getSalaryPerHour();
            sum+=salary;

            int oldBalance = instructor.getAccountBalance();
            instructor.setAccountBalance(oldBalance + salary);

            // Sync balance in the Client list if this person is also a client
            Client client = gym.getClients().stream()
                    .filter(cl -> cl.getId() == instructor.getId())
                    .findFirst()
                    .orElse(null);
            if (client != null) {
                client.setAccountBalance(instructor.getAccountBalance());
            }
        }
        gym.addToBalance(-sum); // Deduct secretary salary from gym balance
        gym.addOperations("Salaries have been paid to all employees");
    }














    public void notify(Session session, String message) {
        if (session != null) {
            notification.notifySession(session, message);
            gym.addOperations("A message was sent to everyone registered for session " + session.getSessionType() +
                    " on " + session.getDate() + " : " + message);
        } else {
            gym.addOperations("Failed to notify: Session is null.");
        }
    }


    public void notify(String date, String message) {
        notification.notifyByDate(date, message);
        gym.addOperations("A message was sent to everyone registered for a session on " + date + " : " + message);
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
