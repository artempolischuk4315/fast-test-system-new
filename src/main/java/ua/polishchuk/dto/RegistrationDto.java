package ua.polishchuk.dto;

import lombok.*;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegistrationDto {

    @Email
    private String email;
    private String password;
    private String confirmPassword;
}
