package br.com.project.todolist.controller.api;

import br.com.project.todolist.domain.dto.ToDoListRequest;
import br.com.project.todolist.domain.dto.ToDoListRequestUpdate;
import br.com.project.todolist.domain.dto.ToDoListResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/v1/schedule")
public interface ToDoListAPI {

    @Operation(
            summary = "Create a new appointment",
            description = "Create a new appointment and save it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ToDoListRequest.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    ResponseEntity<ToDoListResponse> create(@Valid @RequestBody ToDoListRequest toDoListRequest);

    @Operation(
            summary = "Update an appointment",
            description = "Update an appointment and save it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ToDoListRequestUpdate.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Schedule Request Update",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ToDoListRequestUpdate.class)))
    @PatchMapping("/{uuid}")
    ResponseEntity<ToDoListResponse> update(@PathVariable String uuid, @Valid @RequestBody Map<String, Object> toDoListRequest) throws InvalidFormatException;

    @Operation(
            summary = "Delete an appointment",
            description = "Delete an appointment from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> delete(@PathVariable String uuid);

    @Operation(
            summary = "Get an appointment",
            description = "Get an appointment from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ToDoListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{uuid}")
    ResponseEntity<ToDoListResponse> get(@PathVariable String uuid);

    @Operation(
            summary = "Get all appointments",
            description = "Get all appointments from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ToDoListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    ResponseEntity<List<ToDoListResponse>> getAll();
}
