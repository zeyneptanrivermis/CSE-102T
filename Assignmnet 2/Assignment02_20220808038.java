//  ZEYNEP TANRIVERMIS
//  20220808038
//  27.03.2024

import java.util.ArrayList;

public class Assignment02_20220808038 {
    public static void main(String[] args) {
        //  testing the program
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher t = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr",
         123L, cse, 1);
        Course c101 = new Course(cse, 101, "Programming 1",
         "Introduction to Programming", 6, t);
        Course c102 = new Course(cse, 102, "Programming 2",
         "Object Oriented Programming", 4, t);
        Student s = new Student("Zeynep TANRIVERMIS",
         "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        s.addCourse(c101, 80);
        s.addCourse(c102, 30);
        System.out.println(s.getAKTS());
        System.out.println(s.getAttemptedAKTS());
        System.out.println(s.getGPA());
        System.out.println(s);
        s = new GradStudent("Zeynep TANRIVERMIS",
         "20220808038@ogr.akdeniz.edu.tr", 456L, cse, 5, "MDE");
        s.addCourse(c101, 80);
        s.addCourse(c102, 70);
        System.out.println(s.getAKTS());
        System.out.println(s.getAttemptedAKTS());
        System.out.println(s.getGPA());
        System.out.println(s);
        
        cse.setChair(t);
        Department math = new Department("MATH", "Mathematics");
        System.out.println(cse.getCode() + " Chair = " + cse.getChair());
        t.setDepartment(math);
        System.out.println(cse.getCode() + " Chair = " + cse.getChair());
        t.setDepartment(cse);
        System.out.println(cse.getCode() + " Chair = " + cse.getChair()); 
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
            return "Teaching Assistant";
        }
        else if(rank==2){
            return "Lecturer";
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
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Double> grades = new ArrayList<>();

    public Student(String name, String email, long ID, Department department) {
        super(name, email, ID, department);
    }

    @Override
    public void setDepartment(Department department) {
        super.setDepartment(department);
    }

    public int getAKTS() {
        int totalAKTS = 0;
        for(Course course : courses) {
            int index = courses.indexOf(course); 
            if (grades.get(index) >= 60) { 
                totalAKTS += course.getAKTS();
            }
        }
        return totalAKTS;
    }

    public int getAttemptedAKTS() {
        int attemptedAKTS = 0;
        for (Course course : courses) {
            attemptedAKTS += course.getAKTS();
        }
        return attemptedAKTS;  
    }

    public void addCourse(Course course, double grade) {
        int index = courses.indexOf(course);
        if (index != -1) {
            grades.set(index, grade);
        } else {
            courses.add(course);
            grades.add(grade);
        }
    }

    public double courseGPAPoints(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
            if (grade >= 88) {return 4.0;}    
            else if (grade >= 81) {return 3.5;}   
            else if (grade >= 74) {return 3.0;} 
            else if (grade >= 67) {return 2.5;}
            else if (grade >= 60) {return 2.0;}
            else if (grade >= 53) {return 1.5;} 
            else if (grade >= 46) {return 1.0;}
            else if (grade >= 35) {return 0.5;}
            else {return 0.0;}
        }
        else{
            throw new CourseNotFoundException(null, course);
        }
    }

    public String courseGradeLetter(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
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
        else{
            throw new CourseNotFoundException(null, course);
        }  
    }

    public String courseResult(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
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
        else{
            throw new CourseNotFoundException(null, course);
        }  
    }

    public double getGPA() {
        double totalPoints = 0.0;
        int totalAKTS = 0;
        for (Course course : courses) {
            double gradePoints = courseGPAPoints(course); 
            totalPoints += gradePoints * course.getAKTS(); 
            totalAKTS += course.getAKTS(); 
        }
        if (totalAKTS > 0) {
            return totalPoints / totalAKTS; 
        } 
        else {
            return 0.0; 
        } 
    }

    public String toString(){
        return super.toString() + " - GPA:" + getGPA();
    }
}

class GradStudent extends Student {
    private int rank; //  must be 1-2-3
    private String thesisTopic;

    public GradStudent(String name, String email, long ID, Department department,
     int rank, String thesisTopic) {
        super(name, email, ID, department);
        this.rank = rank;
        setThesisTopic(thesisTopic);
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

    public double courseGPAPoints(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
            if (grade >= 90) {return 4.0;}    
            else if (grade >= 85) {return 3.5;}   
            else if (grade >= 80) {return 3.0;} 
            else if (grade >= 75) {return 2.5;}
            else if (grade >= 70) {return 2.0;}
            else {return 0.0;}
        }
        else{
            throw new CourseNotFoundException(null, course);
        }
    }

    public String courseGradeLetter(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
            if (grade >= 90) {return "AA";}    
            else if (grade >= 85) {return "AB";}   
            else if (grade >= 80) {return "BB";} 
            else if (grade >= 75) {return "BC";}
            else if (grade >= 70) {return "CC";}
            else  {return "FF";}
        }
        else{
            throw new CourseNotFoundException(null, course);
        }  
    }

    public String courseResult(Course course) {
        int index = courses.indexOf(course);
        if (index != -1) {
            double grade = grades.get(index);
            if (grade >= 90) {return "Passed";}    
            else if (grade >= 88) {return "Passed";}   
            else if (grade >= 80) {return "Passed";} 
            else if (grade >= 75) {return "Passed";}
            else if (grade >= 70) {return "Passed";}
            else {return "Failed";}
        }
        else{
            throw new CourseNotFoundException(null, course);
        }  
    }

    public String toString(){
        return super.toString();
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


