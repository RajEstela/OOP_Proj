package org.CSPT.sc2001_grp1_proj1.entity;

public class HospitalStaff extends Users{
    public String hospitalStaffID;

    public HospitalStaff(
        String hospitalStaffID, 
        String name, 
        String role, 
        String gender, 
        int age
    ) {
        super(null, name, role, gender, age, null, null, null, 0); 
        this.hospitalStaffID = hospitalStaffID;
    }

    public int getAge() {
        return age;
    }

    public String getrole() {
        return role;
    }

    public String gethospitalStaffID() {
        return hospitalStaffID;
    }
    public String getgender() {
        return gender;
    }
}

