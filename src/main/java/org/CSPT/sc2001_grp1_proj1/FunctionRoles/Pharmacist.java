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
		System.out.println("1. View Appointment Outcome Record");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Request");
		System.out.println("5. Logout");
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
				updatePrescriptionStatus();
				main();
				break;
				case 3:
				displayStock();
				main();
				break;
				case 4:
				System.out.print("Enter medication name: ");
				String medicineName = scanner.nextLine();
				System.out.print("Enter quantity: ");
				int quantity = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				submitReplenishmentRequest(medicineName, quantity);
				main();
				break;
				case 5:
				loggedIn = false;
				HospitalManagementApp.logout();
				break;
			}

		}
	}
	
	public void viewAppointmentOutComeRecord() 
	{
		appointmentOutcomeService.viewAppointmentOutComeRecord();
	}
	
	public void updatePrescriptionStatus() 
	{
		System.out.print("Enter the Appointment Outcome Record ID to update: ");
        String recordID = scanner.nextLine();
        System.out.print("Enter the new status (Completed / Processing): ");
        String newStatus = scanner.nextLine();

        // Update the status via the appointment outcome service
        boolean success = appointmentOutcomeService.updatePrescriptionStatus(recordID, newStatus);
        if (success) {
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("No record found with the given ID or update failed.");
        }
    }
	
	public void submitReplenishmentRequest(String medicineName, int quantity) 
	{
		String requestedBy = this.gethospitalStaffID();
		inventoryService.submitReplenishmentRequest(medicineName, quantity, requestedBy);
    }
	
	public void displayStock() 
	{
		inventoryService.displayStock();
	}
	
	public void displayReplenishmentRequests() 
	{
		inventoryService.displayReplenishmentRequests();
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
