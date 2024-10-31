package org.CSPT.sc2001_grp1_proj1.entity;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HospitalStaffManager{
    protected List<HospitalStaff> staffList;
    public Date lastUpdatedTime;
    public String lastUpdatedBy;

    public HospitalStaffManager(List<HospitalStaff> staffList, Date lastUpdatedTime, String lastUpdatedBy) {
    this.staffList = staffList;
    this.lastUpdatedTime = lastUpdatedTime;
    this.lastUpdatedBy = lastUpdatedBy;
    }
 
    public void addStaffMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf
        (
            "\n Enter Staff details: \n Enter Staff ID:"
        );
        String staffID = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Role:"
        );
        String role = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Gender:"
        );
        String gender = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Age:"
        );
        int age = scanner.nextInt();

        HospitalStaff newStaff = new HospitalStaff(staffID,role,gender,age);

        staffList.add(newStaff);
    }

    public void removeStaffMember(){
        displayStaff();
        System.out.printf
        (
            "\n Enter Staff ID To Remove:"
        );
        Scanner scanner = new Scanner(System.in);
        String idToremove = scanner.nextLine();
        for (HospitalStaff staff : staffList) {
            if (staff.hospitalStaffID.equals(idToremove))
            {
                staffList.remove(staff);  
                break;          
            }
        }
    }

    public void updateStaffMember(){
        displayStaff();
        System.out.printf
        (
            "\n Enter Staff ID To Update:"
        );
        Scanner scanner = new Scanner(System.in);
        String idToUpdate = scanner.nextLine();

        HospitalStaff staffToUpdate = null;
        
        for (HospitalStaff staff : staffList) {
            if (staff.hospitalStaffID.equals(idToUpdate))
            {
                staffToUpdate = staff;  
                break;          
            }
        }

        if (staffToUpdate!=null)
        {
            boolean updatingStaff = true;
            while(updatingStaff)
            {
                System.out.printf
                (
                    "What would you like to update?\n 1 Staff ID?\n 2 Role?\n 3 Age?\n 4 Gender?\n 5 Cancel\n"
                );
                int actionStaff = scanner.nextInt();
                scanner.nextLine();
                switch (actionStaff) {
                    case 1 -> {
                        System.out.printf
                        (
                            "\nEnter new Staff ID:\n"
                        );
                        staffToUpdate.hospitalStaffID = scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.printf
                        (
                            "\nEnter new Role:\n"
                        );            
                        staffToUpdate.role = scanner.nextLine();
                    }
                    case 3 -> {
                        System.out.printf
                        (
                            "\nEnter new Age:\n"
                        );            
                        staffToUpdate.age = scanner.nextInt();
                        scanner.nextLine();

                    }
                    case 4 -> {
                        System.out.printf
                        (
                            "\nEnter new Gender:\n"
                        );  
                        staffToUpdate.gender = scanner.nextLine();            
                    }
                    default -> {
                        System.out.printf
                        (
                            "\nCancelled\n"
                        );   
                        updatingStaff = false;
                        break;                
                    }
                } 
            }           
        }
    }

    public void displayStaff(){
        for (HospitalStaff staff : staffList) {
            String printStaff = String.format("---------------\nStaff ID: %s\n Role: %s\n Gender: %s\n Age: %d\n---------------\n",staff.hospitalStaffID,staff.role,staff.gender,staff.age);
            System.out.printf(printStaff);
        }
    }
}