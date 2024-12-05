package lk.ijse.gdse71.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
    private String name;
    private String nic;
    private String email;
    private String phone;
}
