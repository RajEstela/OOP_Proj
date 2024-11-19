package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.dataLoader.ReplenishmentRequestLoader;

public class InventoryService {

    private InventoryManager inventoryManager;
	private List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();
	
	public InventoryService(InventoryManager inventoryManager) 
	{
		this.inventoryManager = inventoryManager;
		this.replenishmentRequests = ReplenishmentRequestLoader.loadReplenishmentRequests();
    }
	
	// Method to clear the replenishment requests
	public void clearReplenishmentRequests() {
		replenishmentRequests.clear();
		System.out.println("Replenishment requests cleared.");
	}
	//display inventory stock
	public void displayStock() 
	{
		inventoryManager.displayStock();
	}
	
	public void displayReplenishmentRequests() 
	{
		for(ReplenishmentRequest request: replenishmentRequests)
		{
			System.out.println(request);
		}
	}
	//display details of the replenishment requests
	public void displayReplenishmentRequestDetails()
	{
		for (ReplenishmentRequest request : replenishmentRequests) {
			request.displayReplenishmentRequestDetails(); // Call the new method
			System.out.println(); // For better readability
	}
	}
	//view Pending Replenishment Requests raised by current user
	public void viewPendingReplenishmentRequests(String requestedBy) {
        List<ReplenishmentRequest> pendingRequests = getPendingRequests(requestedBy);
        if (pendingRequests.isEmpty()) {
            System.out.println("No pending replenishment requests submitted by you.");
            return;
        }
        for (ReplenishmentRequest pendingReplenishmentRequest : pendingRequests) {
            pendingReplenishmentRequest.displayReplenishmentRequestDetails(); // Ensure this method exists
        }
    }
	//filter to only show Pending requests
    public List<ReplenishmentRequest> getPendingRequests(String requestedBy) {
        List<ReplenishmentRequest> pendingRequests = new ArrayList<>();
        for (ReplenishmentRequest replenishmentRequest : replenishmentRequests) {
            // Check if the request is pending and submitted by the user
            if ("New".equalsIgnoreCase(replenishmentRequest.getStatus()) &&
                requestedBy.equalsIgnoreCase(replenishmentRequest.getRequestedBy())) {
                pendingRequests.add(replenishmentRequest);
            }
        }
        return pendingRequests;
    }

    // Submit a new replenishment request
    public void submitReplenishmentRequest(String medicineName, int quantity, String requestedBy) 
	{
		boolean requestExists = false;

		//to check if request already exists
        for (ReplenishmentRequest existingRequest : replenishmentRequests)
		{
			if(existingRequest.getMedicineName().equalsIgnoreCase(medicineName) && existingRequest.getStatus().equalsIgnoreCase("New"))
			{
				//update quantity
				int newQuantity = existingRequest.getQuantity() + quantity;
				existingRequest.quantity = newQuantity;
			
			
			//update request in excel file
			ReplenishmentRequestLoader.updateReplenishmentRequest(existingRequest);
			requestExists = true;
			System.out.println("There is already a pending replenishment request for medication: " + medicineName + ".");
			System.out.println("Updated replenishment request for medication: " + medicineName + " to new quantity: " + newQuantity);
			break;
			}
		}
		if(!requestExists)
		{
			ReplenishmentRequest request = new ReplenishmentRequest("R",medicineName, quantity, requestedBy, "New");
        	ReplenishmentRequestLoader.addReplenishmentRequest(request);
			replenishmentRequests.add(request);
        	System.out.println("Replenishment request submitted succesfully for medication:  " + medicineName + " with a quantity of: " + quantity);
		}

    }
}
	
	
