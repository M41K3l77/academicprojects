package com.example.mangel.lectortickets.model;

import java.sql.Timestamp;

/**
 * Project LectorTickets
 * Created by M.Angel on 05/08/2016.
 * Usuario del la aplicacion.
 */
public class User {

    // username del usuario
    private String username;
    // pass del usuario
    private String password;
    // fecha que se usa en el login
    private String timestamp;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        java.util.Date date= new java.util.Date();
        Timestamp timestampuser=new Timestamp(date.getTime());
        this.timestamp=timestampuser.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
