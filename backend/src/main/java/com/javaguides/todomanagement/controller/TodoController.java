package com.javaguides.todomanagement.controller;

import com.javaguides.todomanagement.dto.TodoDto;
import com.javaguides.todomanagement.exception.ResourceNotFoundException;
import com.javaguides.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoService todoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodo() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id) throws ResourceNotFoundException {
        todoService.deleteTodo(id);

        return ResponseEntity.ok("Todo deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        return new ResponseEntity<>(todoService.addTodo(todoDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(todoService.updateTodo(id, todoDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(todoService.completeTodo(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(todoService.inCompleteTodo(id));
    }

}
