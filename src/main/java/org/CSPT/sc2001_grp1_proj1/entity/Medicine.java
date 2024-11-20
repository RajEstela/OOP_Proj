package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Represents a medicine with details such as its ID, name, stock count, and low stock level alert.
 */

public class Medicine{
   /** The unique ID of the medicine. */
   public String medicineID;

   /** The name of the medicine. */
   public String medicineName;

   /** A detailed description of the medicine. */
   public String medicineDetail;

   /** The current stock count of the medicine. */
   public int medicineStockCount;

   /** A flag indicating whether the medicine has a low stock alert. */
   public boolean lowStockLevelAlert;

   /** The stock level count that triggers a low stock alert. */
   public int lowStockLevelCount;

   /**
    * Constructs a new Medicine object with the specified details.
    * 
    * @param medicineID The unique ID of the medicine.
    * @param medicineName The name of the medicine.
    * @param medicineDetail The detailed description of the medicine.
    * @param medicineStockCount The current stock count of the medicine.
    * @param lowStockLevelAlert A flag indicating if there is a low stock alert.
    * @param lowStockLevelCount The stock level threshold for triggering a low stock alert.
    */

    public Medicine(String medicineID,String medicineName,String medicineDetail,int medicineStockCount,boolean lowStockLevelAlert,int lowStockLevelCount){
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.medicineDetail = medicineDetail;
        this.medicineStockCount = medicineStockCount;
        this.lowStockLevelAlert = lowStockLevelAlert;
        this.lowStockLevelCount = lowStockLevelCount;
    }

    /**
     * Returns the details of the medicine.
     * 
     * @return A string containing the detailed description of the medicine.
     */
    public String viewMedicineDetails() {
        return this.medicineDetail;
    }

    /**
     * Returns the name of the medicine.
     * 
     * @return The name of the medicine.
     */
    public String getMedicineName() {
        return this.medicineName;
    }

    /**
     * Returns the current stock count of the medicine.
     * 
     * @return The current stock count of the medicine.
     */
    public int getMedicineStockCount() {
        return this.medicineStockCount;
    }

    /**
     * Returns whether the low stock level alert is activated for the medicine.
     * 
     * @return true if the low stock level alert is enabled, false otherwise.
     */
    public boolean getLowStockLevelAlert() {
        return this.lowStockLevelAlert;
    }

    /**
     * Returns the stock level threshold that triggers the low stock alert.
     * 
     * @return The stock level threshold for triggering a low stock alert.
     */
    public int getLowStockLevelCount() {
        return this.lowStockLevelCount;
    }
}

