/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.UserLogin;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentManager;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.Users;


/**
 * The {@code Admin} class represents an administrator in the hospital management system.
 * It provides functionality for managing hospital staff, appointments, and inventory, 
 * as well as approving replenishment requests.
 * <p>
 * This class extends {@code UserLogin} to leverage the user authentication system.
 * </p>
 *
 * @author RajEstela
 * @version 1.0
 * @since 2024
 */

 public class Admin extends UserLogin {

    private final HospitalStaffManager hospitalStaffManager;
    private final InventoryManager medicalInventoryManager;
    private final AppointmentManager appointmentManager;

    /**
     * Constructs a new {@code Admin} instance with the specified managers and user data.
     *
     * @param hospitalStaffManager    the manager for hospital staff
     * @param medicalInventoryManager the manager for medical inventory
     * @param appointmentManager      the manager for appointments
     * @param validUsersLogin         a map of valid user logins
     * @param validUsers              a map of valid user details
     */
    public Admin(HospitalStaffManager hospitalStaffManager,InventoryManager medicalInventoryManager,AppointmentManager appointmentManager,HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers ) {
        super(validUsersLogin, validUsers);
        this.hospitalStaffManager = hospitalStaffManager;
        this.medicalInventoryManager = medicalInventoryManager;
        this.appointmentManager = appointmentManager;
    }
    /**
     * The main administrative interface. Provides a menu for accessing 
     * various administrative functionalities in a loop until the user logs out.
     */
    public void main() {
        boolean loggedIn = true;
        while(loggedIn){
            switch (administratorMenu()) {
                case 1 -> {
                    hospitalStaff(hospitalStaffManager,this.validUsers); 
                }
                case 2 -> {
                    appointmentDetails(appointmentManager);
                }
                case 3 -> {
                    medicationInventory(medicalInventoryManager);
                }
                case 4 -> {
                    approveRepReq(medicalInventoryManager);
                }
                case 5 -> {
                    HospitalManagementApp.logout(null);
                    System.out.printf("Bye!");
                    loggedIn = false;
                    break;
                }
            }
        }
    }
    /**
     * Displays the administrator menu and captures the user's choice.
     *
     * @return an integer representing the user's choice
     */
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
    /**
     * Provides options to view, add, remove, and update hospital staff details.
     *
     * @param staff       the manager for hospital staff
     * @param validUsers  a map of valid user details
     */
    private static void hospitalStaff(HospitalStaffManager staff,HashMap<String, Users> validUsers) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
    
        while (inStaffMenu) {
            System.out.printf(
                "\n1 View Staff\n2 Add Staff\n3 Remove Staff\n4 Update Staff\n5 Add User\n6 Administrator Main Menu\n Enter Choice: "
            );
    
            try {
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1 -> staff.displayStaff();
                    case 2 -> staff.addStaffMember(validUsers);
                    case 3 -> staff.removeStaffMember();
                    case 4 -> staff.updateStaffMember();
                    case 5 -> staff.addUser(validUsers);
                    case 6 -> {
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
    
    /**
     * Allows the administrator to view appointment details.
     *
     * @param appointmentManager the manager for appointments
     */
    private static void appointmentDetails(AppointmentManager appointmentManager) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
    
        while (inStaffMenu) {
            System.out.print("\n1 View Appointments\n2 Administrator Main Menu\nEnter your choice:");  
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
    
                switch (choice) {
                    case 1 -> appointmentManager.viewAppointments();
                    case 2 -> {
                        System.out.println("\nReturning to Administrator Main Menu");
                        inStaffMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }
    /**
     * Provides functionality to view and manage the medical inventory.
     *
     * @param medicalInventoryManager the manager for medical inventory
     */
    private static void medicationInventory(InventoryManager medicalInventoryManager) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
    
        while (inStaffMenu) {
            System.out.printf(
                "\n1 View Medical Inventory Stocks\n2 Add Medical Inventory Stock\n3 Remove Medical Inventory Stock\n4 Update Medical Inventory Stock Count\n5 Update Medical Inventory Stock Alert Level\n6 Add Medicine Into Inventory\n7 Remove Medicine From Inventory\n8 View Replenishment Requests\n9 Administrator Main Menu\n Enter Choice: "
            );
    
            try {
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1 -> medicalInventoryManager.displayStock();
                    case 2 -> medicalInventoryManager.addStock();
                    case 3 -> medicalInventoryManager.removeStock();
                    case 4 -> medicalInventoryManager.updateStockCount();
                    case 5 -> medicalInventoryManager.updateStockAlertLevel();
                    case 6 -> medicalInventoryManager.addMedicine();
                    case 7 -> medicalInventoryManager.removeMedicine();
                    case 8 -> medicalInventoryManager.displayReplenishmentRequests();
                    case 9 -> {
                        System.out.println("\nReturning to Administrator Main Menu");
                        inStaffMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        }   
     }
    /**
     * Provides functionality to view and approve replenishment requests.
     *
     * @param medicalInventoryManager the manager for medical inventory
     */
    private static void approveRepReq(InventoryManager medicalInventoryManager) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
    
        while (inStaffMenu) {
            System.out.printf(
                "\n1 View Replenishment Requests\n2 Approve Replenishment Requests\n3 Administrator Main Menu\n Enter Choice: "
            );
    
            try {
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1 -> medicalInventoryManager.displayReplenishmentRequests();
                    case 2 -> medicalInventoryManager.approveReplenishmentRequests();
                    case 3 -> {
                        System.out.println("\nReturning to Administrator Main Menu");
                        inStaffMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        }   
    }

}
