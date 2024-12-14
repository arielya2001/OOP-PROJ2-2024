**RECENTLY ADDED:
FIXED STRUCT, OutputComperator, Balance calculation, Notify(now Client class is an Observer and Gym class is the Subject)  and etc.
Secreteries - added list of secreteries to track the old ones and be able to throw the error:
Error: Former secretaries are not permitted to perform actions
ID - started from 1111 instead of 1110(don't know if true or not)
people HashMap - The people map is checked to see if the person already exists. If yes, the existing Person object is reused instead of creating a duplicate. Also easier to search with unique ID. SEE EXAMPLE IN "addClient" METHOD
BalanceHistory HashMap - key is the ID of the person, value is the most recenet balance he has. good when changing roles of people in the gym
Current YEAR IS 2024, NEED TO CHANGE TO 2025??**



gym.customers.Person
Attributes:
name (String)
salary (int) - Represents salary or financial balance.
gender (gym.customers.Gender enum)
birthDate (String)

Methods:
gym.customers.Person(String name, int salary, gym.customers.Gender gender, String birthDate)
String getName()
void setName(String name)
  int getSalary()
void setSalary(int salary)
gym.customers.Gender getGender()
void setGender(gym.customers.Gender gender)
String getBirthDate()
void setBirthDate(String birthDate)
String toString()


2. gym.customers.Client (Inherits from gym.customers.Person)
Attributes:
notifications (List<String>) - List of messages received.

Methods:
List<String> getNotifications()
Other client-specific methods (e.g., session registration) may exist but are not explicitly detailed in the code.


3. gym.management.Instructor (Inherits from gym.customers.Person)
Attributes:
qualifications (List<gym.management.Sessions.SessionType>) - Types of sessions the instructor is qualified to lead.

Methods:
List<gym.management.Sessions.SessionType> getQualifications()
void addQualification(gym.management.Sessions.SessionType type)


4. gym.management.Secretary (Inherits from gym.customers.Person)
Attributes:
Likely manages client and instructor registrations, session scheduling, etc.

Methods:
gym.customers.Client registerClient(gym.customers.Person person)
void unregisterClient(gym.customers.Client client)
gym.management.Instructor hireInstructor(gym.customers.Person person, int hourlyPay, List<gym.management.Sessions.SessionType> qualifications)
gym.management.Sessions.Session addSession(gym.management.Sessions.SessionType type, String dateTime, gym.management.Sessions.ForumType forum, gym.management.Instructor instructor)
void registerClientToLesson(gym.customers.Client client, gym.management.Sessions.Session session)
void notify(gym.management.Sessions.Session session, String message)
void notify(String date, String message)
void notify(String message)
void paySalaries()
void printActions()


5. gym.management.Gym
Attributes:
name (String)
secretary (gym.management.Secretary)
Methods:
static gym.management.Gym getInstance() - Singleton pattern.
void setName(String name)
gym.management.Secretary getSecretary()
void setSecretary(gym.customers.Person person, int salary)


6. gym.management.Sessions.Session
Attributes:
gym.management.Sessions.SessionType type
String dateTime
gym.management.Sessions.ForumType forum
gym.management.Instructor instructor

Methods:
gym.management.Sessions.Session(gym.management.Sessions.SessionType type, String dateTime, gym.management.Sessions.ForumType forum, gym.management.Instructor instructor)
gym.management.Sessions.SessionType getType()
void setType(gym.management.Sessions.SessionType type)
String getDateTime()
void setDateTime(String dateTime)
gym.management.Sessions.ForumType getForum()
void setForum(gym.management.Sessions.ForumType forum)
gym.management.Instructor getInstructor()
void setInstructor(gym.management.Instructor instructor)
String toString()


7. Enums
gym.customers.Gender:
Male
Female

gym.management.Sessions.SessionType:
Pilates
ThaiBoxing
MachinePilates
Ninja

gym.management.Sessions.ForumType:
All
Male
Female
Seniors

8. Exceptions
Custom Exceptions:
gym.Exception.InstructorNotQualifiedException - Thrown if an instructor cannot lead a session.
DuplicateClientException - Thrown if a client is already registered.
InvalidAgeException - Thrown if a person does not meet the age criteria.
ClientNotRegisteredException - Thrown if a client is not found in the system.
