package com.todoist;


public class Project {

    private Long id;
    private Long user_id;
    private String name;
    private int cache_count;
    private Color color;
    private int indent;
    private int item_order;
    private boolean collapsed;

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCache_count() {
        return cache_count;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public int getItem_order() {
        return item_order;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Project[");
        sb.append("id=").append(id).append(",");
        sb.append("name=").append(name);
        sb.append("]");

        return sb.toString();
    }
}
