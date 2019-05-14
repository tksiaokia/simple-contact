package com.sean.simplecontact.models;

public class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public String getDisplayName(){
        return String.format("%s %s",firstName,lastName);
    }
}
