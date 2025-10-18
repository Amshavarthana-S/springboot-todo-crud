package dev.codeio.HelloWorld;

import dev.codeio.HelloWorld.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Get Todo by Path Variable
    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoByID(@PathVariable long id) {
        try {
            Todo createdTodo = todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
@GetMapping
ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<List<Todo>>(todoService.getTodos(),HttpStatus.OK);
}



    // Create Todo using POST (JSON body)
    @PostMapping("/create")
    ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        if(todo.getTitle()==null||todo.getTitle().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todo title cannot be empty");
        }
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }
    @PutMapping()
        ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo) {
return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }
@DeleteMapping("/{id}")
    void deleteTodoById(@PathVariable long id){
        todoService.deleteTodoById(id);
    }
    }
