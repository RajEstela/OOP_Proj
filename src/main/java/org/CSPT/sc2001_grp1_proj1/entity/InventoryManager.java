package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicineDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.ReplenishmentRequestLoader;
import org.CSPT.sc2001_grp1_proj1.interfaces.InventoryManagerInterface;

/**
 * This class is responsible for managing the inventory of medicines, including adding/removing stock,
 * updating stock count and alert levels, displaying inventory, handling replenishment requests, and managing medicine details.
 * It implements the {@link InventoryManagerInterface} interface to ensure required methods are included.
 */

public class InventoryManager implements InventoryManagerInterface {

    protected List<Medicine> totalMedicineInventoryList;
    protected List<ReplenishmentRequest> replenishmentRequests;
    protected LocalDateTime lastUpdatedTime;
    protected String lastUpdatedBy; 

    /**
     * Constructor for the InventoryManager class.
     * 
     * @param totalMedicineInventoryList List of all medicines in the inventory.
     * @param lastUpdatedTime Timestamp when the inventory was last updated.
     * @param system The name of the system or user that last updated the inventory.
     */

    public InventoryManager(List<Medicine> totalMedicineInventoryList, LocalDateTime lastUpdatedTime, String system) {
        this.totalMedicineInventoryList = totalMedicineInventoryList;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = system;
    }
    
    /**
     * Initializes the replenishment request list by loading from a data source.
     */

    public void replenishmentRequestInit(){
        this.replenishmentRequests = ReplenishmentRequestLoader.loadReplenishmentRequests();
    }

    /**
     * Adds stock to an existing medicine in the inventory.
     * Prompts the user to enter a medicine name, finds the corresponding medicine in the inventory,
     * and increases the stock count by 1.
     * Displays a success or error message based on the input.
     */

    @Override
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        boolean medicineFound = false;
        System.out.printf
        (
            "\nEnter Medicine Name: "
        );
        String medsNameToUpdate = scanner.nextLine();

        for (Medicine meds : this.totalMedicineInventoryList) {
            if(meds.medicineName == null ? medsNameToUpdate == null : meds.medicineName.equals(medsNameToUpdate))
            {
                medicineFound = true;  
                meds.medicineStockCount++;
                meds.lowStockLevelAlert = meds.medicineStockCount < meds.lowStockLevelCount;
                MedicineDataLoader.updateStockCount(meds);
                inventoryReload();
                System.out.printf
                (
                    "\nAdded Successfully\n"
                );
                break;
            }
        }

