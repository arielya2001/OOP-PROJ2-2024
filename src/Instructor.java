import java.util.ArrayList;

public class Instructor extends Person{

    private int salaryPerHour;
    private ArrayList<SessionType>qualifications;
    public Instructor(String name, int accountBalance, Gender gender, String dateOfBirth, int salaryPerHour) {
        super(name, accountBalance, gender, dateOfBirth);
        this.salaryPerHour=salaryPerHour;
        this.qualifications=new ArrayList<>();
    }
}
