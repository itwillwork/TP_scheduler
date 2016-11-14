package com.example.edgarnurullin.tp_schedule.content;

public class Group {
    private Integer id;
    private String name;

    public Group() {
        this.id = null;
        this.name = "";
    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
