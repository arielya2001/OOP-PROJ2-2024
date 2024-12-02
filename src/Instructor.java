import java.util.ArrayList;

public class Instructor extends Person{

    private int salaryPerHour;
    private ArrayList<SessionType>qualifications;

    public Instructor(String name, int accountBalance, Gender gender, String dateOfBirth, int salaryPerHour,ArrayList<SessionType>qualifications) {
        super(name, accountBalance, gender, dateOfBirth);
        this.salaryPerHour=salaryPerHour;
        this.qualifications=new ArrayList<>(qualifications);
    }

    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public ArrayList<SessionType> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<SessionType> qualifications) {
        this.qualifications = qualifications;
    }
}
