package org.CSPT.sc2001_grp1_proj1.entity;

public class Medicine{
    public String medicineID;
    public String medicineName;
    public String medicineDetail;
    public int medicineStockCount;
    public boolean lowStockLevelAlert;
    public int lowStockLevelCount;

    public Medicine(String medicineID,String medicineName,String medicineDetail,int medicineStockCount,boolean lowStockLevelAlert,int lowStockLevelCount){
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.medicineDetail = medicineDetail;
        this.medicineStockCount = medicineStockCount;
        this.lowStockLevelAlert = lowStockLevelAlert;
        this.lowStockLevelCount = lowStockLevelCount;
    }

    public String viewMedicineDetails(){
        return this.medicineDetail;
    }

    public String getMedicineName(){
        return this.medicineName;
    }

    public int getMedicineStockCount(){
        return this.medicineStockCount;
    }

    public boolean getLowStockLevelAlert(){
        return this.lowStockLevelAlert;
    }

    public int getLowStockLevelCount(){
        return this.lowStockLevelCount;
    }
}

