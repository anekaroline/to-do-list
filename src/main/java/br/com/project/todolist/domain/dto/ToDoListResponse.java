package br.com.project.todolist.domain.dto;

import br.com.project.todolist.domain.enums.ToDoStatus;
import br.com.project.todolist.domain.models.ToDoListEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record ToDoListResponse(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ToDoStatus status
) {

    public static ToDoListResponse from(ToDoListEntity toDoList){
        return new ToDoListResponse(
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
