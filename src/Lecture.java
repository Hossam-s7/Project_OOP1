class Lecture extends Course {
    private  String lectureRoom;
    public Lecture(){}   
    public Lecture(String courseId, String title, String description, String creditHours, String prerequisites, String lectureRoom,String studentId, String status) {
        super( courseId,  title,  description,  creditHours, prerequisites, studentId, status);
        this.lectureRoom = lectureRoom;
    }
    public String getLectureRoom(){
        return this.lectureRoom;
    }
}