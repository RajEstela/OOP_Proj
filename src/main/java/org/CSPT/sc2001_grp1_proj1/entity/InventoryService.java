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
    public void submitReplenishmentRequest(String medicineName, int quantity, String requestedBy) {
        ReplenishmentRequest request = new ReplenishmentRequest(medicineName, quantity, requestedBy, "New");
        replenishmentRequests.add(request);
        ReplenishmentRequestLoader.addReplenishmentRequest(request);
        System.out.println("Replenishment request submitted succesfully for medication:  " + medicineName + " with a quantity of: " + quantity);
    }
}
	
	
