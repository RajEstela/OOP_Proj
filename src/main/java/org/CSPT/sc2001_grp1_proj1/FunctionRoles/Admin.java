/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;


/**
 *
 * @author RajEstela
 */

 public class Admin {

    private final HospitalStaffManager hospitalStaffManager;

    public Admin(HospitalStaffManager hospitalStaffManager) {
        this.hospitalStaffManager = hospitalStaffManager;
    }

    public void main() {
        boolean loggedIn = true;
        while(loggedIn){
            switch (administratorMenu()) {
                case 1 -> {
                    hospitalStaff(hospitalStaffManager);  // Use the passed instance
                }
                case 2 -> {
                    appointmentDetails();
                }
                case 3 -> {
                    medicationInventory();
                }
                case 4 -> {
                    approveRepReq();
                }
                case 5 -> {
                    HospitalManagementApp.logout();
                    System.out.printf("Bye!");
                    loggedIn = false;
                    break;
                }
            }
        }
    }

    private static int administratorMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
    
        while (true) {
            System.out.printf(
                "\n 1 View and Manage Hospital Staff\n 2 View Appointments details\n 3 View and Manage Medication Inventory\n 4 Approve Replenishment Requests\n 5 Logout\n Enter Choice: "
            );
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 5) {
                    throw new IllegalArgumentException("Please enter a valid option (1-5).");
                }
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); 
            }
        }
    
        return choice;
    }

    private static void hospitalStaff(HospitalStaffManager staff) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
    
        while (inStaffMenu) {
            System.out.printf(
                "\n 1 View Staff\n 2 Add Staff\n 3 Remove Staff\n 4 Update Staff\n 5 Administrator Main Menu\n Enter Choice: "
            );
    
            try {
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1 -> staff.displayStaff();
                    case 2 -> staff.addStaffMember();
                    case 3 -> staff.removeStaffMember();
                    case 4 -> staff.updateStaffMember();
                    case 5 -> {
                        System.out.println("\nReturning to Administrator Main Menu");
                        inStaffMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input to prevent infinite loop
            }
        }
    }
    

    private static void appointmentDetails() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void medicationInventory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void approveRepReq() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
