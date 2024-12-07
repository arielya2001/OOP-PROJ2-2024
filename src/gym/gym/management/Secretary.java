package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.ForumType;
import gym.Gender;
import gym.Gym;
import gym.Person;
import gym.customers.Client;
import gym.customers.ClientManagement;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionFactory;
import gym.management.Sessions.SessionManagement;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

public class Secretary extends Person {
    private ClientManagement clientManagement;
    private SessionManagement sessionManagement;
    private InstructorManagement instructorManagement;
    private int salary;
    private Gym gym;


    public Secretary(Person person ,int salary) {
        super(person.getName(), person.getAccountBalance(), person.getGender(), person.getDateOfBirth());
        this.salary=salary;
        this.clientManagement =new ClientManagement();
        this.sessionManagement=new SessionManagement();
        this.instructorManagement=new InstructorManagement();
    }
    public Secretary(String name, int accountBalance, Gender gender, String dateOfBirth ,int salary) {
        super(name, accountBalance, gender, dateOfBirth);
        this.salary=salary;
        this.clientManagement =new ClientManagement();
        this.sessionManagement=new SessionManagement();
    }



    public Client registerClient (Person person) throws InvalidAgeException, DuplicateClientException
    {
        gym.addOperations("Registered new client: "+person.getName());
       return clientManagement.registerNewClient(person);
    }

    public void unregisterClient(Client client)throws ClientNotRegisteredException
    {
        gym.addOperations("Unregistered client: "+client.getName());
        clientManagement.removeClient(client);
    }



    public void registerClientToLesson (Client client, Session session) throws DuplicateClientException,ClientNotRegisteredException {
        ArrayList<String>validate=sessionManagement.validateClientForSession(client,session);
        if (!validate.isEmpty())
        {
            for (String error: validate)
            {
                gym.addOperations(error);
            }
            return;
        }
        if(!clientManagement.isClient(client)){
            throw new ClientNotRegisteredException("The client is not registered with the gym and cannot enroll in lessons");
        }

        if (session.getRegisteredToSession().contains(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }
        session.registerClient(client);
        int sessionPrice = sessionManagement.getSessionPrice(session);
        client.deductBalance(sessionPrice);
        gym.addOperations(client.getName()+" to session: "+session.getSessionType()+" on "+session.getDate()+" for price: "+sessionPrice);
        gym.addToBalance(sessionPrice);
    }




    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType>qualifications) {
        return new Instructor(person,salaryPerHour,qualifications);
    }


    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor)throws InstructorNotQualifiedException {
        if (!instructorManagement.isQualified(sessionType,instructor))
        {
            throw new InstructorNotQualifiedException("Instructor is not qualified to conduct this session type.");
        }
        return new Session(sessionType,date,forumType,instructor);

    }

    public void paySalaries() {

    }
}
