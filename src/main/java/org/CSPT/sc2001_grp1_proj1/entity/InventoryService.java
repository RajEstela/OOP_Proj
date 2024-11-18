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
	
	
