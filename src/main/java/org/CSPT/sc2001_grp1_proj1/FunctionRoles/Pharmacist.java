package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeService;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;

public class Pharmacist  extends HospitalStaff{
//private AppointmentOutcomeRecord appointmentOutComeRecord;
	private InventoryService inventoryService;
	private AppointmentOutcomeService appointmentOutcomeService;
	private Scanner scanner;
	
    public Pharmacist(String hospitalStaffID, String name, String role, String gender, int age, InventoryService inventoryService, AppointmentOutcomeService appointmentOutcomeService) {
        super(hospitalStaffID, name, role, gender, age); 
        this.inventoryService = inventoryService;
		this.appointmentOutcomeService = appointmentOutcomeService;
		this.scanner = new Scanner(System.in); 
    }

		public void showMenu() {
		System.out.println("1. View All Appointment Outcome Records");
		System.out.println("2. View Pending Appointment Outcome Records");
		System.out.println("3. Update Prescription Status");
		System.out.println("4. View Medication Inventory");
		System.out.println("5. Submit Replenishment Request");
		System.out.println("6. View Pending Replenishment Request");
		System.out.println("7. Logout");
	}
	
	public void main()
	{
		boolean loggedIn = true;
        while (loggedIn) 
		{
            showMenu();
            int option = getValidOption();
            switch (option)
			{
				case 1:
				viewAppointmentOutComeRecord();
				main();
				break;
				case 2:
				viewPendingAppointmentOutcomeRecords();
				main();
				break;
				case 3:
				updatePrescriptionStatus();
				main();
				break;
				case 4:
				displayStock();
				break;
				case 5:
				System.out.print("Enter medication name: ");
				String medicineName = scanner.nextLine();
				System.out.print("Enter quantity: ");
				int quantity = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				submitReplenishmentRequest(medicineName, quantity);
				main();
				break;
				case 6:
				viewPendingReplenishmentRequests();
				break;
				case 7:
				loggedIn = false;
				HospitalManagementApp.logout(null);
				break;
			}

		}
	}
	
	public void viewAppointmentOutComeRecord() 
	{
		System.out.println("========= Displaying All Appointment Outcome Records ==========");
		appointmentOutcomeService.viewAppointmentOutComeRecord();
		System.out.println("======================== END ==================================");
	}

	public void viewPendingAppointmentOutcomeRecords()
	{
		System.out.println("====== Displaying Pending Appointment Outcome Records =======");
		appointmentOutcomeService.viewPendingAppointmentOutcomeRecords();
		System.out.println("======================= END =================================");
	}

	public void updatePrescriptionStatus() 
	{
		System.out.print("Please enter the Appointment Outcome Record ID for update: ");
        String recordID = scanner.nextLine();
        System.out.print("Please enter the new status (Dispensed / Pending): ");
        String newStatus = scanner.nextLine();

        // Update the status via the appointmentOutcomeService
        boolean success = appointmentOutcomeService.updatePrescriptionStatus(recordID, newStatus);
        if (success) {
            System.out.println("Prescription status for " + recordID + " has been updated successfully.");
        } else {
            System.out.println("No record found for " + recordID + " or update failed.");
        }
    }
	
	public void submitReplenishmentRequest(String medicineName, int quantity) 
	{
		String requestedBy = this.gethospitalStaffID();
		inventoryService.submitReplenishmentRequest(medicineName, quantity, requestedBy);
    }
	
	public void displayStock() 
	{
		System.out.println("=================== Displaying All Medication List ===================");
		inventoryService.displayStock();
		System.out.println("============================== END ===================================");

	}
	
	public void viewPendingReplenishmentRequests() 
	{
		System.out.println("============ Displaying Your Pending Replenishment Requests ============");
		String requestedBy = this.gethospitalStaffID(); 
		inventoryService.viewPendingReplenishmentRequests(requestedBy);
		System.out.println("============================= END ======================================");
	}

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
