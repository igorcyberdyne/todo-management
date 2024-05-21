package com.javaguides.todomanagement.service;


import com.javaguides.todomanagement.exception.ResourceNotFoundException;
import com.javaguides.todomanagement.dto.TodoDto;
import com.javaguides.todomanagement.entity.Todo;

import java.util.List;

public interface TodoService {
    public TodoDto addTodo(TodoDto todo);

    public TodoDto updateTodo(Long todoId, TodoDto todo) throws ResourceNotFoundException;
    public TodoDto completeTodo(Long todoId) throws ResourceNotFoundException;
    public TodoDto inCompleteTodo(Long todoId) throws ResourceNotFoundException;

    public void deleteTodo(Long todoId) throws ResourceNotFoundException;

    public TodoDto getTodoById(Long id) throws ResourceNotFoundException;

    public Todo retrieveTodoByIdIfExistElseThrownResourceNotFoundException(Long id) throws ResourceNotFoundException;

    public List<TodoDto> getAllTodos();
}
