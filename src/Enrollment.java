public class Enrollment {
    private String studentId;
    private String courseId;
    private String status;
    private String grade;

    public Enrollment(String studentId, String courseId, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.grade = "N/A";
    }

    public String getStudentId() { 
        return studentId; 
    }
    public String getCourseId() { 
        return courseId; 
    }
    public String getStatus() { 
        return status; 
    }
    public String getGrade() { 
        return grade; 
    }

    public void assignGrade(String grade) {
        this.grade = grade;
    }
}