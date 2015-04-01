package com.parse.models;

import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */
public class WeTubeUser extends ParseUser {

    public WeTubeUser(){

    }

    public WeTubeUser(boolean isLoggedIn){
        setLoggedStatus(isLoggedIn);
    }

    public void setLoggedStatus(boolean isLoggedIn) {
        put("isLoggedIn", isLoggedIn);
    }

}

