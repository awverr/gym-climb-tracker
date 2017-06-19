package com.topoutlabs.gymclimbtracker.model;

/**
 * Created by aubry on 3/22/2017.
 */

public class User {

    private String uid;
    private String emailAddress;
    private String firstName;
    private String lastName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String emailAddress, String firstName, String lastName){
        this.uid = uid;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
