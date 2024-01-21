package br.com.project.todolist.domain.dto;

import br.com.project.todolist.annotation.ISO8601Date;
import br.com.project.todolist.annotation.NoEmptyOrBlank;
import br.com.project.todolist.domain.enums.ToDoStatus;
import br.com.project.todolist.domain.models.ToDoListEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ToDoListRequest(
        @NotNull
        @NotBlank
        @Size(min = 1, max = 50)
        String title,
        String description,
        @NoEmptyOrBlank
        @ISO8601Date()
        String startDate,
        @NoEmptyOrBlank
        @ISO8601Date()
        String endDate,
        String status
) {

    public ToDoListEntity toDomain(){
        return new ToDoListEntity(
                null,
                this.title,
                this.description,
                this.startDate != null ? LocalDate.parse(this.startDate) : null,
                this.endDate != null ? LocalDate.parse(this.endDate) : null,
                this.status != null ?ToDoStatus.toEnum(this.status) : ToDoStatus.PENDING,
                null,
                null);
    }
}
