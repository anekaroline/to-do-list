package br.com.project.todolist.domain.dto;

import br.com.project.todolist.domain.enums.ToDoStatus;
import br.com.project.todolist.domain.models.ToDoListEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ToDoListRequest(
        @NotNull
        @NotBlank
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        @NotNull
        @NotBlank
        ToDoStatus status
) {

    public ToDoListEntity toDomain(){
        return new ToDoListEntity(null, title, description, startDate, endDate, status, null, null);
    }
}
