package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.dataLoader.ReplenishmentRequestLoader;

/**
 * The InventoryService class manages inventory-related operations including displaying stock,
 * submitting replenishment requests, viewing pending requests, and clearing all requests.
 * It acts as an intermediary to interact with the InventoryManager and manages a list of replenishment requests.
 */

public class InventoryService {

    private InventoryManager inventoryManager;
	private List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();
	
	/**
     * Constructor for the InventoryService class.
     * Initializes the inventory manager and loads replenishment requests from an external source.
     * 
     * @param inventoryManager The InventoryManager instance that handles inventory operations.
     */

	public InventoryService(InventoryManager inventoryManager) 
	{
		this.inventoryManager = inventoryManager;
		this.replenishmentRequests = ReplenishmentRequestLoader.loadReplenishmentRequests();
    }
	
	/**
     * Clears all replenishment requests from the list.
     * This method prints a confirmation message that the requests have been cleared.
     */

	// Method to clear the replenishment requests
	public void clearReplenishmentRequests() {
		replenishmentRequests.clear();
		System.out.println("Replenishment requests cleared.");
	}

	 /**
     * Displays the current inventory stock by delegating the task to the InventoryManager instance.
     */

	//display inventory stock
	public void displayStock() 
	{
		inventoryManager.displayStock();
	}
	
	/**
     * Displays all replenishment requests in the system.
     * It iterates through the replenishment requests list and prints each request's details.
     */

	public void displayReplenishmentRequests() 
	{
		for(ReplenishmentRequest request: replenishmentRequests)
		{
			System.out.println(request);
		}
	}

	public void removeStock() 
	{
		inventoryManager.removeStock();
	}



	/**
     * Displays detailed information for each replenishment request in the system.
     * This method calls the {@link ReplenishmentRequest#displayReplenishmentRequestDetails()} method 
     * on each replenishment request for a detailed display.
     */

	//display details of the replenishment requests
	public void displayReplenishmentRequestDetails()
	{
		for (ReplenishmentRequest request : replenishmentRequests) {
			request.displayReplenishmentRequestDetails(); // Call the new method
			System.out.println(); // For better readability
	}
	}

	/**
     * Displays pending replenishment requests submitted by the current user.
     * This method filters and shows only the requests that are still "New" and are raised by the given user.
     * 
     * @param requestedBy The name of the user who made the replenishment request.
     */

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

	/**
     * Filters and returns a list of pending replenishment requests that were submitted by a specific user.
     * 
     * @param requestedBy The name of the user whose pending requests are to be fetched.
     * @return A list of pending replenishment requests for the specified user.
     */

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

	 /**
     * Submits a new replenishment request for a specific medicine.
     * If a pending request for the same medicine already exists, the quantity is updated; otherwise, 
     * a new replenishment request is created and added to the list.
     * The request is also stored or updated in an external data source (e.g., an Excel file).
     * 
     * @param medicineName The name of the medicine for which the replenishment request is being made.
     * @param quantity The quantity of the medicine requested for replenishment.
     * @param requestedBy The name of the user submitting the replenishment request.
     */
	
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
	
	
