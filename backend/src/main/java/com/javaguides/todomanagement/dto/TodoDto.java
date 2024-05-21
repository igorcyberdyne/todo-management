package com.javaguides.todomanagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
