package br.com.project.todolist.domain.models;

import br.com.project.todolist.domain.enums.ToDoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "schedule")
@EqualsAndHashCode()
public class ToDoListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ToDoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public ToDoListEntity(ToDoListEntity original) {
        this.id = original.getId();
        this.title = original.getTitle();
        this.description = original.getDescription();
        this.startDate = original.getStartDate();
        this.endDate = original.getEndDate();
        this.status = original.getStatus();
        this.createdAt = original.getCreatedAt();
        this.updatedAt = original.getUpdatedAt();
    }

}
