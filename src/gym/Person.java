package gym;

public class Person {
    private String name;
    private Gender gender;
    private int accountBalance;
    private  String dateOfBirth;
    private static int nextId=1110;
    private int id;

    public Person(String name,int accountBalance,Gender gender, String dateOfBirth)
    {
        this.id=nextId++;
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

    public int getId() {
        return id;
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

    public int getAge()
    {
        int currentYear=2024;
        String birthYear=dateOfBirth.substring(6,10);
        int year=Integer.parseInt(birthYear);
        return currentYear-year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return id == person.id;
    }


    public String toString()
    {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d",
                id, name, gender, dateOfBirth, getAge(), accountBalance);
    }

}
