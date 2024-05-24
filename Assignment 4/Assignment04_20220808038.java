//  @ ZEYNEP TANRIVERMİŞ
//  @ 20220808038
//  @ 21.05.2024

import java.util.*;

public class Assignment04_20220808038 {
    public static void main(String[] args) {
        //  testing the program
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr",
                123L, cse, 3);
        System.out.println(teacher);
        Student stu = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 546L, cse);
        Semester s1 = new Semester(1, 2020);
        Course c101 = new Course(cse, 101, "Programming 1",
                "Introduction", 6, teacher);
        Semester s2 = new Semester(2, 2021);
        Course c102 = new Course(cse, 102, "Programming 2",
                "Object Oriented Programming", 4, teacher);
        Course c204 = new Course(cse, 204, "Database Systems",
                "DBMS", 6, teacher);

        stu.addCourse(c101, s1, 80);
        stu.addCourse(c102, s2, 30);
        stu.addCourse(c204, s2, 70);
        System.out.println("List Grades for CSE101:\n" + stu.listGrades(c101));
        System.out.println("List Grades for Spring 2021:\n" + stu.listGrades(s2));
        System.out.println("Student Transcript:\n" + stu.transcript());

        GradStudent gs = new GradStudent("Assignment 4 GRADSTUDENT", "20220808038@ogr.akdeniz.edu.tr",789L, cse, 2, "MDE" );
        gs.addCourse(c101, s1, 85);
        gs.addCourse(c102, s1, 40);
        gs.setTeachingAssistant(c101);
        System.out.println("Teaching Assistant:\n" + gs.getTeachingAssistant());
        gs.setTeachingAssistant(c102);
        System.out.println("Teaching Assistant:\n" + gs.getTeachingAssistant());
    }
}

class Department {
    private String code; //  must be 3-4 ch
    private String name;
    private Teacher chair;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        if(code.length()==3 || code.length()==4){
            this.code = code;
        }
        else{
            throw new InvalidValueException("Department", "code",
                    code, "3 or 4");
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return chair;
    }
    public void setChair(Teacher chair) {
        if(chair == null || !chair.getDepartment().equals(this)) {
            throw new DepartmentMismatchException(this, chair);
        }
        this.chair = chair;
    }
}

class Course {
    private Department department;
    private Teacher teacher;
    private int courseNumber; //  must be 100-999, 5000-5999, 7000-7999
    private String title;
    private String description;
    private int AKTS; //  must be positive

