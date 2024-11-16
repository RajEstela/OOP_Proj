package org.CSPT.sc2001_grp1_proj1.entity;

public class InventoryService {

    private InventoryManager inventoryManager;
	
	public InventoryService(InventoryManager inventoryManager) 
	{
		this.inventoryManager = inventoryManager;
	}
	
	public void checkStockLevels() 
	{
		inventoryManager.displayStock();
	}
	
	//submit ReplenishmentRequest
	// public void submitReplenishmentRequest(String medicationName, int quantity, String requestedBy) 
	// {
	// 	ReplenishmentRequest request = new ReplenishmentRequest (medicationName, quantity, requestedBy);
	// 	inventoryManager.addReplenishmentRequests(request);
	// 	System.out.println("\nReplenishment request submitted for medication: " + medicationName + "\nQuantity: " + quantity);
	// }
	
	public void displayStock() 
	{
		inventoryManager.displayStock();
	}
	
	public void displayReplenishmentRequests() 
	{
		inventoryManager.displayReplenishmentRequests();
	}
}
