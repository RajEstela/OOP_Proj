package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Pharmacist;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicineDataLoader;

public class InventoryServiceTest {
    public static void main(String[] args) {
        // Setup
        List<Medicine> medicineList = MedicineDataLoader.inventoryReload(); // Initialize your medicine list
        LocalDateTime lastUpdated = LocalDateTime.now(); // Get the current time
        String systemUser  = "Admin"; // Example system user

        InventoryManager inventoryManager = new InventoryManager(medicineList, lastUpdated, systemUser);
        InventoryService inventoryService = new InventoryService(inventoryManager);
        Pharmacist pharmacist = new Pharmacist("P001", "John Doe", "Pharmacist", "Male", 30, inventoryService);


        // Test: Call the showMenu method
        pharmacist.showMenu();
        // Test: View Medication Inventory
        System.out.println("Viewing Medication Inventory:");
        pharmacist.displayStock(); // This should print the current medication inventory

        // Test: Submit Replenishment Request
        //String medicineName = "Aspirin"; // Example medicine
        //int quantity = 10; // Example quantity
        //pharmacist.submitReplenishmentRequest(medicineName, quantity); // This should submit the request
        // For testing submitReplenishmentRequest, you can call it directly
        pharmacist.submitReplenishmentRequest("Paracetamol", 100);

        // Verify: Check if the request has been added
        System.out.println("Current Replenishment Requests:");
         inventoryService.displayReplenishmentRequests(); // This should show the submitted request
    }
}
