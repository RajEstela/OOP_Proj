package org.CSPT.sc2001_grp1_proj1.entity;

public class Users {
    public String hospitalID;
    public String name;
    public String role;
    public String gender;
    public int age;
    public String username;
    public String password;
    public String email;
    public int phoneNo;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
