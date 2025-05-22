import java.io.*;
import java.nio.file.*;


public class FileManager {
    public static void saveToFile(String filename, String data) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(data + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    public static void saveGradeToFile(String studentId, String courseId, String grade) {
        String gradeData = studentId + "," + courseId + "," + grade;
        saveToFile("grades.txt", gradeData);
    }
    public static void displayFileContents(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void updateGradeInFile(String filename, String studentId, 
                                       String courseId, String newGrade) {
        try {
            File inputFile = new File(filename);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(studentId) && parts[1].equals(courseId)) {
                    line = studentId + "," + courseId + "," + parts[2] + "," + newGrade;
                }
                writer.write(line + "\n");
            }
            writer.close();
            reader.close();

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            System.out.println("Error updating grade: " + e.getMessage());
        }
    }

    public static void displayCourseEnrollments(String filename, String courseId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(courseId)) {
                    System.out.println(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading enrollments: " + e.getMessage());
        }
    }

    public static void copyFile(String sourceFile, String destFile) {
        try {
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Error backing up file: " + e.getMessage());
        }
    }
    public static void addCourseMaterial(String courseId, String title, String content) {
        String materialData = courseId + "," + title + "," + content;
        saveToFile("courses.txt", materialData);
    }

    public static void updateCourseMaterial(String courseId, String oldTitle, 
                                          String newTitle, String newContent) {
        try {
            File inputFile = new File("courses.txt");
            File tempFile = new File("temp_course.txt");

            if (!inputFile.exists()) {
                System.out.println("Error: Course file does not exist.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(courseId) && parts[1].equals(oldTitle)) {
                    line = courseId + "," + newTitle + "," + newContent;
                    found = true;
                }
                writer.write(line + "\n");
            }

            writer.close();
            reader.close();
if (found) {
                Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Course material updated successfully.");
            } else {
                System.out.println("Material not found. No changes made.");
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error updating course material: " + e.getMessage());
        }
    }

    public static void removeCourseMaterial(String courseId, String title) {
        try {
            File inputFile = new File("courses.txt");
            File tempFile = new File("temp_course.txt");

            if (!inputFile.exists()) {
                System.out.println("Error: Course file does not exist.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(courseId) && parts[1].equals(title)) {
                    found = true;
                    continue; 
                }
                writer.write(line + "\n");
            }

            writer.close();
            reader.close();
            
            if (found) {
                Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Course material removed successfully.");
            } else {
                System.out.println("Material not found. No changes made.");
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error removing course material: " + e.getMessage());
        }
    }
    
    
    public static void displayCourseMaterials(String courseId) {
        try {
            File file = new File("courses.txt");
            if (!file.exists()) {
                System.out.println("No course materials found.");
                return;
            }
                
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean found = false;
            System.out.println("\nCourse Materials for " + courseId + ":");
            System.out.println("-----------------------------");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(courseId)) {
                    System.out.println("Title: " + parts[1]);
                    System.out.println("Content: " + parts[2]);
                    System.out.println("-----------------------------");
                    Lecture l=new Lecture();
                    System.out.println(l.getLectureRoom());
                    Lab la=new Lab();
                    System.out.println(la.getLab());
                    found = true;
                }
                // Course c=new Course();
                // c.getectureRoom;

            }
            if (!found) {
                System.out.println("No materials found for this course.");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading course materials: " + e.getMessage());
        }
    }





































}















