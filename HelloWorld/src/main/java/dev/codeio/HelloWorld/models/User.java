package dev.codeio.HelloWorld.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="UserTable")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Email(message = "Email must be valid")
    @jakarta.validation.constraints.NotBlank(message = "Email is required")
    private String email;

    @jakarta.validation.constraints.NotBlank(message = "Password is required")
    private String password;
}
