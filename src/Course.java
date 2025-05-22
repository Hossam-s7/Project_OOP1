public class Course {
    private String courseId;
    private String title;
    private String description;
    private String creditHours;
    private String prerequisites;
    Enrollment enrollment;
     public Course(){}
    public Course(String courseId, String title, String description, String creditHours, String prerequisites,String studentId, String status) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
        this.enrollment=new Enrollment(studentId, courseId, status);
    }

    public void displayCourseInfo() {
        System.out.println("\nCourse Information:");
        System.out.println("ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Prerequisites: " + prerequisites);
    }

}
