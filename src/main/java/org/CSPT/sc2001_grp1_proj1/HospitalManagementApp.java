package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Admin;
import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Patient;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicineDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.StaffDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentManager;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.RolesEnum;
import org.CSPT.sc2001_grp1_proj1.entity.Users;

public class HospitalManagementApp {

    private static HashMap<String, String> validUsersLogin = new HashMap<>();
    private static HashMap<String, Users> validUsers = new HashMap<>();
    private static HashMap<String, Users> validUsersByID = new HashMap<>();
    private static HospitalStaffManager hospitalStaffManager;
    private static InventoryManager medicalInventoryManager;
    private static AppointmentManager appointmentManager;


    public static void refreshHashMaps(){
        validUsersLogin.clear();
        validUsers.clear();
        UserDataLoader.populateUsers(validUsersLogin, validUsers, validUsersByID);
    }

    public static void refreshvalidUsers(){
        validUsers.clear();
        validUsers = UserDataLoader.populateValidUsers(validUsers);
    }
    
    public static void main(String[] args) {
        UserDataLoader.populateUsers(validUsersLogin, validUsers, validUsersByID);
        hospitalStaffManager = loadHospitalStaff(validUsers);
        medicalInventoryManager = loadMedicalInventory();
        
        AppointmentsDataLoader aptmntDL = new AppointmentsDataLoader();
        appointmentManager = new AppointmentManager(aptmntDL);
        loginProcess();
    }

    private static void loginProcess() {
        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin(validUsersLogin, validUsers);

        while (true) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1. Login");
                System.out.println("2. Forgot Password");
                System.out.print("Please enter your choice (or type 'exit' to quit): ");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                }

                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        Users loggedInUser = userLogin.loginMenu();
                        if (loggedInUser != null) {
                            handleUserRole(loggedInUser); 
                        }
                        break;
                    case 2:
                        userLogin.forgotPassword(scanner);
                        break;
                    default:
                        System.out.println("Invalid option. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (1 or 2) or type 'exit' to quit.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    //TODO: ADD YOUR ROLES HERE post login
    private static void handleUserRole(Users user) {
        try {
            RolesEnum roleEnum = RolesEnum.valueOf(user.getRole());

            switch (roleEnum) {
                case Doctor -> System.out.println("This person is a Doctor.");
                case Administrator -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Admin admin = new Admin(hospitalStaffManager,medicalInventoryManager,appointmentManager,validUsersLogin, validUsers);  
                    admin.main();
                }
                case Pharmacists -> System.out.println("This person is a Pharmacist.");
                case Patient -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Patient patient = new Patient();
                    patient.main();
                }
                default -> throw new AssertionError("Unknown role: " + roleEnum);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Login Failed");
        }
    }

    private static HospitalStaffManager loadHospitalStaff(HashMap<String, Users> validUsers) {
        return StaffDataLoader.loadHospitalStaff(validUsers);
    }

    private static InventoryManager loadMedicalInventory() {
        return MedicineDataLoader.loadMedicalInventory("./data/MedicalInventory_List.xlsx");
    }

    public static HashMap<String, Users> getValidUsersByID() {
        return validUsersByID;
    }
    public static void logout() {
        System.out.println("Logging out...");
        loginProcess(); 
    }
}
