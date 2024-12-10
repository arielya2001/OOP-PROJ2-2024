package gym.management;

import gym.management.Sessions.SessionType;

public class InstructorManagement {



    public boolean isQualified(SessionType sessionType, Instructor instructor)
    {
        return (instructor.getQualifications().contains(sessionType));
    }



    
}
