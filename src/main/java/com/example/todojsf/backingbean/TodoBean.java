package com.example.todojsf.backingbean;

import com.example.todojsf.entity.Todo;
import com.example.todojsf.service.TodoService;
import jakarta.annotation.PostConstruct;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
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

//    no code today
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

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object selectedTodoId = externalContext.getSessionMap().get("selectedTodoId");

        if (selectedTodoId != null) {
            try {
                Long todoId = Long.parseLong(selectedTodoId.toString());
                Todo selectedTodo = todoService.getTodoById(todoId);
                if (selectedTodo != null) {
                    todo.setId(selectedTodo.getId());
                    todo.setDescription(selectedTodo.getDescription());
                    todo.setTitle(selectedTodo.getTitle());
                    todo.setCompleted(selectedTodo.isCompleted());
                    todo.setStarred(selectedTodo.isStarred());
                    isNew = false;
                }
                externalContext.getSessionMap().remove("selectedTodoId");
            } catch (NumberFormatException e) {
                logger.warning("Invalid todo ID: " + selectedTodoId);
            }
        }
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
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/view/todo.xhtml");
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }

        }
        catch (Exception e){
            logger.warning(e.getMessage());

        }
        finally {
            resetTodo();

        }

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



    public void todoEditorNavigation() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/view/todoEditor.xhtml");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public void todoEditorNavigationWithData(Long  todoId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        try {
            externalContext.getSessionMap().put("selectedTodoId", todoId);
            externalContext.redirect(externalContext.getRequestContextPath() + "/view/todoEditor.xhtml");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}

