package com.example.singmysong;

public class BaiHat {
    private int id;
    private String name;
    private String singer;
    private float time;

    public BaiHat(int id, String name, String singer, float time) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.time = time;
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
