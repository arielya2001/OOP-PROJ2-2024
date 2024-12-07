package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.ForumType;
import gym.Gym;
import gym.Person;
import gym.Gender;
import gym.customers.Client;
import gym.customers.ClientRegistration;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Secretary extends Person {
    private int salaryPerMonth;
    private Gym gym;
    private List<String> actionsHistory = new ArrayList<>();

    private ClientRegistration clientRegistration;


    public Secretary()
    {
        this.clientRegistration=new ClientRegistration();

    }
    public Secretary(String name, int accountBalance, Gender gender, String dateOfBirth, int salaryPerMonth) {
        super(name, accountBalance, gender, dateOfBirth); // Call Person constructor
        this.salaryPerMonth = salaryPerMonth; // Initialize the salary
    }




    public Client registerClient (Person person) throws InvalidAgeException, DuplicateClientException
    {
        return clientRegistration.registerNewClient(person);
    }

    public void unregisterClient(Client client)throws ClientNotRegisteredException
    {
        clientRegistration.removeClient(client);
    }



    public void registerClientToLesson (Client client, Session session)
    {


    }




    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType>qualifications) {
        return new Instructor(person,salaryPerHour,qualifications);
    }


    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor)throws InstructorNotQualifiedException {
        if (!instructor.getQualifications().contains(sessionType))
        {
            throw new InstructorNotQualifiedException("Instructor is not qualified to conduct this session type.");
        }
        return new Session(sessionType,date,forumType,instructor);

    }

    public void paySalaries() {
    }
    public void printActions() {
        for (String action : actionsHistory) {
            System.out.println(action);
        }
    }
}
