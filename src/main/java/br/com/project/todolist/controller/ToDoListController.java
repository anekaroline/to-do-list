package br.com.project.todolist.controller;

import br.com.project.todolist.controller.api.ToDoListAPI;
import br.com.project.todolist.domain.dto.ToDoListRequest;
import br.com.project.todolist.domain.dto.ToDoListResponse;
import br.com.project.todolist.service.ToDoListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Schedule", description = "API agenda or To-Do List")
@RestController
public class ToDoListController implements ToDoListAPI {

    private final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @Override
    public ResponseEntity<ToDoListResponse> create(ToDoListRequest toDoListRequest) {
        final var result = toDoListService.save(toDoListRequest.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(ToDoListResponse.from(result));
    }

    @Override
    public ResponseEntity<ToDoListResponse> update(String uuid, ToDoListRequest toDoListRequest) {
        final var result = toDoListService.update(uuid, toDoListRequest.toDomain());
        return ResponseEntity.ok().body(ToDoListResponse.from(result));
    }

    @Override
    public ResponseEntity<ToDoListResponse> delete(String uuid) {
        toDoListService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ToDoListResponse> get(String uuid) {
        final var result = toDoListService.findById(uuid);
        return ResponseEntity.ok().body(ToDoListResponse.from(result));
    }

    @Override
    public ResponseEntity<List<ToDoListResponse>> getAll() {
        final var result = toDoListService.findAll();
        List<ToDoListResponse> responseList = ToDoListResponse.from(result);
        return ResponseEntity.ok().body(responseList);
    }

}
