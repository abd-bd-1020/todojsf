package com.example.todojsf.backingbean;

import com.example.todojsf.entity.Todo;
import com.example.todojsf.service.TodoService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class TodoBean implements Serializable {

    protected Logger logger = Logger.getLogger(TodoBean.class.getName());
    public Todo getTodo() {
        return todo;
    }
    private boolean isNew = true;


    private final Todo todo = new Todo();

    public List<Todo> getTodos() {
        return todos;
    }

    private List<Todo> todos;
    private String controllerID;


    @Inject
    private TodoService todoService;
    @PostConstruct
    public void init() {
        todos = todoService.getAllTodos();

    }
    public void submit(){
        try{
            if(isNew){
                todoService.create(todo);
                todos.add(new Todo(todo));
                FacesContext.getCurrentInstance().addMessage("submitstatus", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message added successfully.", null));

            }
            else{
                isNew = true;
                todoService.update(todo);
                for (int i = 0; i < todos.size(); i++) {
                    if (todos.get(i).getId().equals(todo.getId())) {
                        todos.set(i, new Todo(todo));
                        break;
                    }
                }
            }

        }
        catch (Exception e){
            logger.warning(e.getMessage());

        }
        finally {
            resetTodo();

        }



    }


    public void updateModal(Todo clickedTodo){
        isNew = false;
        todo.setId(clickedTodo.getId());
        todo.setDescription(clickedTodo.getDescription());
        todo.setTitle(clickedTodo.getTitle());
        todo.setCompleted(clickedTodo.isCompleted());
        todo.setStarred(clickedTodo.isStarred());



    }

    public void delete(Long todoId){
        logger.warning(String.valueOf(todoId));
        controllerID = String.valueOf(this);
        todoService.delete(todoId);
        todos.removeIf(t -> t.getId().equals(todoId));
    }
    public void resetTodo() {
        isNew = true;
        Todo newTodo = new Todo();
        todo.setId(newTodo.getId());
        todo.setTitle(newTodo.getTitle());
        todo.setDescription(newTodo.getDescription());
        todo.setStarred(newTodo.isStarred());
        todo.setCompleted(newTodo.isCompleted());
    }


    public String getControllerID() {
        return controllerID;
    }


    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}

