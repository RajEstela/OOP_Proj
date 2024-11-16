package org.CSPT.sc2001_grp1_proj1.interfaces;

import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.entity.Users;

public interface HospitalStaffManagerInterface {
    void addUser();
    void addStaffMember(HashMap<String, Users> validUsers);
    void removeStaffMember();
    void updateStaffMember();
    void displayStaff();  
} 