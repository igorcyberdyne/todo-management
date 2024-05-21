package com.javaguides.todomanagement.repository;

import com.javaguides.todomanagement.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
