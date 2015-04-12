package com.parse.models;

import com.parse.ParseUser;

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
}

