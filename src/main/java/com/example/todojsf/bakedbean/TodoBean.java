package com.example.todojsf.bakedbean;

import com.example.todojsf.entity.Todo;
import com.example.todojsf.service.TodoService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TodoBean {

    protected Logger logger = Logger.getLogger(TodoBean.class.getName());

    public Todo getTodo() {
        return todo;
    }

    private final Todo todo = new Todo();

    public List<Todo> getTodos() {
        return todos;
    }

    private List<Todo> todos;
    private String controllerID;
    private String input;
    private String output;

    @Inject
    private TodoService todoService;
    @PostConstruct
    public void init() {
        todos = todoService.getAllTodos();
        logger.warning("this is the init with @postconstruct");

    }
    public void submit(){
        controllerID = String.valueOf(this);
        logger.warning("before submit msg len: " + todos.size());
        controllerID = String.valueOf(this);
        todoService.create(todo);
        todos.add(todo);
        logger.warning("todo submitted: " + todo);

        logger.warning("after submit todo len: " + todos.size());
        FacesContext.getCurrentInstance().addMessage("submitstatus", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message added successfully.", null));

    }
    public void delete(Long todoId){
        controllerID = String.valueOf(this);
        todoService.delete(todoId);
        todos.removeIf(t -> t.getId().equals(todoId));
        logger.warning("delete message");
    }



    public String getControllerID() {
        return controllerID;
    }
}

