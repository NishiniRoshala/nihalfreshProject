package lk.ijse.gdse71.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO {
    private String userId;         // Unique identifier for the user
    private String userName;       // User's name
    private String passwordHash;   // Hashed password for security
    private String role;           // Role of the user (e.g., Admin, User)
}
