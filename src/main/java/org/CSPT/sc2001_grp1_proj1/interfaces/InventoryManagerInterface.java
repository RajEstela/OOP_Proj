package org.CSPT.sc2001_grp1_proj1.interfaces;

/**
 * Interface for managing inventory in the hospital.
 * Provides methods for adding, removing, updating, and displaying stock and medicine details.
 */
public interface InventoryManagerInterface {

    /**
     * Adds a new medicine to the inventory.
     */
    void addMedicine();

    /**
     * Removes a medicine from the inventory.
     */
    void removeMedicine();

    /**
     * Adds stock to an existing medicine in the inventory.
     */
    void addStock();

    /**
     * Removes stock from an existing medicine in the inventory.
     */
    void removeStock();

    /**
     * Updates the stock count of an existing medicine.
     */
    void updateStockCount();

    /**
     * Updates the alert level for stock replenishment.
     */
    void updateStockAlertLevel();

    /**
     * Updates the details of an existing medicine in the inventory.
     */
    void updateMedicine();

    /**
     * Displays the current inventory stock details.
     */
    void displayStock();

    /**
     * Displays replenishment requests for inventory.
     */
    void displayReplenishmentRequests();

    /**
     * Approves replenishment requests for inventory.
     */
    void approveReplenishmentRequests();
}