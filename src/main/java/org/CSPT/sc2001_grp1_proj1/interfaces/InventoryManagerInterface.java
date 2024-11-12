package org.CSPT.sc2001_grp1_proj1.interfaces;

public interface InventoryManagerInterface{
    void addStock();
    void removeStock();
    void updateStockCount();
    void updateStockAlertLevel();
    void displayStock();
    void displayReplenishmentRequests();
}