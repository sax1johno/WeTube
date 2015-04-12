package com.parse.models;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2/26/2015.
 */
public class WeTubeUser extends ParseUser {

    public WeTubeUser(){

    }

    public WeTubeUser(boolean isLoggedIn, boolean isInSession){
        setLoggedStatus(isLoggedIn);
        setSessionStatus(isInSession);
    }

    public void setLoggedStatus(boolean isLoggedIn) {
        put("isLoggedIn", isLoggedIn);
    }
    public void setSessionStatus(boolean isInSession) { put("isInSession", isInSession); }
    public boolean getSessionStatus() { return getBoolean("isInSession"); }
    public List<String> getTags() {
        ArrayList<String> tags = (ArrayList<String>) get("tags");
        return tags;
    }
    public void addTag(String tag) { add("tags", tag); }
    public void removeTag(String tag) {  }

}

