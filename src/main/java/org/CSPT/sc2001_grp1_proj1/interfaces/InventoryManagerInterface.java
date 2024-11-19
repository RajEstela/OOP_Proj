package org.CSPT.sc2001_grp1_proj1.interfaces;

public interface InventoryManagerInterface{
    void addMedicine();
    void removeMedicine();
    void addStock();
    void removeStock();
    void updateStockCount();
    void updateStockAlertLevel();
    void updateMedicine();
    void displayStock();
    void displayReplenishmentRequests();
    void approveReplenishmentRequests();

}