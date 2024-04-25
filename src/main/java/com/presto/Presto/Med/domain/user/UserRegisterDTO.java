package com.presto.Presto.Med.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
    private Long id;

    @NotBlank(message = "name must not be null.")
    private String name;

    @NotBlank(message = "last name must not be null.")
    private String last_name;

    @NotBlank(message = "email must not be null.")
    @Email(message = "O email deve estar em um formato válido")
    private String email;

    @NotBlank(message = "password must not be null.")
    private String password;

    @NotBlank(message = "phone must not be null.")
    @Pattern(regexp="\\d{9,11}", message="O telefone deve conter entre 9 e 11 dígitos")
    private String phone;
}
