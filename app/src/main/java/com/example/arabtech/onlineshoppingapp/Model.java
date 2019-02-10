package com.example.arabtech.onlineshoppingapp;

public class Model {
private String proPic,post;
private String name,time,description;

    /*public Model( String proPic, String post, String name, String time, String description) {
        this.proPic = proPic;
        this.post = post;
        this.name = name;
        this.time = time;
        this.description = description;
    }*/

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
