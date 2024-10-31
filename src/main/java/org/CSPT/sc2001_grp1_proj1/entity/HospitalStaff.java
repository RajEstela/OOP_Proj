package org.CSPT.sc2001_grp1_proj1.entity;

public class HospitalStaff{
    protected String hospitalStaffID;
    protected  String role;
    protected String gender;
    protected int age;

    public HospitalStaff(String hospitalStaffID, String role,String gender,int age) {
        this.age = age;
        this.gender = gender;
        this.hospitalStaffID = hospitalStaffID;
        this.role = role;
    }

}

