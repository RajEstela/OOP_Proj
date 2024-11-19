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
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeService;
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
        medicalInventoryManager.replenishmentRequestInit();
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

    private static void handleUserRole(Users user) {
        try {
            RolesEnum roleEnum = RolesEnum.valueOf(user.getRole());

            switch (roleEnum) {
                case Doctor -> {
                    System.out.printf("\nHi!Dr. %s\n",user.getUsername());
                    Doctor doctor = new Doctor();
                    doctor.main();
                }
                case Administrator -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Admin admin = new Admin(hospitalStaffManager,medicalInventoryManager,appointmentManager,validUsersLogin, validUsers);  
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
                    pharmacist.main(); // Call the pharmacist's main method
                 } 
                case Patient -> {
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    Patient patient = new Patient();
                    patient.main();
                }
                case Pending ->{
                    System.out.printf("\nWelcome %s\n", user.getUsername());
                    System.out.printf("\nYou are yet to be assigned to any role. Please try again Later\n", user.getUsername());
                    loginProcess();
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

