package br.com.project.todolist.service;

import br.com.project.todolist.domain.dto.ToDoListRequest;
import br.com.project.todolist.domain.models.ToDoListEntity;
import br.com.project.todolist.exceptionhandler.exceptions.EndDateBeforeStartDateException;
import br.com.project.todolist.exceptionhandler.exceptions.WithoutModifications;
import br.com.project.todolist.repository.ToDoListRepository;
import br.com.project.todolist.utils.EntityMerger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.*;

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
       return toDoListRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new NoSuchElementException(uuid));
    }


    public ToDoListEntity save(ToDoListEntity toDoListEntity) {
        isValidDate(toDoListEntity);
        return toDoListRepository.save(toDoListEntity);
    }

    public ToDoListEntity update(String uuid, Map<String, Object> objectMap, ToDoListEntity  toDoListEntity) {
        ToDoListEntity toDoListExisting = findById(uuid);
        isValidDate(toDoListEntity);
        var clone = new ToDoListEntity(toDoListExisting);
        EntityMerger.mergeData(clone, objectMap);
        verificaSeHouveModanca(clone, toDoListExisting);
        return toDoListRepository.save(clone);
    }

    private void verificaSeHouveModanca(ToDoListEntity clone, ToDoListEntity toDoListExisting) {
        if(clone.equals(toDoListExisting)){
            throw new WithoutModifications();
        }
    }


    public void delete(String uuid) {
        findById(uuid);
        toDoListRepository.deleteById(UUID.fromString(uuid));
    }

    public void isValidDate(ToDoListEntity toDoListEntity) {
        if(Objects.nonNull(toDoListEntity.getStartDate()) && Objects.nonNull(toDoListEntity.getEndDate())){
            boolean isAfter = !toDoListEntity.getStartDate().isAfter(toDoListEntity.getEndDate());
            if(!isAfter){
                throw new EndDateBeforeStartDateException();
            }
        }

    }

}
