package dev.codeio.HelloWorld.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


// spring JPA auto create table
@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
  private   Long id;
private String title;
private String Description;
private Boolean isCompleted;


}
