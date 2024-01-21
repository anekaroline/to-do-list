package br.com.project.todolist.domain.dto;

import br.com.project.todolist.annotation.ISO8601Date;
import br.com.project.todolist.annotation.NoEmptyOrBlank;
import br.com.project.todolist.domain.enums.ToDoStatus;
import br.com.project.todolist.domain.models.ToDoListEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
//swagger name class

@Schema(name = "Schedule Response", description = "Schedule  Response DTO  Model  Definitions  for  Swagger UI  and  OpenAPI  Specifications  (OpenAPI  v3.0.0)  and  JSON  Schema  (JSON  Schema  v4.0.0)  ")
public record ToDoListResponse(
        @NotBlank
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ToDoList ID", example = "2c7a7f8d-9e5b-4e3b-8e7e-c3d9a7d1d9d9")
        UUID uuid,
        @NotNull
        @NotBlank
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ToDoList Title", example = "ToDoList Title")
        String title,
        @Schema(description = "ToDoList Description", example = "ToDoList Description")
        String description,
        @ISO8601Date
        @NoEmptyOrBlank
        @Schema(description = "ToDoList Start Date", example = "2022-01-01")
        LocalDate startDate,
        @ISO8601Date
        @NoEmptyOrBlank
        @Schema(description = "ToDoList End Date", example = "2022-01-01")
        LocalDate endDate,
        @NotNull
        @NotBlank
        @Schema(description = "ToDoList Status", example = "PENDING")
        ToDoStatus status
) {

    public static ToDoListResponse from(ToDoListEntity toDoList){
        return new ToDoListResponse(
                toDoList.getId(),
                toDoList.getTitle(),
                toDoList.getDescription(),
                toDoList.getStartDate(),
                toDoList.getEndDate(),
                toDoList.getStatus()
        );
    }
    public static List<ToDoListResponse> from(List<ToDoListEntity> entityList) {
        return entityList.stream()
                .map(ToDoListResponse::from)
                .collect(Collectors.toList());
    }

}
