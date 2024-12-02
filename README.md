Person
Attributes:
name (String)
salary (int) - Represents salary or financial balance.
gender (Gender enum)
birthDate (String)

Methods:
Person(String name, int salary, Gender gender, String birthDate)
String getName()
void setName(String name)
  int getSalary()
void setSalary(int salary)
Gender getGender()
void setGender(Gender gender)
String getBirthDate()
void setBirthDate(String birthDate)
String toString()


2. Client (Inherits from Person)
Attributes:
notifications (List<String>) - List of messages received.

Methods:
List<String> getNotifications()
Other client-specific methods (e.g., session registration) may exist but are not explicitly detailed in the code.


3. Instructor (Inherits from Person)
Attributes:
qualifications (List<SessionType>) - Types of sessions the instructor is qualified to lead.

Methods:
List<SessionType> getQualifications()
void addQualification(SessionType type)


4. Secretary (Inherits from Person)
Attributes:
Likely manages client and instructor registrations, session scheduling, etc.

Methods:
Client registerClient(Person person)
void unregisterClient(Client client)
Instructor hireInstructor(Person person, int hourlyPay, List<SessionType> qualifications)
Session addSession(SessionType type, String dateTime, ForumType forum, Instructor instructor)
void registerClientToLesson(Client client, Session session)
void notify(Session session, String message)
void notify(String date, String message)
void notify(String message)
void paySalaries()
void printActions()


5. Gym
Attributes:
name (String)
secretary (Secretary)
Methods:
static Gym getInstance() - Singleton pattern.
void setName(String name)
Secretary getSecretary()
void setSecretary(Person person, int salary)


6. Session
Attributes:
SessionType type
String dateTime
ForumType forum
Instructor instructor

Methods:
Session(SessionType type, String dateTime, ForumType forum, Instructor instructor)
SessionType getType()
void setType(SessionType type)
String getDateTime()
void setDateTime(String dateTime)
ForumType getForum()
void setForum(ForumType forum)
Instructor getInstructor()
void setInstructor(Instructor instructor)
String toString()


7. Enums
Gender:
Male
Female

SessionType:
Pilates
ThaiBoxing
MachinePilates
Ninja

ForumType:
All
Male
Female
Seniors

8. Exceptions
Custom Exceptions:
InstructorNotQualifiedException - Thrown if an instructor cannot lead a session.
DuplicateClientException - Thrown if a client is already registered.
InvalidAgeException - Thrown if a person does not meet the age criteria.
ClientNotRegisteredException - Thrown if a client is not found in the system.
