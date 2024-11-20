package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * The `Users` class represents a user in the hospital system.
 * This class contains user-related information such as hospital ID, name, role, contact details, and credentials.
 */

public class Users {
    /** The hospital ID of the user. */
    protected String hospitalID;
    
    /** The name of the user. */
    private String name;
    
    /** The role of the user (e.g., Doctor, Patient, Administrator). */
    protected String role;
    
    /** The gender of the user. */
    protected String gender;
    
    /** The age of the user. */
    protected int age;
    
    /** The username for the user to log in. */
    private String username;
    
    /** The password for the user to log in. */
    private String password;
    
    /** The email address of the user. */
    private String email;
    
    /** The phone number of the user. */
    private int phoneNo;

    /**
     * Constructs a new `Users` object with the provided user details.
     * 
     * @param hospitalID The hospital ID of the user.
     * @param name The name of the user.
     * @param role The role of the user (e.g., Doctor, Patient).
     * @param gender The gender of the user.
     * @param age The age of the user.
     * @param username The username for the user.
     * @param password The password for the user.
     * @param email The email address of the user.
     * @param phoneNo The phone number of the user.
     */
    public Users(    
    String  hospitalID
    ,String name
    ,String role
    ,String gender
    ,int    age
    ,String username
    ,String password
    ,String email
    ,int    phoneNo)
    {
        this.hospitalID = hospitalID;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
    }

   /**
     * Gets the hospital ID of the user.
     * 
     * @return The hospital ID.
     */
    public String gethospitalID() {
        return hospitalID;
    }

    /**
     * Gets the name of the user.
     * 
     * @return The name of the user.
     */
    public String getname() {
        return name;
    }

    /**
     * Gets the gender of the user.
     * 
     * @return The gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the age of the user.
     * 
     * @return The age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the role of the user (e.g., Doctor, Patient).
     * 
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return The phone number of the user.
     */
    public int getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets a new password for the user.
     * 
     * @param password The new password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets a new role for the user.
     * 
     * @param role The new role for the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

}
