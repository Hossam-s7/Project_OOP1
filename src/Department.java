public class Department {
    private String departmentId;
    private String name;

    public Department(String departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public void displayInfo() {
        System.out.println("\nDepartment Information:");
        System.out.println("ID: " + departmentId);
        System.out.println("Name: " + name);
    }

    // Getters
    public String getDepartmentId() { return departmentId; }
    public String getName() { return name; }
}
