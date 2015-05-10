package com.example.ricardogarcia.politojobs;

import java.io.Serializable;

/**
 * Created by ricardogarcia on 26/04/15.
 */
public class Message implements Serializable{
    private String subject;
    private String message;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
