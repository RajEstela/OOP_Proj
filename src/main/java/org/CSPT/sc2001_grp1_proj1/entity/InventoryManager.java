package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.interfaces.InventoryManagerInterface;

public class InventoryManager implements InventoryManagerInterface {

    protected List<Medicine> totalMedicineInventoryList;
    //protected List<ReplenishmentRequests> replenishmentRequests;
    protected LocalDateTime lastUpdatedTime;
    protected String lastUpdatedBy; 

    public InventoryManager(List<Medicine> totalMedicineInventoryList, LocalDateTime lastUpdatedTime, String system) {
        this.totalMedicineInventoryList = totalMedicineInventoryList;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = system;
    }

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
            scanner.close();
            addStock();
        }          
    }
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
                    System.out.println("\nUpdated Successfully\n");
                    break;
                }
            }
    
            if (!medicineFound) {
                System.out.println("\nInvalid Medicine Name! Please try again.\n");
            }
        }
    }
    

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
                    System.out.println("\nUpdated Successfully\n");
                    break;
                }
            }
    
            if (!medicineFound) {
                System.out.println("\nInvalid Medicine Name! Please try again.\n");
            }
        }
    }
    

    @Override
    public void displayStock() {
        for (Medicine meds : this.totalMedicineInventoryList) {
            String stockAlert = "Normal";
            if(meds.lowStockLevelAlert)
                stockAlert = "Low";           
            String printStaff = String.format("\n---------------\nMedicine Name: %s\n Medicine Stock Count: %d\n Low Stock Level: %s\n---------------\n",meds.medicineName,meds.medicineStockCount,stockAlert);
            System.out.printf(printStaff);
        }
    }
    @Override
    public void displayReplenishmentRequests() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayReplenishmentRequests'");
    }


}
