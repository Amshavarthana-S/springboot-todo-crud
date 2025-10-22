package dev.codeio.HelloWorld.controller;

import dev.codeio.HelloWorld.service.TodoService;
import dev.codeio.HelloWorld.models.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@Slf4j
public class TodoController {

    @Autowired
    private TodoService todoService;
@ApiResponses(value = {
        @ApiResponse(responseCode ="200",description = "Todo retrieved successfully"),
        @ApiResponse(responseCode = "404",description = "Todo not found")
})
    // Get Todo by Path Variable
    @GetMapping("/{id}")

    ResponseEntity<Todo> getTodoByID(@PathVariable long id) {
        try {
            Todo createdTodo = todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        } catch (RuntimeException exception) {
            log.info("Error");
            log.warn("");
            log.error("",exception);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
@GetMapping
ResponseEntity<List<Todo>> getTodosByID(){

        return new ResponseEntity<List<Todo>>(todoService.getTodos(),HttpStatus.OK);
}



    // Create Todo Using POST (JSON body)
    @PostMapping("/create")
    ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        if(todo.getTitle()==null||todo.getTitle().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todo title cannot be empty");
        }
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }
    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPages(page,size),HttpStatus.OK);
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
