package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Admin;
import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Doctor;
import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Patient;
import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Pharmacist;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicineDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.StaffDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentManager;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeService;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;
import org.CSPT.sc2001_grp1_proj1.entity.RolesEnum;
import org.CSPT.sc2001_grp1_proj1.entity.Users;
/**
 * Main class for the Hospital Management Application.
 * Provides functionality for user login, role-specific actions, and management of hospital resources
 * such as staff, inventory, and appointments.
 */
public class HospitalManagementApp {

    private static HashMap<String, String> validUsersLogin = new HashMap<>();
    private static HashMap<String, Users> validUsers = new HashMap<>();
    private static HashMap<String, Users> validUsersByID = new HashMap<>();
    private static HospitalStaffManager hospitalStaffManager;
    private static InventoryManager medicalInventoryManager;
    private static AppointmentManager appointmentManager;

    /**
     * Refreshes the user data from the data source and repopulates the valid user HashMaps.
     */
    public static void refreshHashMaps() {
        validUsersLogin.clear();
        validUsers.clear();
        UserDataLoader.populateUsers(validUsersLogin, validUsers, validUsersByID);
    }

    /**
     * Main entry point of the application.
     * Initializes hospital resources and starts the user login process.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        UserDataLoader.populateUsers(validUsersLogin, validUsers, validUsersByID);
        hospitalStaffManager = loadHospitalStaff(validUsers);
        medicalInventoryManager = loadMedicalInventory();
        medicalInventoryManager.replenishmentRequestInit();
        AppointmentsDataLoader aptmntDL = new AppointmentsDataLoader();
        appointmentManager = new AppointmentManager(aptmntDL);

        Scanner scanner = new Scanner(System.in);
        loginProcess(scanner);
    }

    /**
     * Handles the user login process, including options for login and password recovery.
     *
     * @param scanner A Scanner object for user input.
     */
    private static void loginProcess(Scanner scanner) {
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
                    System.exit(0);
                    break;
                }

                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        Users loggedInUser = userLogin.loginMenu(scanner);
                        if (loggedInUser != null) {
                            handleUserRole(loggedInUser, scanner);
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
    }

    /**
     * Handles user actions based on their role in the hospital system.
     *
     * @param user    The logged-in user.
     * @param scanner A Scanner object for user input.
     */
    private static void handleUserRole(Users user, Scanner scanner) {
        try {
            RolesEnum roleEnum = RolesEnum.valueOf(user.getRole());

            switch (roleEnum) {
                case Doctor -> {
                    System.out.printf("\nWelcome Doctor %s\n", user.getname());
                    Doctor doctor = new Doctor(user.gethospitalID(), user.getname(), user.getRole(), user.getGender(), user.getAge());
                    doctor.main();
                }
                case Administrator -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Admin admin = new Admin(hospitalStaffManager, medicalInventoryManager, appointmentManager, validUsersLogin, validUsers);
                    admin.main();
                }
                case Pharmacist -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    String hospitalStaffID = user.gethospitalID();
                    String name = user.getname();
                    String role = user.getRole();
                    String gender = user.getGender();
                    int age = user.getAge();
                    InventoryService inventoryService = new InventoryService(medicalInventoryManager);
                    AppointmentOutcomeService appointmentOutcomeService = new AppointmentOutcomeService();
                    Pharmacist pharmacist = new Pharmacist(hospitalStaffID, name, role, gender, age, inventoryService, appointmentOutcomeService);
                    pharmacist.main();
                }
                case Patient -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Patient patient = new Patient();
                    patient.main();
                }
                case Pending -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    System.out.printf("\nYou are yet to be assigned to any role. Please try again later.\n", user.getUsername());
                    loginProcess(scanner);
                }
                default -> throw new AssertionError("Unknown role: " + roleEnum);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Login Failed");
        }
    }

    /**
     * Loads hospital staff data from the data source.
     *
     * @param validUsers A HashMap of valid users.
     * @return A HospitalStaffManager object with loaded staff data.
     */
    private static HospitalStaffManager loadHospitalStaff(HashMap<String, Users> validUsers) {
        return StaffDataLoader.loadHospitalStaff(validUsers);
    }

    /**
     * Loads medical inventory data from the data source.
     *
     * @return An InventoryManager object with loaded inventory data.
     */
    private static InventoryManager loadMedicalInventory() {
        return MedicineDataLoader.loadMedicalInventory("./data/MedicalInventory_List.xlsx");
    }

    /**
     * Retrieves a HashMap of valid users by their IDs.
     *
     * @return A HashMap containing users indexed by their IDs.
     */
    public static HashMap<String, Users> getValidUsersByID() {
        return validUsersByID;
    }

    /**
     * Logs out the current user and redirects to the login process.
     *
     * @param scanner A Scanner object for user input.
     */
    public static void logout(Scanner scanner) {
        System.out.println("Logging out...");
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        loginProcess(scanner);
    }
}

