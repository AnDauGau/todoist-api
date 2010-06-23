package com.todoist;

public class Label {
    private int color;
    private int count;
    private long uid;
    private long id;
    private String name;

    public int getColor() {
        return color;
    }

    public int getCount() {
        return count;
    }

    public long getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Label[" + name + "]";
    }
}
