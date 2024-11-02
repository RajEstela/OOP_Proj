package org.CSPT.sc2001_grp1_proj1.entity;

public class Users {
    private String hospitalID;
    public String username;
    private String password;
    public String email;
    public String gender;
    public int age;
    public int phoneNo;
    public String role;

    public Users(String hospitalID, String username,String password, String email, String gender, int age, int phoneNo,String role){
        this.hospitalID = hospitalID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = email;
        this.age =  age;
        this.phoneNo = phoneNo;
        this.role = role;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
