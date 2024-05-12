//  @20220808038
//  @zeyneptanrivermis
//  @08.05.2024


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Assignment03Tests_20220808038 {

    @Test
    public void testAssignment02ClassName() {
        Assignment02_20220808038 assignment = new Assignment02_20220808038();
        assertEquals("Assignment02_20220808038", Assignment02_20220808038.class.getSimpleName());
    }

    @Test
    public void shouldNotCreateDepartmentWithInvalidID() {
        assertThrows(InvalidValueException.class, () -> {
            new Department("ABC", "Computer Engineering");
        });
    }

    @Test
    public void shouldNotCreateCourseWithDifferentDepartment() {
        Department cse = new Department("CSE", "Computer Engineering");
        Department math = new Department("MATH", "Mathematics");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, cse, 1);
        assertThrows(DepartmentMismatchException.class, () -> {
            new Course(math, 101, "Programming 1", "Introduction to Programming", 6, teacher);
        });
    }

    @Test
    public void departmentShouldNotSetChairDifferentDepartment() {
        Department cse = new Department("CSE", "Computer Engineering");
        Department math = new Department("MATH", "Mathematics");
        Teacher chair = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, cse, 1);
        assertThrows(DepartmentMismatchException.class, () -> {
            cse.setChair(chair);
        });
    }

    @Test
    public void DepartmentMismatchExceptionShouldHaveDepartmentTeacherConstructor() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, department, 1);
        DepartmentMismatchException exception = new DepartmentMismatchException(department, teacher);
        assertNotNull(exception);
    }

    @Test
    public void shouldGetGPA() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course c101 = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        Course c102 = new Course(cse, 102, "Programming 2", "Object Oriented Programming", 4, null);
        student.addCourse(c101, 80);
        student.addCourse(c102, 70);
        assertEquals(3.0, student.getGPA(), 0.001);
    }

    @Test
    public void shouldCourseResultThrowCourseNotFoundException() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course c101 = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        assertThrows(CourseNotFoundException.class, () -> {
            student.courseResult(c101);
        });
    }

    @Test
    public void shouldAddCourse() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course c101 = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        student.addCourse(c101, 80);
        assertEquals(1, student.courses.size());
        assertEquals(c101, student.courses.get(0));
        assertEquals(80, student.grades.get(0), 0.001);
    }

    @Test
    public void departmentShouldSetChair() {
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, cse, 1);
        cse.setChair(teacher);
        assertEquals(teacher, cse.getChair());
    }

    @Test
    public void shouldCreateCourse() {
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, cse, 1);
        Course course = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, teacher);
        assertNotNull(course);
        assertEquals(cse, course.getDepartment());
        assertEquals(101, course.getCourseNumber());
        assertEquals("Programming 1", course.getTitle());
        assertEquals("Introduction to Programming", course.getDescription());
        assertEquals(6, course.getAKTS());
        assertEquals(teacher, course.getTeacher());
    }

    @Test
    public void shouldNotCreateGradStudent() {
        Department cse = new Department("CSE", "Computer Engineering");
        assertThrows(InvalidRankException.class, () -> {
            GradStudent gradStudent = new GradStudent("Zeynep TANRIVERMIS",
                    "20220808038@ogr.akdeniz.edu.tr", 456L, cse, 5, "MDE");
        });
    }

    @Test
    public void shouldCreateStudent() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        assertNotNull(student);
        assertEquals("Zeynep TANRIVERMIS", student.getName());
        assertEquals("20220808038@ogr.akdeniz.edu.tr", student.getEmail());
        assertEquals(123L, student.getID());
        assertEquals(cse, student.getDepartment());
    }

    @Test
    public void teachershouldSetDepartmentAndChair() {
        Department cse = new Department("CSE", "Computer Engineering");
        Department math = new Department("MATH", "Mathematics");
        Teacher teacher = new Teacher("Berk Ercin", "berkercin@akdeniz.edu.tr", 123L, null, 1);
        cse.setChair(teacher);
        assertEquals(teacher, cse.getChair());
        teacher.setDepartment(math);
        assertEquals(null, cse.getChair());
    }

    @Test
    public void gradStudentCourseResultThrowException() {
        Department cse = new Department("CSE", "Computer Engineering");
        GradStudent gradStudent = new GradStudent("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 456L, cse, 5, "MDE");
        assertThrows(CourseNotFoundException.class, () -> gradStudent.courseResult(new Course(cse, 103, "Programming 3", "Advanced Programming", 6, null)));
    }

    @Test
    public void shouldGetCourseGPAPoints() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course course = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        student.addCourse(course, 88);
        double gpaPoints = student.courseGPAPoints(course);
        assertEquals(4.0, gpaPoints);
    }

    @Test
    public void courseShouldNotSetTeacherFromDifferentDpt() {
        Department cse = new Department("CSE", "Computer Engineering");
        Department math = new Department("MATH", "Mathematics");
        Teacher teacher = new Teacher("John Doe", "johndoe@example.com", 456L, math, 1);
        Course course = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        assertThrows(DepartmentMismatchException.class, () -> course.setTeacher(teacher));
    }

    @Test
    public void shouldReplaceCourse() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course course1 = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        Course course2 = new Course(cse, 102, "Programming 2", "Object Oriented Programming", 4, null);
        student.addCourse(course1, 80);
        student.addCourse(course2, 90);
        assertEquals(1, student.courses.size());
        assertEquals(course2, student.courses.get(0));
    }

    @Test
    public void shouldGetCourseGradeLetter() {
        Department cse = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep TANRIVERMIS", "20220808038@ogr.akdeniz.edu.tr", 123L, cse);
        Course course = new Course(cse, 101, "Programming 1", "Introduction to Programming", 6, null);
        student.addCourse(course, 75);
        assertEquals("BC", student.courseGradeLetter(course));
    }

    @Test
    public void shouldCreateTeacher() {
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET", "josephledet@akdeniz.edu.tr", 123L, cse, 1);
        assertEquals("Joseph LEDET", teacher.getName());
        assertEquals("josephledet@akdeniz.edu.tr", teacher.getEmail());
        assertEquals(123L, teacher.getID());
        assertEquals(cse, teacher.getDepartment());
        assertEquals(1, teacher.getRank());
    }

    @Test
    public void shouldCreateDepartment() {
        Department department = new Department("CSE", "Computer Engineering");
        assertEquals("CSE", department.getCode());
        assertEquals("Computer Engineering", department.getName());
    }

    @Test
    public void teachershouldSetDepartment() {
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Berk Ercin", "berkercin@akdeniz.edu.tr", 123L, null, 1);
        teacher.setDepartment(cse);
        assertEquals(cse, teacher.getDepartment());
    }

    @Test
    public void teacherShouldPromoteDemote() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Berk Ercin", "berkercin@akdeniz.edu.tr", 123L, department, 2);
        assertEquals(2, teacher.getRank());
        teacher.promote();
        assertEquals(3, teacher.getRank());
        teacher.demote();
        assertEquals(2, teacher.getRank());
    }

    @Test
    public void shouldGetGPAwithOneFailedCourse() {
        Department department = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep Tanrıvermiş", "20220808038@ogr.akdeniz.edu.tr", 123L, department);
        Course course = new Course(department, 101, "Course1", "Description1", 6, null);
        student.addCourse(course, 30);
        assertEquals(0.0, student.getGPA());
    }

    @Test
    public void shouldGetCourseResultFailed() {
        Department department = new Department("CSE", "Computer Engineering");
        Student student = new Student("Zeynep Tanrıvermiş", "20220808038@ogr.akdeniz.edu.tr", 123L, department);
        Course course = new Course(department, 101, "Course1", "Description1", 6, null);
        student.addCourse(course, 30);
        assertEquals("Failed", student.courseResult(course));
    }

    @Test
    public void DepartmentMismatchExceptionShouldHaveCourseTeacherConstructor() {
        Department department1 = new Department("CSE", "Computer Engineering");
        Department department2 = new Department("MATH", "Mathematics");
        Teacher teacher = new Teacher("John Doe", "john.doe@example.com", 456L, department1, 1);
        Course course = new Course(department2, 101, "Course1", "Description1", 6, teacher);
        assertThrows(DepartmentMismatchException.class, () -> course.setTeacher(teacher));
    }

    @Test
    public void courseMutators() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("John Doe", "john.doe@example.com", 456L, department, 1);
        Course course = new Course(department, 101, "Course1", "Description1", 6, teacher);
        Department newDepartment = new Department("ECON", "Economics");
        course.setDepartment(newDepartment);
        assertEquals(newDepartment, course.getDepartment());
        int newCourseNumber = 102;
        course.setCourseNumber(newCourseNumber);
        assertEquals(newCourseNumber, course.getCourseNumber());
        String newTitle = "New Title";
        course.setTitle(newTitle);
        assertEquals(newTitle, course.getTitle());
        String newDescription = "New Description";
        course.setDescription(newDescription);
        assertEquals(newDescription, course.getDescription());
        int newAKTS = 5;
        course.setAKTS(newAKTS);
        assertEquals(newAKTS, course.getAKTS());
    }

    @Test
    public void teacherShouldNotDemote() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("John Doe", "john.doe@example.com", 456L, department, 1);
        assertThrows(InvalidRankException.class, () -> {
            teacher.demote();
        });
        assertEquals(1, teacher.getRank());
    }

    @Test
    public void gradStudentShouldGetCourseGPAPoints() {
        Department department = new Department("CSE", "Computer Engineering");
        GradStudent gradStudent = new GradStudent("Jane Smith", "jane.smith@example.com", 789L, department, 1, "Thesis Topic");
        Course course = new Course(department, 101, "Course1", "Description1", 6, null);
        gradStudent.addCourse(course, 95);
        double gradePoints = gradStudent.courseGPAPoints(course);
        double expectedGradePoints = 4.0;
        assertEquals(expectedGradePoints, gradePoints);
    }

    @Test
    public void gradStudentShouldGetCourseResult() {
        Department department = new Department("CSE", "Computer Engineering");
        GradStudent gradStudent = new GradStudent("Jane Smith", "jane.smith@example.com", 789L, department, 1, "Thesis Topic");
        Course course = new Course(department, 101, "Course1", "Description1", 6, null);
        gradStudent.addCourse(course, 85);
        assertEquals("Passed", gradStudent.courseResult(course));
    }

    @Test
    public void teacherShouldNotPromote() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("John Doe", "john.doe@example.com", 456L, department, 5);
        assertThrows(InvalidRankException.class, () -> {
            teacher.promote();
        });
        assertEquals(5, teacher.getRank());
    }

    @Test
    public void gradStudentShouldGetCourseGradeLetter() {
        Department department = new Department("CSE", "Computer Engineering");
        GradStudent gradStudent = new GradStudent("Jane Smith", "jane.smith@example.com", 789L, department, 1, "Thesis Topic");
        Course course = new Course(department, 101, "Course1", "Description1", 6, null);
        gradStudent.addCourse(course, 92); // Grade of 92
        assertEquals("AA", gradStudent.courseGradeLetter(course));
    }

    @Test
    public void shouldThrowInvalidGradeException() {
        assertThrows(InvalidGradeException.class, () -> {
            Course course = new Course(null, 101, "Course1", "Description1", 6, null);
            course.setAKTS(-1);
        });
    }

    @Test
    public void departmentAccessors() {
        Department department = new Department("CSE", "Computer Engineering");
        assertEquals("CSE", department.getCode());
        assertEquals("Computer Engineering", department.getName());
        assertNotNull(department.getChair());
    }

    @Test
    public void departmentShouldNotSetInvalidID() {
        Department department = new Department("CSE", "Computer Engineering");
        assertThrows(InvalidValueException.class, () -> {
            department.setCode("ABCDE");
        });
        assertEquals("CSE", department.getCode());
    }

    @Test
    public void shouldCreateGradStudent() {
        Department department = new Department("CSE", "Computer Engineering");
        GradStudent gradStudent = new GradStudent("John Doe", "john.doe@example.com", 123L, department, 1, "Thesis Topic");
        assertNotNull(gradStudent);
        assertEquals("John Doe", gradStudent.getName());
        assertEquals("john.doe@example.com", gradStudent.getEmail());
        assertEquals(123L, gradStudent.getID());
        assertEquals(department, gradStudent.getDepartment());
        assertEquals(1, gradStudent.getRank());
        assertEquals("Thesis Topic", gradStudent.getThesisTopic());
    }

    @Test
    public void courseAccessors() {
        Department department = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Jane Smith", "jane.smith@example.com", 456L, department, 2);
        Course course = new Course(department, 101, "Course Name", "Course Description", 6, teacher);
        assertEquals(department, course.getDepartment());
        assertEquals(101, course.getCourseNumber());
        assertEquals("Course Name", course.getTitle());
        assertEquals("Course Description", course.getDescription());
        assertEquals(6, course.getAKTS());
        assertEquals(teacher, course.getTeacher());
    }

    @Test
    public void shouldGetCourseResultConditionallyPassed() {
        Department department = new Department("CSE", "Computer Engineering");
        Student student = new Student("Alice Johnson", "alice.johnson@example.com", 123L, department);
        Course course = new Course(department, 101, "Course Name", "Course Description", 6, null);
        student.addCourse(course, 53);
        assertEquals("Conditionally Passed", student.courseResult(course));
    }

    @Test
    public void departmentMutators() {
        Department department = new Department("CSE", "Computer Engineering");
        department.setCode("EEE");
        assertEquals("EEE", department.getCode());
        department.setName("Electrical Engineering");
        assertEquals("Electrical Engineering", department.getName());
        Teacher teacher = new Teacher("John Doe", "john.doe@example.com", 456L, department, 2);
        department.setChair(teacher);
        assertEquals(teacher, department.getChair());
    }
}