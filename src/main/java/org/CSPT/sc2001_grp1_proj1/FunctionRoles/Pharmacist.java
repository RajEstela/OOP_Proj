package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;

public class Pharmacist  extends HospitalStaff{
//private AppointmentOutcomeRecord appointmentOutComeRecord;
	private InventoryService inventoryService;
	
	public Pharmacist(String hospitalStaffID, String role,String gender,int age) 
	{
		super(hospitalStaffID, role, gender,age);
		//this.appointmentOutComeRecord = appointmentOutComeRecord;
		this.inventoryService = inventoryService;
		
	}
	
	public void viewAppointmentOutComeRecord(int appointmentID) 
	{
		//appointmentOutComeRecord.ViewRecord(appointmentID);
	}
	
	public void updatePrescriptionStatus(int prescriptionID, String status) 
	{
		//appointmentOutComeRecord.updatePrescriptionStatus(prescriptionID, status);
	}
	
	// public void submitReplenishmentRequest(String medicineName, int quantity) 
	// {
	// 	inventoryService.submitReplenishmentRequest(medicineName, quantity, this.username);
	// }
	
	public void displayStock() 
	{
		inventoryService.displayStock();
	}
	
	public void displayReplenishmentRequests() 
	{
		inventoryService.displayReplenishmentRequests();
	}
	
	//for testing
	//public class PharmacistTester {
	  //  public static void main(String[] args) {
	    	// Initialize InventoryManager
	      //  InventoryManager inventoryManager = new InventoryManager();

	        // Initialize InventoryService
	       // InventoryService inventoryService = new InventoryService(inventoryManager);
	        
	        //initialize new pharmacist
	      //  Pharmacist pharmacist = new Pharmacist("H123", "john_johnson", "password123", "john.johnson@gmail.com", "Male", 35, 12345678, "Pharmacist", inventoryService);
	        
	      //  System.out.println("\nSubmitting Replenishment Request:");
	     // pharmacist.submitReplenishmentRequest("Paracetamol", 100);

	     //   System.out.println("\nDisplaying Replenishment Requests:");
	     //   pharmacist.displayReplenishmentRequests();
	   // }
	//}

}
