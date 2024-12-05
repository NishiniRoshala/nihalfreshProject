package lk.ijse.gdse71.dto;



public class EmployeeDTO {
    private String employeeId;
    private String name;
    private String phone;
    private String email;
    private double salary;
    private String position;
    private String department;

    // Constructor
    public EmployeeDTO(String employeeId, String name, String phone, String email, double salary, String position, String department) {
        this.employeeId = employeeId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.position = position;
        this.department = department;
    }

    // Getters and setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