        if(!medicineFound)
        {
            System.out.printf
            (
                "\nInvalid Medicine Name!\n"
            );
            addStock();
        }          
    }

    /**
     * Removes stock from an existing medicine in the inventory.
     * Prompts the user to enter a medicine name, finds the corresponding medicine in the inventory,
     * and decreases the stock count by 1.
     * Displays a success or error message based on the input.
     */

    @Override
    public void removeStock() {
        // TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        boolean medicineFound = false;
        System.out.printf
        (
            "\nEnter Medicine Name: "
        );
        String medsNameToUpdate = scanner.nextLine();

        for (Medicine meds : this.totalMedicineInventoryList) {
            if(meds.medicineName == null ? medsNameToUpdate == null : meds.medicineName.equals(medsNameToUpdate))
            {
                medicineFound = true;  
                meds.medicineStockCount--;
                meds.lowStockLevelAlert = meds.medicineStockCount < meds.lowStockLevelCount;
                MedicineDataLoader.updateStockCount(meds);
                inventoryReload();
                System.out.printf
                (
                    "\nRemoved Successfully\n"
                );
                break;
            }
        }

        if(!medicineFound)
        {
            System.out.printf
            (
                "\nInvalid Medicine Name!\n"
            );
            scanner.close();
            removeStock();
        }      
    }

    /**
     * Updates the stock count for a specific medicine.
     * Prompts the user to enter a medicine name and the number of stocks to add.
     * The input must be a positive integer; otherwise, an error message is displayed.
     */

    @Override
    public void updateStockCount() {
        Scanner scanner = new Scanner(System.in);
        boolean medicineFound = false;
    
        while (!medicineFound) {
            System.out.print("\nEnter Medicine Name To Update: ");
            String medsNameToUpdate = scanner.nextLine();
    
            for (Medicine meds : this.totalMedicineInventoryList) {
                if ((meds.medicineName == null && medsNameToUpdate == null) ||
                    (meds.medicineName != null && meds.medicineName.equals(medsNameToUpdate))) {
                    
                    medicineFound = true;
                    int addStockCount = -1;
    
                    while (true) {
                        try {
                            System.out.print("\nEnter number of stocks to add: ");
                            addStockCount = scanner.nextInt();
                            if (addStockCount < 0) {
                                throw new IllegalArgumentException("Input should not be below 0.");
                            }
                            break; 
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            scanner.nextLine(); 
                        }
                    }  
                    meds.medicineStockCount += addStockCount;
                    meds.lowStockLevelAlert = meds.medicineStockCount < meds.lowStockLevelCount;
                    MedicineDataLoader.updateStockCount(meds);
                    inventoryReload();
                    System.out.println("\nUpdated Successfully\n");
                    break;
                }
            }
    
            if (!medicineFound) {
                System.out.println("\nInvalid Medicine Name! Please try again.\n");
            }
        }
    }
    
    /**
     * Updates the low stock alert level for a specific medicine.
     * Prompts the user to enter a medicine name and a new stock alert level.
     * The input must be a positive integer; otherwise, an error message is displayed.
     */

    @Override
    public void updateStockAlertLevel() {
        Scanner scanner = new Scanner(System.in);
        boolean medicineFound = false;
    
        while (!medicineFound) {
            System.out.print("\nEnter Medicine Name To Update: ");
            String medsNameToUpdate = scanner.nextLine();
    
            for (Medicine meds : this.totalMedicineInventoryList) {
                if ((meds.medicineName == null && medsNameToUpdate == null) ||
                    (meds.medicineName != null && meds.medicineName.equals(medsNameToUpdate))) {
                    
                    medicineFound = true;
                    int newAlertLevel = -1;
    
                    while (true) {
                        try {
                            System.out.print("\nEnter new stock alert level: ");
                            newAlertLevel = scanner.nextInt();
                            if (newAlertLevel < 0) {
                                throw new IllegalArgumentException("Input should not be below 0.");
                            }
                            break; 
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            scanner.nextLine(); 
                        }
                    }
                    meds.lowStockLevelCount = newAlertLevel;
                    meds.lowStockLevelAlert = meds.medicineStockCount < meds.lowStockLevelCount;
                    MedicineDataLoader.updateLowLevelStockCount(meds);
                    inventoryReload();
                    System.out.println("\nUpdated Successfully\n");
                    break;
                }
            }
    
            if (!medicineFound) {
                System.out.println("\nInvalid Medicine Name! Please try again.\n");
            }
        }
    }
    
    /**
     * Displays the current stock of all medicines in the inventory.
     * The displayed information includes the medicine name, stock count, low stock alert, and alert level.
     */

    @Override
    public void displayStock() {
        System.out.printf("\n%-20s %-20s %-20s %-20s%n", "Medicine Name", "Stock Count", "Low Stock Alert","Low Stock Level");
        System.out.println("-------------------------------------------------------------");

        for (Medicine medicine : this.totalMedicineInventoryList) {
            System.out.printf("%-20s %-20d %-20s %-20s%n", 
                                medicine.medicineName, 
                                medicine.medicineStockCount,
                                medicine.lowStockLevelAlert, 
                                medicine.lowStockLevelCount);
        }
    }

    /**
     * Displays all the replenishment requests that are pending.
     * The displayed information includes request ID, medicine name, quantity, requested by, and request status.
     */

    @Override
    public void displayReplenishmentRequests() {
        System.out.printf("\n%-20s %-20s %-20s %-20s%n", "Request ID", "Medicine Name", "Quantity", "Requested By", "Status");
        System.out.println("-------------------------------------------------------------");

        for (ReplenishmentRequest reps : this.replenishmentRequests) {
            System.out.printf("%-20s %-20s %-20s %-20s%n", 
                                reps.medicineName, 
                                reps.quantity,
                                reps.requestedBy, 
                                reps.status);
        }
    }

    /**
     * Approves a replenishment request for a medicine.
     * Prompts the user to enter the name of the medicine whose replenishment request is to be approved.
     * Once approved, the stock for the corresponding medicine is updated and the request status is marked as "Approved".
     */

    @Override
    public void approveReplenishmentRequests() {
        Scanner scanner = new Scanner(System.in);
        boolean req = false;
    
        while (!req) {
            System.out.print("\nEnter Replenishment Request's Medicine Name To Approve: ");
            String reqMedName = scanner.nextLine();
    
            for (ReplenishmentRequest reps : this.replenishmentRequests) {
                if ((reps.medicineName == null && reqMedName == null) ||
                    (reps.medicineName != null && reps.medicineName.equals(reqMedName))) {
                    
                    req = true;  
                    reps.status = "Approved";                     
                    ReplenishmentRequestLoader.updateReplenishmentRequest(reps);
                    replenishmentRequestInit();

                    for (Medicine meds : this.totalMedicineInventoryList) {
                        if (meds.medicineName.equals(reps.medicineName)) {
                                        
                            meds.medicineStockCount += reps.quantity;
                            MedicineDataLoader.updateStockCount(meds);
                            inventoryReload();
                            break;
                        }
                    }

                    System.out.println("\nUpdated Successfully\n");
                    break;
                }
            }
    
            if (!req) {
                System.out.println("\nInvalid Request ID! Please try again.\n");
            }
        }
    }

    /**
     * Adds a new medicine to the inventory.
     * Prompts the user to enter the medicine name, details, stock count, and low stock alert level.
     * Performs validation for non-negative stock values and ensures the alert level is not greater than the stock count.
     */

    @Override
    public void addMedicine() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\nEnter Medicine Name: ");
        String medicineName = scanner.nextLine();

        System.out.printf("\nEnter Medicine Detail: ");
        String medicineDetail = scanner.nextLine();
        int medicineStockCount = 0;
        int medicineAlertLevelCount =0;
        while (true) {
            try {
                System.out.printf("\nEnter Medicine Stock Count: ");
                medicineStockCount = Integer.parseInt(scanner.nextLine());
        
                System.out.printf("\nEnter Medicine Low Stock Alert Level Count: ");
                medicineAlertLevelCount  = Integer.parseInt(scanner.nextLine());                

                if (medicineStockCount < 0 || medicineAlertLevelCount < 0) {
                    throw new IllegalArgumentException("Input should not be below 0.");
                }
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        }

        boolean lowAlert = false;

        if(medicineAlertLevelCount > medicineStockCount){
            lowAlert = true;            
        }

        Medicine newMeds = new Medicine("x",medicineName,medicineDetail, medicineStockCount,lowAlert,medicineAlertLevelCount);
        MedicineDataLoader.addMedicine(newMeds);
        inventoryReload();

    }

    /**
     * Updates the details of an existing medicine.
     * Prompts the user to select which detail (medicine name, detail, stock count, or alert level) they want to update.
     * Allows canceling the update if the user chooses to do so.
     */

    @Override
    public void updateMedicine() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf
        (
            "\nEnter Medicine Name To Update:"
        );
        String medsNameToUpdate = scanner.nextLine();
        Medicine medsToUpdate = null;

        for (var meds : this.totalMedicineInventoryList) {
            if(meds.medicineName.equals(medsNameToUpdate))
            {
                medsToUpdate = meds;
                break;
            }
        }

        if (medsToUpdate!=null)
        {
            boolean updatingMeds = true;
            while(updatingMeds)
            {
                System.out.printf
                (
                    "\nWhat would you like to update?\n1 Medicine Name\n2 Medicine Detail\n3 Medicine Stock Count\n4 Medicine Low Stock Alert Level\n5 Cancel\nEnter Choice: "
                );
                int actionStaff = scanner.nextInt();
                scanner.nextLine();
                switch (actionStaff) {
                    case 1 -> {
                        System.out.printf
                        (
                            "\nEnter new Medicine Name: "
                        );
                        medsToUpdate.medicineName = scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.printf
                        (
                            "\nEnter new Medicine Detail: "
                        );            
                        medsToUpdate.medicineDetail = scanner.nextLine();
                    }
                    case 3 -> {
                        int stock = 0;
                        while (true) {
                            try {
                                System.out.printf
                                (
                                    "\nEnter new Medicine Stock Count: "
                                );            
                                stock = scanner.nextInt();
                                if (stock < 0) {
                                    throw new IllegalArgumentException("\nInput should not be below 0.");
                                }
                                break; 
                            } catch (IllegalArgumentException e) {
                                System.out.println("\nError: " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("\nInvalid input. Please enter a valid integer.");
                                scanner.nextLine(); 
                            }
                        }       
                        medsToUpdate.medicineStockCount = stock;
                        scanner.nextLine();
                    }
                    case 4 -> {
                        int stock = 0;
                        while (true) {
                            try {
                                System.out.printf
                                (
                                    "\nEnter new Medicine Low Stock Alert Level: "
                                );            
                                stock = scanner.nextInt();
                                if (stock < 0) {
                                    throw new IllegalArgumentException("\nInput should not be below 0.");
                                }
                                break; 
                            } catch (IllegalArgumentException e) {
                                System.out.println("\nError: " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("\nInvalid input. Please enter a valid integer.");
                                scanner.nextLine(); 
                            }
                        }        
                        medsToUpdate.lowStockLevelCount = stock; 
                        scanner.nextLine();
                    }
                    case 5 ->{
                        MedicineDataLoader.updateMedicine(medsToUpdate);  
                        inventoryReload();           
                        System.out.printf
                        (
                            "\nCancelled "
                        );   
                        updatingMeds = false;
                        break;          
                    }
                    default -> {
                        System.out.printf
                        (
                            "\nInvalid Selection "
                        );        
                    }
                } 
            } 
        }
        else{
            System.out.printf
            (
                "\nMedicine Not Found"
            );   
        }    
    }

    /**
     * Removes an existing medicine from the inventory.
     * Prompts the user to enter the medicine name and removes the corresponding medicine from the inventory.
     */

    @Override
    public void removeMedicine() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf
        (
            "\nEnter Medicine Name To Remove:"
        );
        String medsNameToUpdate = scanner.nextLine();
        Medicine medsToUpdate = null;

        for (var meds : this.totalMedicineInventoryList) {
            if(meds.medicineName.equals(medsNameToUpdate))
            {
                medsToUpdate = meds;
                break;
            }
        }   
        if(!MedicineDataLoader.removeMedicine(medsToUpdate)){
            System.out.printf
            (
                "\nError, Medicine unable to remove. Please try again later"
            );            
        }
        inventoryReload();
    }
    
    private void inventoryReload(){
        this.totalMedicineInventoryList = MedicineDataLoader.inventoryReload();
    }



}
