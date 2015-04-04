package com.gmail.markdevw.wetube.api.model;

/**
 * Created by Mark on 4/3/2015.
 */
public class UserItem {
    String name;

    public UserItem(String name){
        setName(name);
    }

    public String getName() { return name;}
    public void setName(String name) {this.name = name; }
}
