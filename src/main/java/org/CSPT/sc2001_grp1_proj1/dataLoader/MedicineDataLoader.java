package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.Medicine;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MedicineDataLoader {

    private static final String EXCEL_FILE_PATH = "./data/MedicalInventory_List.xlsx";

    public static InventoryManager loadMedicalInventory(String excelFilePath) {
        List<Medicine> medsList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

               String medicineID =row.getCell(0).getStringCellValue();
               String medicineName =row.getCell(1).getStringCellValue();
               String medicineDetail =row.getCell(2).getStringCellValue();
               int medicineStockCount = (int) row.getCell(3).getNumericCellValue();
               boolean lowStockLevelAlert = row.getCell(4).getBooleanCellValue();              
               int lowStockLevelCount = (int) row.getCell(5).getNumericCellValue();

                Medicine meds = new Medicine(medicineID,medicineName,medicineDetail,medicineStockCount,lowStockLevelAlert,lowStockLevelCount);
                medsList.add(meds);
            }
        } catch (IOException e) {
        }
        InventoryManager medsInit = new InventoryManager(medsList, LocalDateTime.now(), "SYSTEM");
        return medsInit;
    }

    public static void updateLowLevelStockCount(Medicine medicineToUpdate) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(1).getStringCellValue().equals(medicineToUpdate.getMedicineName())) {
                    row.getCell(5).setCellValue(medicineToUpdate.getLowStockLevelCount()); 
                    row.getCell(4).setCellValue(medicineToUpdate.getLowStockLevelAlert());                
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while updating medicine: " + e.getMessage());
        }
    }
    public static void updateStockCount(Medicine medicineToUpdate) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(1).getStringCellValue().equals(medicineToUpdate.getMedicineName())) {
                    row.getCell(3).setCellValue(medicineToUpdate.getLowStockLevelCount());
                    row.getCell(4).setCellValue(medicineToUpdate.getLowStockLevelAlert());                
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while updating medicine: " + e.getMessage());
        }
    }
    public static void addMedicine(Medicine medicineToAdd) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0); 
            int lastRowNum = sheet.getLastRowNum(); 
            Row lastRow = sheet.getRow(lastRowNum);
            Row newRow = sheet.createRow(lastRowNum + 1);
    
            newRow.createCell(0).setCellValue(generateNextMedsID(lastRow.getCell(0).getStringCellValue())); 
            newRow.createCell(1).setCellValue(medicineToAdd.medicineName);       
            newRow.createCell(2).setCellValue(medicineToAdd.medicineDetail);       
            newRow.createCell(3).setCellValue(medicineToAdd.medicineStockCount);     
            newRow.createCell(4).setCellValue(medicineToAdd.lowStockLevelAlert);        
            newRow.createCell(5).setCellValue(medicineToAdd.lowStockLevelCount);   

    
            try (FileOutputStream fos = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(fos);
            }
            System.out.println("Medicine added successfully!");
        } catch (IOException e) {
            System.err.println("Error while adding medicine: " + e.getMessage());
        }
    }
    public static boolean removeMedicine(Medicine medicineToRemove) {
        boolean found = false;
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(1).getStringCellValue().equals(medicineToRemove.medicineName)) {
                    sheet.removeRow(row);                   
                    found = true;      
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    System.out.println("Medicine removed successfully!");
                }
            }
        } catch (IOException e) {
            System.err.println("Error while adding medicine: " + e.getMessage());
        }
        return found;
    }
    public static void updateMedicine(Medicine medicineToUpdate) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(5).getStringCellValue().equals(medicineToUpdate.medicineID)) {
                    row.getCell(0).setCellValue(medicineToUpdate.medicineName); 
                    row.getCell(1).setCellValue(medicineToUpdate.medicineDetail);       
                    row.getCell(2).setCellValue(medicineToUpdate.medicineStockCount);       
                    row.getCell(3).setCellValue(medicineToUpdate.lowStockLevelAlert);     
                    row.getCell(4).setCellValue(medicineToUpdate.lowStockLevelCount);                             

                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
    public static String generateNextMedsID(String prevID) {
        int numericPart = Integer.parseInt(prevID.substring(1)); 
        numericPart ++;
        return String.format("M%03d", numericPart);
    }
}
