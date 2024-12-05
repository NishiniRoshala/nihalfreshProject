package lk.ijse.gdse71.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionTM {
    private String userId;
    private String userName;
    private String passwordHash;
    private String role;
}

