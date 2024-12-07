package gym;

public class Person {
    private String name;
    private Gender gender;
    private int accountBalance;
    private  String dateOfBirth;

    public Person(String name,int accountBalance,Gender gender, String dateOfBirth)
    {
        this.name=name;
        this.accountBalance=accountBalance;
        this.gender=gender;
        this.dateOfBirth=dateOfBirth;
    }

    public String getName() {
        return this.name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public int getAccountBalance() {
        return this.accountBalance;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public boolean isOverAge(int Age)
    {
        return DateUtils.isOverAge(getDateOfBirth(),Age);
    }
}
