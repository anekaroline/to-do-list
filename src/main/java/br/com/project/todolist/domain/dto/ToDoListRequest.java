package br.com.project.todolist.domain.dto;

import br.com.project.todolist.annotation.ISO8601Date;
import br.com.project.todolist.annotation.NoEmptyOrBlank;
import br.com.project.todolist.domain.enums.ToDoStatus;
import br.com.project.todolist.domain.models.ToDoListEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@Tag(name = "Schedule Request" , description = "Schedule Request")
@Schema(name = "Schedule Request", description = "Schedule  Request DTO  Model  Definitions  for  Swagger UI  and  OpenAPI  Specifications  (OpenAPI  v3.0.0)  and  JSON  Schema  (JSON  Schema  v4.0.0)  ")
public record ToDoListRequest(
        @NotNull
        @NotBlank
        @Size(min = 1, max = 50)
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Title of the ToDoList", example = "My ToDoList")
        String title,
        @Schema(description = "Description of the ToDoList", example = "This is my ToDoList")
        @NoEmptyOrBlank
        String description,
        @NoEmptyOrBlank
        @ISO8601Date()
        @Schema(description = "Start date of the ToDoList", example = "2023-01-01")
        String startDate,
        @NoEmptyOrBlank
        @ISO8601Date()
        @Schema(description = "End date of the ToDoList", example = "2023-01-31")
        String endDate,
        @Schema(description = "Status of the ToDoList", example = "IN_PROGRESS", defaultValue = "PENDING")
        @NoEmptyOrBlank
        String status
) {
        public ToDoListEntity toDomain() {
            return getToDoListEntity(this.title, this.description, this.startDate, this.endDate, this.status);
        }

    static ToDoListEntity getToDoListEntity(String title, String description, String startDate, String endDate, String status) {
        return new ToDoListEntity(
                null,
                title,
                description,
                startDate != null ? LocalDate.parse(startDate) : null,
                endDate != null ? LocalDate.parse(endDate) : null,
                status != null ? ToDoStatus.toEnum(status) : ToDoStatus.PENDING,
                null,
                null);
    }

}
