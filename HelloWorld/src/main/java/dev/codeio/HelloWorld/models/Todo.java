package dev.codeio.HelloWorld.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;



// spring JPA auto create table
@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
  private   Long id;
@NotNull
@NotBlank
@Schema(name="title",example = "complete spring boot")
private String title;


private Boolean isCompleted;
@Email
String email;


}
