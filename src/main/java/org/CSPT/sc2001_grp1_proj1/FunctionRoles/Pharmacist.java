package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;

public class Pharmacist  extends HospitalStaff{
//private AppointmentOutcomeRecord appointmentOutComeRecord;
	private InventoryService inventoryService;
	//private AppointmentOutcomeService appointmentOutcomeService;
	private Scanner scanner;
	
    public Pharmacist(String hospitalStaffID, String name, String role, String gender, int age, InventoryService inventoryService) {
        super(hospitalStaffID, name, role, gender, age); 
        this.inventoryService = inventoryService;
		//this.appointmentOutcomeService = appointmentOutcomeService;
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
				viewAppointmentOutComeRecord(1); //delete 1 when you do the implementation
				break;
				case 2:
				updatePrescriptionStatus();
				break;
				case 3:
				displayStock();
				break;
				case 4:
				System.out.print("Enter medication name: ");
				String medicineName = scanner.nextLine();
				System.out.print("Enter quantity: ");
				int quantity = scanner.nextInt();
				submitReplenishmentRequest(medicineName, quantity);
				break;
				case 5:
				loggedIn = false;
				HospitalManagementApp.logout();
				break;
				default:
				System.out.println("Invalid option. Please enter a number from 1 to 5.");
			}

		}
	}
	
	public void viewAppointmentOutComeRecord(int appointmentID) 
	{
		//appointmentOutcomeService.ViewRecord(appointmentID);
		System.out.println("ViewAppointmentRecord: public void viewAppointmentOutComeRecord(int appointmentID)");
		//AppointmentOutcomeRecord record = appointmentOutcomeService.getAppointmentOutcomeRecord(appointmentID);
	}
	
	public void updatePrescriptionStatus() 
	{
		//appointmentOutComeRecord.updatePrescriptionStatus(prescriptionID, status);
		System.out.println("Update Prescription Status: public void updatePrescriptionStatus(int prescriptionID, String status) ;");
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
	
	//for testing
	//public class PharmacistTester {
	  //public static void main(String[] args) {
	    	// Initialize InventoryManager
	        //InventoryManager inventoryManager = new InventoryManager();

	        // Initialize InventoryService
	        //InventoryService inventoryService = new InventoryService(inventoryManager);
	        
	        //initialize new pharmacist
	        //Pharmacist pharmacist = new Pharmacist("H123", "john_johnson", "password123", "john.johnson@gmail.com", "Male", 35, 12345678, "Pharmacist", inventoryService);
	        
	      //System.out.println("\nSubmitting Replenishment Request:");
	     //pharmacist.submitReplenishmentRequest("Paracetamol", 100);

	     //   System.out.println("\nDisplaying Replenishment Requests:");
	    // pharmacist.displayReplenishmentRequests();
	   // }
	//}

}
