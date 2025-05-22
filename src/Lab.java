class Lab extends Course {
    private String lab;
    public Lab(){}
    public Lab(String courseId, String title, String description, String creditHours, String prerequisites, String lab,String studentId, String status) {
        super( courseId,  title,  description,  creditHours, prerequisites, studentId, status);
        this.lab = lab;
    }
    public String getLab(){
        return this.lab;
    }
}
