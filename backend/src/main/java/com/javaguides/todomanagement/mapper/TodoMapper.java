package com.javaguides.todomanagement.mapper;

import com.javaguides.todomanagement.dto.TodoDto;
import com.javaguides.todomanagement.entity.Todo;

public class TodoMapper {

    public static TodoDto mapToTodoDto(Todo todo) {
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isCompleted());
    }

    public static Todo mapToTodo(TodoDto todo) {
        return new Todo(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isCompleted());
    }
}
