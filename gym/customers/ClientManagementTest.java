package gym.tests;

import gym.customers.BalanceManager;
import gym.customers.Client;
import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Gym;
import gym.management.Secretary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceManagerIntegrationTest {

    private Gym gym;
    private Person person;
    private Client client;
    private Secretary secretary;

    @BeforeEach
    public void setUp() {
        gym = Gym.getInstance();

        // Create a new Person with an initial balance
        person = new Person("John Doe", 1000, Gender.Male, "01-01-1990");
        BalanceManager.initializeBalance(person.getId(), 1000);

        client = new Client(person); // Create a Client from the Person
    }

    @Test
    public void testClientBalanceAfterDeduction() {
        // Deduct balance from Client
        client.deductBalance(200);

        // Check balance dynamically from BalanceManager
        int expectedBalance = 800;
        int actualBalance = BalanceManager.getBalance(client.getId());

        assertEquals(expectedBalance, actualBalance, "Balance after deduction should be 800");
    }

    @Test
    public void testSecretaryBalanceMatchesClientAfterCreation() {
        // Deduct balance for the Client
        client.deductBalance(200);

        // Set the same Person as Secretary
        gym.setSecretary(person, 3000);
        secretary = gym.getSecretary();

        // Verify Secretary's balance is dynamically consistent
        int expectedBalance = 800;
        int actualBalance = BalanceManager.getBalance(secretary.getId());

        assertEquals(expectedBalance, actualBalance, "Secretary balance should match updated Client balance");
    }

    @Test
    public void testMultipleRolesShareSameBalance() {
        // Deduct balance for the Client
        client.deductBalance(200);

        // Set the same Person as Secretary
        gym.setSecretary(person, 3000);
        secretary = gym.getSecretary();

        // Verify dynamic balance consistency across all roles
        int expectedBalance = 800;

        assertEquals(expectedBalance, BalanceManager.getBalance(person.getId()), "Person balance should be consistent");
        assertEquals(expectedBalance, BalanceManager.getBalance(client.getId()), "Client balance should be consistent");
        assertEquals(expectedBalance, BalanceManager.getBalance(secretary.getId()), "Secretary balance should be consistent");
    }
}
