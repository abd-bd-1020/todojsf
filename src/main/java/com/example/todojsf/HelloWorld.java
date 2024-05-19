package com.example.todojsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class HelloWorld {
    private String input;
    private String output;
    public void submit() {
        output = "Hello World! You have typed: " + input;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }
    public String getOutput() {
        return output;
    }
}