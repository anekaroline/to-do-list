package br.com.project.todolist.repository;

import br.com.project.todolist.domain.models.ToDoListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoListEntity, UUID> {
}
