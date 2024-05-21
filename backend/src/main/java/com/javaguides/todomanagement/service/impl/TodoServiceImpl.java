package com.javaguides.todomanagement.service.impl;

import com.javaguides.todomanagement.exception.ResourceNotFoundException;
import com.javaguides.todomanagement.dto.TodoDto;
import com.javaguides.todomanagement.entity.Todo;
import com.javaguides.todomanagement.repository.TodoRepository;
import com.javaguides.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;


    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        return modelMapper.map(todoRepository.save(modelMapper.map(todoDto, Todo.class)), TodoDto.class);
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todo) throws ResourceNotFoundException {
        Todo todoEntity = retrieveTodoByIdIfExistElseThrownResourceNotFoundException(todoId);
        todoEntity.setTitle(todo.getTitle());
        todoEntity.setDescription(todo.getDescription());
        todoEntity.setCompleted(todo.isCompleted());

        return modelMapper.map(todoRepository.save(todoEntity), TodoDto.class);
    }

    @Override
    public TodoDto completeTodo(Long todoId) throws ResourceNotFoundException {
        Todo todoEntity = retrieveTodoByIdIfExistElseThrownResourceNotFoundException(todoId);
        todoEntity.setCompleted(Boolean.TRUE);

        return modelMapper.map(todoRepository.save(todoEntity), TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long todoId) throws ResourceNotFoundException {
        Todo todoEntity = retrieveTodoByIdIfExistElseThrownResourceNotFoundException(todoId);
        todoEntity.setCompleted(Boolean.FALSE);

        return modelMapper.map(todoRepository.save(todoEntity), TodoDto.class);
    }

    @Override
    public void deleteTodo(Long todoId) throws ResourceNotFoundException {
        todoRepository.delete(retrieveTodoByIdIfExistElseThrownResourceNotFoundException(todoId));
    }

    @Override
    public TodoDto getTodoById(Long id) throws ResourceNotFoundException {
        return modelMapper.map(retrieveTodoByIdIfExistElseThrownResourceNotFoundException(id), TodoDto.class);
    }

    @Override
    public Todo retrieveTodoByIdIfExistElseThrownResourceNotFoundException(Long id) throws ResourceNotFoundException {
        return todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo resource not found with id : " + id));
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).toList();
    }
}
