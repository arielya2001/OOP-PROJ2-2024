package gym.management.Sessions;

import java.util.Map;

public class SessionFactory {

    public static int getPrice(SessionType sessionType) {
        switch (sessionType) {
            case Pilates:
                return 60;
            case MachinePilates:
                return 80;
            case ThaiBoxing:
                return 100;
            case Ninja:
                return 150;
            default:
                throw new IllegalArgumentException("Unknown session type: " + sessionType);
        }
    }

    public static int getCapacity(SessionType sessionType)
    {
        switch (sessionType) {
            case Pilates:
                return 30;
            case MachinePilates:
                return 10;
            case ThaiBoxing:
                return 20;
            case Ninja:
                return 5;
            default:
                throw new IllegalArgumentException("Unknown session type: " + sessionType);
        }

    }



}
