//  ZEYNEP TANRIVERMİŞ
//  20220808038
//  10.03.2024

public class Assignment01_20220808038 {
    public static void main(String[] args) {
        //  testing the program
        Course c = new Course("CSE", 102, "Programming 2", "Introduction to OOP", 6);
        System.out.println(c.courseCode(null, null) +  " - " + c.getTitle());
        System.out.println(c);

        Teacher t = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, "CSE", 1);
        System.out.println(t);

        Student s = new Student("Zeynep TANRIVERMİŞ", "20220808038@ogr.akdeniz.edu.tr", 456L, "CSE", 0);
        System.out.println(s);

        s.passCourse(c);
        System.out.println(s.getAkts());

        System.out.println("----------");

        Course course = new Course("CSE", 101, "Computer Programming", "Introduction to Programming", 6);
        Student student = new Student("Zeynep", "zeynep@ogr.akdeniz.edu.tr", 123L, "CSE", 0);
        student.passCourse(course);
        course.setCourseNumber(course.getCourseNumber() + 10);
        System.out.println(student);
        System.out.println(course);
        course = new Course("CSE", 102, "Computer Programming 2", "Introduction to OOP", 4);
        student.passCourse(course);
        course.setCourseNumber(course.getCourseNumber() - 10);
        System.out.println(course);
        System.out.println(student);
    } 
}

class Course{
    //  data fields
    private String departmentCode; //  must be 3-4 ch
    private int courseNumber; //  must be 100-999, 5000-5999, 7000-7999
    private String title;
    private String description; 
    private int akts; //  must be positive

    //  constructor
    public Course(String departmentCode, int courseNumber, String title,
     String description, int akts){
        this.departmentCode = departmentCode;
        this.courseNumber = courseNumber;
        this.title = title;
        this.description = description;
        this.akts = akts;
    }

    //  getter and setter methods
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        if(departmentCode.length()==3 && departmentCode.length()==4){
            this.departmentCode = departmentCode;
        }
        else{
            throw new IllegalArgumentException("Deparment code must be 3 or 4 " +
             "characters long!");
        }
    }

    public int getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(int courseNumber) {
        if((100<=courseNumber && courseNumber<=999) &&
         (5000<=courseNumber && courseNumber<=5999) &&
          (7000<=courseNumber && courseNumber<=7999)){
            this.courseNumber = courseNumber;
        }
        else{
            throw new IllegalArgumentException("Course number must be in " + 
             "the range 100-999 or 5000-5999 or 7000-7999!");
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

    public int getAkts() {
        return akts;
    }
    public void setAkts(int akts) {
        if(akts>0){
            this.akts = akts;
        }
        else{
            throw new IllegalArgumentException("AKTS must be positive!");
        }
    }

    //  returns the department code and course number with no space between
    public String courseCode(String departmentCode, String courseNumber){
        return departmentCode.toUpperCase() + courseNumber;
    }

    public String toString(){
        return courseCode(departmentCode, departmentCode) + " - " + 
         title + " - " + "(" + akts + ")";

    }
} 

class Person{
    //  data fields
    private String name;
    private String eMail; //  must be username@uniname.domain
    private long id;
    private String departmentCode; //  must be 3-4 ch

    //  constructor
    public Person(String name, String eMail, long id, String departmentCode){
        this.name = name;
        seteMail(eMail);
        this.id = id;
        setDepartmentCode(departmentCode);
    }

    //  getter and setter methods
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        if(eMail.matches(".+@\\..+")){
            this.eMail = eMail;
        }
        else{
            throw new IllegalArgumentException("Email must be of the form " +
             "{username}@{university name}.{domain}");
        }
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        if(departmentCode.length()==3 && departmentCode.length()==4){
            this.departmentCode = departmentCode;
        }
        else{
            throw new IllegalArgumentException("Deparment code must be 3 or 4 " + 
             "characters long!");        
        }
    }

    public String toString(){
        return name + " (" + id + ")" + " - " + eMail;
    }
}

class Teacher extends Person{
    //  data fields
    private int rank; //  must be between 1-4

    //  constructor
    public Teacher(String name, String eMail, long id,
     String departmentCode, int rank) {
        super(name, eMail, id, departmentCode); 
    }
    
    //  getter and setter methods
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        if(1<=rank && rank<=4){
            this.rank = rank;
        }
        else{
            throw new IllegalArgumentException("Rank must be between " +
             "1 and 4!");
        }
    }

    public String getTitle(int rank){
        if(rank==1){
            return "Lecturer";
        }
        else if(rank==2){
            return "Assistant Professor";
        }
        else if(rank==3){
            return "Associate Professor";
        }
        else{
            return "Professor";
        }
    }

    //  Increases the status of the teacher and make sure that rank remains a valid value
    public void promote(int rank){
        if(1<=rank && rank<=3){
            rank += 1;
        }
    }

    //  Decreases the status of the teacher and Make sure that rank remains a valid value
    public void demote(int rank){
        if(2<=rank && rank<=4){
            rank -= 1;
        }
    }

    public String toString(){
        return getTitle(rank) + " " + super.toString();
    }
}

class Student extends Person{
    //  data fields
    private int akts;

    //  constructor
    public Student(String name, String eMail, long id, String departmentCode, int akts) {
        super(name, eMail, id, departmentCode);
        this.akts = 0;
    }

    //  getter and setter methos
    public int getAkts() {
        return akts;
    }

    public void passCourse(Course course){
        akts += akts;
    }

    public String toString(){
        return super.toString();
    }

}

class GradStudent extends Student{
    //  data fields
    private int rank; //  must be 1-2-3
    private String thesisTopic;

    //  constructor
    public GradStudent(String name, String eMail, long id, 
    String departmentCode, int rank, String thesisTopic) {
        super(name, eMail, id, departmentCode, rank);
    }

    //  getter and setter methıds
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        if(1<=rank && rank<=3){
            this.rank = rank;
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

    public String toString(){
        return super.toString();
    }
}