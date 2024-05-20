package com.example.todojsf.service;

import com.example.todojsf.entity.Todo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class TodoService {
    @PersistenceContext(unitName = "todoDB")
    private EntityManager entityManager;

    public void create(Todo todo) {
        entityManager.persist(todo);
    }

    public List<Todo> getAllTodos() {
        return entityManager
                .createQuery("SELECT t FROM Todo t", Todo.class)
                .getResultList();
    }
    public void delete(Long todoId){
        try{
            Todo todo = entityManager.find(Todo.class,todoId);
            entityManager.remove(todo);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Todo todo) {
        entityManager.merge(todo);
    }
}
