package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeService;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;

/**
 * Class representing the Pharmacist role in the hospital management system.
 * Extends the {@link HospitalStaff} class and provides functionalities specific to a Pharmacist,
 * such as managing prescriptions, viewing and updating inventory, and handling replenishment requests.
 */
public class Pharmacist extends HospitalStaff {

    private InventoryService inventoryService;
    private AppointmentOutcomeService appointmentOutcomeService;
    private Scanner scanner;

    /**
     * Constructs a new Pharmacist object with the specified details.
     *
     * @param hospitalStaffID          The ID of the hospital staff.
     * @param name                     The name of the pharmacist.
     * @param role                     The role of the pharmacist.
     * @param gender                   The gender of the pharmacist.
     * @param age                      The age of the pharmacist.
     * @param inventoryService         The inventory service used by the pharmacist.
     * @param appointmentOutcomeService The appointment outcome service used by the pharmacist.
     */
    public Pharmacist(String hospitalStaffID, String name, String role, String gender, int age, 
                      InventoryService inventoryService, AppointmentOutcomeService appointmentOutcomeService) {
        super(hospitalStaffID, name, role, gender, age);
        this.inventoryService = inventoryService;
        this.appointmentOutcomeService = appointmentOutcomeService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu options available to the pharmacist.
     */
    public void showMenu() {
        System.out.println("1. View All Appointment Outcome Records");
        System.out.println("2. View Pending Appointment Outcome Records");
        System.out.println("3. Update Prescription Status");
        System.out.println("4. View Medication Inventory");
        System.out.println("5. Dispense Medication");
        System.out.println("6. Submit Replenishment Request");
        System.out.println("7. View Pending Replenishment Request");
        System.out.println("8. Logout");
    }

    /**
     * Main method for handling the pharmacist's actions. Displays the menu and processes the user's input.
     */
    public void main() {
        boolean loggedIn = true;
        while (loggedIn) {
            showMenu();
            int option = getValidOption();
            switch (option) {
                case 1 -> {
                    viewAppointmentOutComeRecord();
                    main();
                }
                case 2 -> {
                    viewPendingAppointmentOutcomeRecords();
                    main();
                }
                case 3 -> {
                    updatePrescriptionStatus();
                    main();
                }
                case 4 -> displayStock();
                case 5 -> removeStock();
                case 6 -> {
                    System.out.print("Enter medication name: ");
                    String medicineName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    submitReplenishmentRequest(medicineName, quantity);
                    main();
                }
                case 7 -> viewPendingReplenishmentRequests();
                case 8 -> {
                    loggedIn = false;
                    HospitalManagementApp.logout(null);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays all appointment outcome records.
     */
    public void viewAppointmentOutComeRecord() {
        System.out.println("========= Displaying All Appointment Outcome Records ==========");
        appointmentOutcomeService.viewAppointmentOutComeRecord();
        System.out.println("======================== END ==================================");
    }

    /**
     * Displays all pending appointment outcome records.
     */
    public void viewPendingAppointmentOutcomeRecords() {
        System.out.println("====== Displaying Pending Appointment Outcome Records =======");
        appointmentOutcomeService.viewPendingAppointmentOutcomeRecords();
        System.out.println("======================= END =================================");
    }

    /**
     * Updates the prescription status for a specific appointment outcome record.
     */
    public void updatePrescriptionStatus() {
        System.out.print("Please enter the Appointment Outcome Record ID for update: ");
        String recordID = scanner.nextLine();
        System.out.print("Please enter the new status (Dispensed / Pending): ");
        String newStatus = scanner.nextLine();

        boolean success = appointmentOutcomeService.updatePrescriptionStatus(recordID, newStatus);
        if (success) {
            System.out.println("Prescription status for " + recordID + " has been updated successfully.");
        } else {
            System.out.println("No record found for " + recordID + " or update failed.");
        }
    }

    /**
     * Submits a replenishment request for a specific medication.
     *
     * @param medicineName The name of the medication to replenish.
     * @param quantity     The quantity of medication to request.
     */
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        String requestedBy = this.gethospitalStaffID();
        inventoryService.submitReplenishmentRequest(medicineName, quantity, requestedBy);
    }

    /**
     * Displays the current stock of medication in the inventory.
     */
    public void displayStock() {
        System.out.println("=================== Displaying All Medication List ===================");
        inventoryService.displayStock();
        System.out.println("============================== END ===================================");
    }

    /**
     * Remove medication in the inventory.
     */
    public void removeStock() {
        System.out.println("=================== Dispensing Medication View ===================");
        inventoryService.removeStock();
        System.out.println("============================== END ===================================");
    }


    /**
     * Displays all pending replenishment requests submitted by the pharmacist.
     */
    public void viewPendingReplenishmentRequests() {
        System.out.println("============ Displaying Your Pending Replenishment Requests ============");
        String requestedBy = this.gethospitalStaffID();
        inventoryService.viewPendingReplenishmentRequests(requestedBy);
        System.out.println("============================= END ======================================");
    }

    /**
     * Prompts the user to select a valid menu option and validates the input.
     *
     * @return The valid menu option selected by the user.
     */
    private int getValidOption() {
        while (true) {
            try {
                System.out.print("Please select an option: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
