package org.CSPT.sc2001_grp1_proj1.entity;

public class Users {
    protected String hospitalID;
    private String name;
    protected String role;
    protected String gender;
    protected int age;
    private String username;
    private String password;
    private String email;
    private int phoneNo;

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

    public String gethospitalID(){
        return hospitalID;
    }
    public String getname(){
        return name;
    }
    public String getGender(){
        return role;
    }
    public int getAge(){
        return age;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail(){
        return email;
    }
    public int getPhoneNo(){
        return phoneNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
