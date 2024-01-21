package br.com.project.todolist.controller.api;

import br.com.project.todolist.domain.dto.ToDoListRequest;
import br.com.project.todolist.domain.dto.ToDoListResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/schedule")
public interface ToDoListAPI {
    @PostMapping
    ResponseEntity<ToDoListResponse> create(@Valid @RequestBody ToDoListRequest toDoListRequest);
    @PutMapping("/{uuid}")
    ResponseEntity<ToDoListResponse> update(@PathVariable String uuid, @Valid @RequestBody ToDoListRequest toDoListRequest);
    @DeleteMapping("/{uuid}")
    ResponseEntity<ToDoListResponse> delete(@PathVariable String uuid);
    @GetMapping("/{uuid}")
    ResponseEntity<ToDoListResponse> get(@PathVariable String uuid);
    @GetMapping
    ResponseEntity<List<ToDoListResponse>> getAll();
}
