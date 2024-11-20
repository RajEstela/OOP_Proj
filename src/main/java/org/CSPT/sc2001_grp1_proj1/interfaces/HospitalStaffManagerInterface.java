package org.CSPT.sc2001_grp1_proj1.interfaces;

import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.entity.Users;

/**
 * Interface for managing hospital staff.
 * Provides methods for adding, removing, updating, and displaying staff members.
 */
public interface HospitalStaffManagerInterface {

    /**
     * Adds a new user to the system.
     *
     * @param validUsers A HashMap containing valid user data.
     */
    void addUser(HashMap<String, Users> validUsers);

    /**
     * Adds a new staff member to the hospital system.
     *
     * @param validUsers A HashMap containing valid user data.
     */
    void addStaffMember(HashMap<String, Users> validUsers);

    /**
     * Removes an existing staff member from the hospital system.
     */
    void removeStaffMember();

    /**
     * Updates the details of an existing staff member.
     */
    void updateStaffMember();

    /**
     * Displays the list of all hospital staff members.
     */
    void displayStaff();
}