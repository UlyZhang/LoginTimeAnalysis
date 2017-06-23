/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ulysses.LTA;

/**
 *
 * @author UlyssesZhang
 */
public class User {
    
    private final String ID;
    
    private final String logintime;

    User(String id, String logintime) {
        this.ID = id;
        this.logintime = logintime;
    }

    public String getID() {
        return ID;
    }

    public String getLogintime() {
        return logintime;
    }

}
