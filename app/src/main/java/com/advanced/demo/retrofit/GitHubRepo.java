package com.advanced.demo.retrofit;

/**
 * @author by sangw on 2017/8/15.
 */

public class GitHubRepo {

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "GitHubRepo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
