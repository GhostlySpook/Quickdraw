package com.game.quickdrawv2;

public class Reaction {
    int id;
    String name;
    int time;
    String created;
    String modified;

    public Reaction(int id, String name, int time){
        this.id = id;
        this.name = name;
        this.time = time;
        this.created = "";
        this.modified = "";
    }

    public Reaction(int id, String name, int time, String created, String modified){
        this.id = id;
        this.name = name;
        this.time = time;
        this.created = created;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
