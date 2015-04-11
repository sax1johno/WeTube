package com.gmail.markdevw.wetube.api.model;

/**
 * Created by Mark on 4/3/2015.
 */
public class UserItem {
    String name;
    String id;
    boolean isInSession;

    public UserItem(String name, String id, boolean isInSession){

        setName(name);
        setId(id);
        setSessionStatus(isInSession);
    }

    public String getName() { return name;}
    public void setName(String name) {this.name = name; }
    public String getId() { return id; }
    public void setId(String id) {this.id = id; }
    public boolean getSessionStatus() { return isInSession; }
    public void setSessionStatus(boolean isInSession) {this.isInSession = isInSession; }

}
