package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Admin;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicineDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.StaffDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.RolesEnum;
import org.CSPT.sc2001_grp1_proj1.entity.Users;

public class HospitalManagementApp {

    private static HashMap<String, String> validUsersLogin = new HashMap<>();
    private static HashMap<String, Users> validUsers = new HashMap<>();
    private static HospitalStaffManager hospitalStaffManager;
    private static InventoryManager medicalInventoryManager;

    public static void main(String[] args) {
        UserDataLoader.populateUsers(validUsersLogin, validUsers);
        hospitalStaffManager = loadHospitalStaff();
        medicalInventoryManager = loadMedicalInventory();
        loginProcess();
    }

    private static void loginProcess() {
        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin(validUsersLogin, validUsers);

        while (true) {
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
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    //TODO: ADD YOUR ROLES HERE post login
    private static void handleUserRole(Users user) {
        try {
            RolesEnum roleEnum = RolesEnum.valueOf(user.role);

            switch (roleEnum) {
                case Doctor -> System.out.println("This person is a Doctor.");
                case Administrator -> {
                    System.out.printf("\nWelcome %s\n", user.username);
                    Admin admin = new Admin(hospitalStaffManager,medicalInventoryManager);  
                    admin.main();
                }
                case Pharmacists -> System.out.println("This person is a Pharmacist.");
                case Patient -> System.out.println("This person is a Patient.");
                default -> throw new AssertionError("Unknown role: " + roleEnum);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Login Failed");
        }
    }

    private static HospitalStaffManager loadHospitalStaff() {
        return StaffDataLoader.loadHospitalStaff("./data/Staff_List.xlsx");
    }

    private static InventoryManager loadMedicalInventory() {
        return MedicineDataLoader.loadMedicalInventory("./data/MedicalInventory_List.xlsx");
    }

    public static void logout() {
        System.out.println("Logging out...");
        loginProcess(); 
    }
}
