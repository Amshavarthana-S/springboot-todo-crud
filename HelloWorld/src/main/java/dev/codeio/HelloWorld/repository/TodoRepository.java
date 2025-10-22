package dev.codeio.HelloWorld.repository;


import dev.codeio.HelloWorld.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD -> create read update delete
public interface TodoRepository extends JpaRepository<Todo,Long> {


}
