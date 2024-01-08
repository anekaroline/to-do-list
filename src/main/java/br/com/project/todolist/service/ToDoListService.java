package br.com.project.todolist.service;

import br.com.project.todolist.domain.models.ToDoListEntity;
import br.com.project.todolist.repository.ToDoListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ToDoListService {

    private final ToDoListRepository toDoListRepository;

    public ToDoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    public List<ToDoListEntity> findAll(){
        return toDoListRepository.findAll();
    }

    public ToDoListEntity findById(String uuid) {
       return toDoListRepository.findById(UUID.fromString(uuid)).orElseThrow(NoSuchElementException::new);
    }


    public ToDoListEntity save(ToDoListEntity toDoListEntity) {
        return toDoListRepository.save(toDoListEntity);
    }

    public ToDoListEntity update(String uuid, ToDoListEntity toDoListEntity) {
        ToDoListEntity toDoListExisting = findById(uuid);
        BeanUtils.copyProperties(toDoListExisting, toDoListEntity, "uuid");
        return toDoListRepository.save(toDoListExisting);
    }


    public void delete(String uuid) {
        findById(uuid);
        toDoListRepository.deleteById(UUID.fromString(uuid));
    }

}
