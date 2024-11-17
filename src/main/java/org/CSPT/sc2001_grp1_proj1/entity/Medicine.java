package org.CSPT.sc2001_grp1_proj1.entity;

public class Medicine{
    protected String medicineID;
    protected String medicineName;
    protected String medicineDetail;
    protected int medicineStockCount;
    protected boolean lowStockLevelAlert;
    protected int lowStockLevelCount;

    public Medicine(String medicineID,String medicineName,String medicineDetail,int medicineStockCount,boolean lowStockLevelAlert,int lowStockLevelCount){
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

