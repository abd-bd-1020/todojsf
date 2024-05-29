package com.example.todojsf.converter;


import com.example.todojsf.entity.Todo;
import com.example.todojsf.service.TodoService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass=Todo.class, managed=true)
public class TodoConverter implements Converter<Todo> {
    @Inject
    private TodoService todoService;
    @Override
    public String getAsString
            (FacesContext context, UIComponent component, Todo todo){
        if (todo == null) {
            return "";
        }
        if (todo.getId() != null) {
            return todo.getId().toString();
        }
        else {
            throw new ConverterException(
                    new FacesMessage("Invalid product ID"));
        }
    }
    @Override
    public Todo getAsObject
            (FacesContext context, UIComponent component, String id)
    {
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            return todoService.getTodoById(Long.valueOf(id));
        }
        catch (NumberFormatException e) {
            throw new ConverterException(
                    new FacesMessage("Invalid product ID"), e);
        }
    }
}