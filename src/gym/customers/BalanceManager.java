package gym.customers;

import java.util.HashMap;
import java.util.Map;

public class BalanceManager {
    private static final Map<Integer, Integer> balances = new HashMap<>();

    public static void initializeBalance(int id, int initialBalance) {
        balances.putIfAbsent(id, initialBalance);
    }

    public static void updateBalance(int id, int amount) {
        balances.put(id, balances.getOrDefault(id, 0) + amount);
    }

    public static int getBalance(int id) {
        return balances.getOrDefault(id, 0);
    }
}