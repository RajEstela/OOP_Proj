package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Represents a hospital staff member, extending the general user profile.
 * Contains additional attributes specific to hospital staff.
 */

public class HospitalStaff extends Users{

    /**
     * Constructs a new HospitalStaff instance.
     *
     * @param hospitalID The unique identifier for the hospital staff member.
     * @param name       The name of the staff member.
     * @param role       The role of the staff member (e.g., Nurse, Doctor).
     * @param gender     The gender of the staff member.
     * @param age        The age of the staff member.
     */

    public HospitalStaff(
        String hospitalID, 
        String name, 
        String role, 
        String gender, 
        int age
    ) {
        super(hospitalID, name, role, gender, age, null, null, null, 0); 
    }

    /**
     * Gets the age of the hospital staff member.
     *
     * @return The age.
     */

    public int getAge() {
        return age;
    }
    
    /**
     * Gets the role of the hospital staff member.
     *
     * @return The role.
     */

    public String getrole() {
        return role;
    }

    /**
     * Gets the hospital staff member's unique ID.
     *
     * @return The hospital staff ID.
     */

    public String gethospitalStaffID() {
        return hospitalID;
    }

     /**
     * Gets the gender of the hospital staff member.
     *
     * @return The gender.
     */
    
    public String getgender() {
        return gender;
    }
}

