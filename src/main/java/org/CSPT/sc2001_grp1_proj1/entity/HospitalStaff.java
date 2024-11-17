package org.CSPT.sc2001_grp1_proj1.entity;

public class HospitalStaff extends Users{
    public HospitalStaff(
        String hospitalID, 
        String name, 
        String role, 
        String gender, 
        int age
    ) {
        super(hospitalID, name, role, gender, age, null, null, null, 0); 
    }

    public int getAge() {
        return age;
    }

    public String getrole() {
        return role;
    }

    public String gethospitalStaffID() {
        return hospitalID;
    }
    public String getgender() {
        return gender;
    }
}

