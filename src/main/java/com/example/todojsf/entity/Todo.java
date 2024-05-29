package com.example.todojsf.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class Todo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) @Lob
    private String title;
    @Column(nullable = false)
    private String description;
    private boolean completed;
    private boolean starred;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return completed == todo.completed && starred == todo.starred && Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(description, todo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, completed, starred);
    }

    public Todo(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.starred = todo.isStarred();
        this.completed = todo.isCompleted();
    }

    // Default constructor
    public Todo() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}