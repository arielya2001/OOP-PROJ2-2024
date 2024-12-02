import java.util.ArrayList;

public class Secretary {




    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType>qualifications) {
        return new Instructor(person.getName(),person.getAccountBalance(),person.getGender(),person.getDateOfBirth(),salaryPerHour,qualifications);
    }


    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor)throws InstructorNotQualifiedException  {
        if (!instructor.getQualifications().contains(sessionType))
        {
            throw new InstructorNotQualifiedException("The instructor is not authorized to teach "+sessionType);
        }
        return new Session(sessionType,date,forumType,instructor);

    }
}
