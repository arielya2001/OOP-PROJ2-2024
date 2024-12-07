gym.Person
Attributes:
name (String)
salary (int) - Represents salary or financial balance.
gender (gym.Gender enum)
birthDate (String)

Methods:
gym.Person(String name, int salary, gym.Gender gender, String birthDate)
String getName()
void setName(String name)
  int getSalary()
void setSalary(int salary)
gym.Gender getGender()
void setGender(gym.Gender gender)
String getBirthDate()
void setBirthDate(String birthDate)
String toString()


2. gym.customers.Client (Inherits from gym.Person)
Attributes:
notifications (List<String>) - List of messages received.

Methods:
List<String> getNotifications()
Other client-specific methods (e.g., session registration) may exist but are not explicitly detailed in the code.


3. gym.management.Instructor (Inherits from gym.Person)
Attributes:
qualifications (List<gym.management.Sessions.SessionType>) - Types of sessions the instructor is qualified to lead.

Methods:
List<gym.management.Sessions.SessionType> getQualifications()
void addQualification(gym.management.Sessions.SessionType type)


4. gym.management.Secretary (Inherits from gym.Person)
Attributes:
Likely manages client and instructor registrations, session scheduling, etc.

Methods:
gym.customers.Client registerClient(gym.Person person)
void unregisterClient(gym.customers.Client client)
gym.management.Instructor hireInstructor(gym.Person person, int hourlyPay, List<gym.management.Sessions.SessionType> qualifications)
gym.management.Sessions.Session addSession(gym.management.Sessions.SessionType type, String dateTime, gym.ForumType forum, gym.management.Instructor instructor)
void registerClientToLesson(gym.customers.Client client, gym.management.Sessions.Session session)
void notify(gym.management.Sessions.Session session, String message)
void notify(String date, String message)
void notify(String message)
void paySalaries()
void printActions()


5. gym.Gym
Attributes:
name (String)
secretary (gym.management.Secretary)
Methods:
static gym.Gym getInstance() - Singleton pattern.
void setName(String name)
gym.management.Secretary getSecretary()
void setSecretary(gym.Person person, int salary)


6. gym.management.Sessions.Session
Attributes:
gym.management.Sessions.SessionType type
String dateTime
gym.ForumType forum
gym.management.Instructor instructor

Methods:
gym.management.Sessions.Session(gym.management.Sessions.SessionType type, String dateTime, gym.ForumType forum, gym.management.Instructor instructor)
gym.management.Sessions.SessionType getType()
void setType(gym.management.Sessions.SessionType type)
String getDateTime()
void setDateTime(String dateTime)
gym.ForumType getForum()
void setForum(gym.ForumType forum)
gym.management.Instructor getInstructor()
void setInstructor(gym.management.Instructor instructor)
String toString()


7. Enums
gym.Gender:
Male
Female

gym.management.Sessions.SessionType:
Pilates
ThaiBoxing
MachinePilates
Ninja

gym.ForumType:
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
