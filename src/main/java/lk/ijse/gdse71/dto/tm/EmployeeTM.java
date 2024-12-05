package lk.ijse.gdse71.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeTM {
    private String employeeId;
    private String name;
    private String phone;
    private String email;
    private double salary;
    private String position;
    private String department;
}