    public Course(Department department, int courseNumber, String title,
                  String description, int AKTS, Teacher teacher) {
        if (department != null && teacher != null &&
                !department.equals(teacher.getDepartment())) {
            throw new DepartmentMismatchException(department, teacher);
        }
        this.department = department;
        this.courseNumber = courseNumber;
        this.title = title;
        this.description = description;
        this.AKTS = AKTS;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(int courseNumber) {
        if((100<=courseNumber && courseNumber<=999) ||
                (5000<=courseNumber && courseNumber<=5999) ||
                (7000<=courseNumber && courseNumber<=7999)){
            this.courseNumber = courseNumber;
        }
        else{
            throw new InvalidValueException("Course", "course number",
                    courseNumber, "100-999, 5000-5999, 7000-7999");
        }
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getAKTS() {
        return AKTS;
    }
    public void setAKTS(int AKTS) {
        if(AKTS>0){
            this.AKTS = AKTS;
        }
        else{
            throw new InvalidValueException("Course", "akts", AKTS,
                    "positive");
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        if (teacher != null && (department == null ||
                department.equals(teacher.getDepartment()))) {
            this.teacher = teacher;
        }
        else {
            throw new DepartmentMismatchException(department, teacher);
        }
    }

    //  returns the department code and course number with no space between
    public String courseCode(){
        return department.getCode() + courseNumber;
    }

    public String toString(){
        return courseCode() + " - " +
                title + "(" + AKTS + ")";

    }
}

abstract class Person {
    private Department department;
    private String name;
    private String email; //  must be username@uniname.domain
    private long ID;

    public Person(String name, String email, long ID, Department department){
        this.department = department;
        this.name = name;
        setEmail(email);
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if(email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            this.email = email;
        }
        else{
            throw new InvalidValueException("Person", "e-mail",
                    email, "username@uniname.domain");
        }
    }

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public String toString(){
        return name + " (" + ID + ")" + " - " + email;
    }
}

class Teacher extends Person {
    private int rank; //  must be between 1-4

    public Teacher(String name, String email, long ID,
                   Department department, int rank) {
        super(name, email, ID, department);
        setRank(rank);
    }

    public void setDepartmentCode(Department department) {
        if(department == null || department.getChair() == null ||
                department.getChair().equals(this)) {
            super.setDepartment(department);
        }
        else {
            throw new DepartmentMismatchException(department, this);
        }
    }

    @Override
    public void setDepartment(Department department) {
        if (department == null || department.getChair() == null ||
                department.getChair().equals(this)) {
            super.setDepartment(department);
        }
        else {
            throw new DepartmentMismatchException(department, this);
        }
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle(){
        if(rank==1) {
            return "Lecturer";
        }
        else if(rank==2){
            return "Adjunct Instructor";
        }
        else if(rank==3){
            return "Assistant Professor";
        }
        else if(rank==4){
            return "Associate Professor";
        }
        else{
            return "Professor";
        }
    }

    //  Increases the status of the teacher and make sure that rank remains a valid value
    public void promote(){
        if(rank < 5) {
            rank += 1;
        }
        else {
            throw new InvalidRankException(rank);
        }
    }

    //  Decreases the status of the teacher and Make sure that rank remains a valid value
    public void demote(){
        if(rank > 1) {
            rank -= 1;
        }
        else {
            throw new InvalidRankException(rank);
        }
    }

    public String toString(){
        return getTitle() + " " + super.toString();
    }
}

class Student extends Person {
    private Map<Course, Map<Semester, Double>> courseGrades;
    private Course Course;

    public Student(String name, String email, long ID, Department department) {
        super(name, email, ID, department);
        this.courseGrades = new HashMap<>();
    }

    @Override
    public void setDepartment(Department department) {
        super.setDepartment(department);
    }

    public void addCourse(Course course, Semester semester, double grade) {
        if (grade<0 || grade>100) {
            throw new InvalidGradeException(grade);
        }
        if (!courseGrades.containsKey(course)) {
            courseGrades.put(course, new HashMap<>());
        }
        courseGrades.get(course).put(semester, grade);
    }

    public int getAKTS() {
        int totalAKTS = 0;
        for (Course course : courseGrades.keySet()) {
            totalAKTS += course.getAKTS();
        }
        return totalAKTS;
    }

    public int getAttemptedAKTS() {
        int totalAttemptedAKTS = 0;
        for (Course course : courseGrades.keySet()) {
            totalAttemptedAKTS += course.getAKTS() * courseGrades.get(course).size();
        }
        return totalAttemptedAKTS;
    }

    public double courseGPAPoints(Course course) {
        if (!courseGrades.containsKey(course)) {
            return 0.0;
        }
        double totalPoints = 0.0;
        for (Double grade : courseGrades.get(course).values()) {
            totalPoints += grade * course.getAKTS();
        }
        return totalPoints;
    }

    public String courseGradeLetter(Course course) {
        if (!courseGrades.containsKey(course)) {
            throw new CourseNotFoundException(null, course);
        }

        double totalGradePoints = courseGPAPoints(course);
        int totalAKTS = getAttemptedAKTS();
        double grade = totalGradePoints / totalAKTS;

        if (grade >= 88) {return "AA";}
        else if (grade >= 81) {return "AB";}
        else if (grade >= 74) {return "BB";}
        else if (grade >= 67) {return "BC";}
        else if (grade >= 60) {return "CC";}
        else if (grade >= 53) {return "DC";}
        else if (grade >= 46) {return "DD";}
        else if (grade >= 35) {return "FD";}
        else  {return "FF";}
    }

    public String courseResult(Course course) {
        if (!courseGrades.containsKey(course)) {
            throw new CourseNotFoundException(null, course);
        }

        double totalGradePoints = courseGPAPoints(course);
        int totalAKTS = getAttemptedAKTS();
        double grade = totalGradePoints / totalAKTS;

        if (grade >= 88) {return "Passed";}
        else if (grade >= 81) {return "Passed";}
        else if (grade >= 74) {return "Passed";}
        else if (grade >= 67) {return "Passed";}
        else if (grade >= 60) {return "Passed";}
        else if (grade >= 53) {return "Conditionally Passed";}
        else if (grade >= 46) {return "Conditionally Passed";}
        else if (grade >= 35) {return "Failed";}
        else {return "Failed";}
    }

    public String listGrades(Semester semester) {
        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (Course course : courseGrades.keySet()) {
            Map<Semester, Double> grades = courseGrades.get(course);
            if (grades.containsKey(semester)) {
                found = true;
                result.append(course.getTitle()).append(": ").append(grades.get(semester)).append("\n");
            }
        }

        if (!found) {
            throw new SemesterNotFoundException(null, semester);
        }

        return result.toString();
    }

    public String listGrades(Course course) {
        if (!courseGrades.containsKey(course)) {
            throw new CourseNotFoundException(null, course);
        }

        StringBuilder result = new StringBuilder();
        for (Semester semester : courseGrades.get(course).keySet()) {
            result.append(semester.toString()).append(": ").append(courseGrades.get(course).get(semester)).append("\n");
        }
        return result.toString();
    }

    public String transcript() {
        StringBuilder result = new StringBuilder();
        result.append("Transcript for: ").append(getName()).append("\n\n");

        for (Course course : courseGrades.keySet()) {
            result.append(course.getTitle()).append("\n");
            for (Map.Entry<Semester, Double> entry : courseGrades.get(course).entrySet()) {
                Semester semester = entry.getKey();
                double grade = entry.getValue();
                result.append("  ").append(semester.toString()).append(": ").append(grade).append("\n");
            }
        }

        result.append("\nGPA: ").append(courseGPAPoints((Course) courseGrades.get(Course))).append("\n");
        return result.toString();
    }

    public String toString(){
        return super.toString() + " - GPA:" + courseGPAPoints((Course) courseGrades.get(Course));
    }
}

class GradStudent extends Student {
    private int rank; //  must be 1-2-3
    private String thesisTopic;
    private Course teachingAssistantCourse;
    private Map<Course, Map<Semester, Double>> courseGrades;

    public GradStudent(String name, String email, long ID, Department department,
                       int rank, String thesisTopic) {
        super(name, email, ID, department);
        this.rank = rank;
        setThesisTopic(thesisTopic);
        this.teachingAssistantCourse = null;
        this.courseGrades = new HashMap<>();
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        if(1<=rank && rank<=5){
            this.rank = rank;
        }
        else {
            throw new InvalidRankException(rank);
        }
    }

    public String getThesisTopic() {
        return thesisTopic;
    }
    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = "Thesis Topic: " + thesisTopic;
    }

    public String getLevel(int rank){
        if(rank==1){
            return "Master's Student";
        }
        else if(rank==2){
            return "Doctoral Student";
        }
        else{
            return "Doctoral Candidate";
        }
    }

    public void setTeachingAssistant(Course course) {
        if (this.teachingAssistantCourse != null) {
            throw new IllegalStateException("Already a teaching assistant for a course");
        }
        double highestGrade = courseGPAPoints(course);
        if (highestGrade >= 80) {
            this.teachingAssistantCourse = course;
        } else {
            throw new CourseNotFoundException(this, course);
        }
    }

    public Course getTeachingAssistant() {
        return this.teachingAssistantCourse;
    }

    public double courseGPAPoints(Course course) {
        if (!courseGrades.containsKey(course)) {
            return 0.0;
        }
        double totalPoints = 0.0;
        for (Double grade : courseGrades.get(course).values()) {
            totalPoints += grade * course.getAKTS();
        }
        return totalPoints;
    }

    public String courseGradeLetter(Course course) {
        if (!courseGrades.containsKey(course)) {
            throw new CourseNotFoundException(null, course);
        }

        double totalGradePoints = courseGPAPoints(course);
        int totalAKTS = getAttemptedAKTS();
        double grade = totalGradePoints / totalAKTS;

        if (grade >= 90) {
            return "AA";
        } else if (grade >= 85) {
            return "AB";
        } else if (grade >= 80) {
            return "BB";
        } else if (grade >= 75) {
            return "BC";
        } else if (grade >= 70) {
            return "CC";
        } else {
            return "FF";
        }
    }

    public String courseResult(Course course) {
        if (!courseGrades.containsKey(course)) {
            throw new CourseNotFoundException(null, course);
        }

        double totalGradePoints = courseGPAPoints(course);
        int totalAKTS = getAttemptedAKTS();
        double grade = totalGradePoints / totalAKTS;

        if (grade >= 90) {return "Passed";}
        else if (grade >= 88) {return "Passed";}
        else if (grade >= 80) {return "Passed";}
        else if (grade >= 75) {return "Passed";}
        else if (grade >= 70) {return "Passed";}
        else {return "Failed";}
    }

    public String toString(){
        return super.toString();
    }
}

final class Semester {

    private final int SEASON;
    private final String[] SEASONS = {"Fall", "Spring", "Summer"};
    private final int YEAR;

    public Semester(int SEASON, int YEAR) {
        if(SEASON<1 || SEASON>3) {
            throw new InvalidValueException("Semester", "year", YEAR, "1-2-3");
        }
        this.SEASON = SEASON;
        this.YEAR = YEAR;
    }

    public String getSEASON() {
        if (SEASON==1) {
            return "Fall";
        }
        else if (SEASON==2) {
            return "Spring";
        }
        else {
            return "Summer";
        }
    }

    public int getYEAR() {
        return YEAR;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "SEASON=" + SEASON +
                ", YEAR=" + YEAR +
                '}';
    }
}

class CourseNotFoundException extends RuntimeException {
    private Student student;
    private Course course;

    public CourseNotFoundException(Student student, Course course){
        this.student = student;
        this.course = course;
    }

    public String toString(){
        return "CourseNotFoundExcepiton: " + student.getID() +" has not yet taken "
                + course.courseCode();
    }
}

class DepartmentMismatchException extends RuntimeException {
    private Department department;
    private Teacher person;
    private Course course;

    public DepartmentMismatchException(Teacher person, Course course){
        this.person = person;
        this.course = course;
        this.department = null;
    }

    public DepartmentMismatchException(Department department, Teacher person){
        this.department = department;
        this.person = person;
        this.course = null;
    }

    public String toString(){
        if(course == null){
            return "DepartmentMismatchException: " + person.getName() + " (" +
                    person.getID() + ") can not be chair of " +
                    department.getCode() + " because he/she is currently assigned to "
                    + person.getID();
        }
        return "DepartmentMismatchException: " + person.getName() +
                " (" + person.getID() + ") cannot teach " + course.courseCode() +
                " because he/she is currently assigned to " + person.getID();
    }
}

class InvalidGradeException extends RuntimeException {
    private double grade;

    public InvalidGradeException(double grade){
        this.grade = grade;
    }

    public String toString(){
        return "InvalidGradeException: " +grade;
    }
}

class InvalidRankException extends RuntimeException {
    private int rank;

    public InvalidRankException(int rank){
        this.rank = rank;
    }

    public String toString(){
        return "InvalidRankException: " + rank;
    }
}

class InvalidValueException extends RuntimeException {
    private String className;
    private String attributeName;
    private Object invalidValue;
    private String validValues;

    public InvalidValueException(String className, String attributeName,
                                 Object invalidValue, String validValues) {
        this.className = className;
        this.attributeName = attributeName;
        this.invalidValue = invalidValue;
        this.validValues = validValues;
    }

    public String toString() {
        return "InvalidValueException: " + className + " and " +
                attributeName + ": " + invalidValue +
                " is not a valid value. Valid values are " + validValues;
    }
}

class SemesterNotFoundException extends RuntimeException {
    private Student student;
    private  Semester semester;

    public SemesterNotFoundException(Student student, Semester semester) {
        this.student = student;
        this.semester =semester;
    }
    @Override
    public String toString() {
        return "SemesterNotFoundException: " +
                student.getID() +
                " has not taken any courses in " + semester;
    }
}

