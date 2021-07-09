package com.example.firebasetest;

public class MyModel {

    private  String todo;
    private  boolean completed;
    private Long time;

    public MyModel() {

    }

    public MyModel(String todo, boolean completed,Long time) {
        this.todo = todo;
        this.completed = completed;
        this.time=time;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Long getTime() {
        return time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
