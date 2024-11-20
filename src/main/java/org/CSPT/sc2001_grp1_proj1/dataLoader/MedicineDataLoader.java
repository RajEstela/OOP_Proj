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

/**
 * This class handles operations for loading, updating, and managing medicine inventory data
 * from an Excel file.
 */
public class MedicineDataLoader {
    /**
     * Path to the Excel file containing the medical inventory data.
     */
    private static final String EXCEL_FILE_PATH = "./data/MedicalInventory_List.xlsx";
    /**
     * Loads the medical inventory from the specified Excel file.
     *
     * @param excelFilePath the path to the Excel file.
     * @return an {@code InventoryManager} initialized with the medicines and the current timestamp.
     */
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
    /**
     * Updates the low stock level count and alert status for a specific medicine in the inventory.
     *
     * @param medicineToUpdate the {@code Medicine} object containing updated information.
     */
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
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while updating medicine: " + e.getMessage());
        }
    }
    /**
     * Updates the stock count and low stock alert status for a specific medicine in the inventory.
     *
     * @param medicineToUpdate the {@code Medicine} object containing updated information.
     */
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
                    row.getCell(3).setCellValue(medicineToUpdate.getMedicineStockCount());
                    row.getCell(4).setCellValue(medicineToUpdate.getLowStockLevelAlert());                
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while updating medicine: " + e.getMessage());
        }
    }
    /**
     * Adds a new medicine to the inventory.
     *
     * @param medicineToAdd the {@code Medicine} object to add.
     */
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
    /**
     * Removes a specific medicine from the inventory.
     *
     * @param medicineToRemove the {@code Medicine} object to remove.
     * @return {@code true} if the medicine was successfully removed; {@code false} otherwise.
     */
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
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while adding medicine: " + e.getMessage());
        }
        return found;
    }
    /**
     * Updates all details for a specific medicine in the inventory.
     *
     * @param medicineToUpdate the {@code Medicine} object containing updated information.
     */
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
                    break;
                }
            }
        } catch (IOException e) {
        }
    }
    /**
     * Generates the next medicine ID based on the previous ID.
     *
     * @param prevID the previous medicine ID.
     * @return the next medicine ID.
     */
    public static String generateNextMedsID(String prevID) {
        int numericPart = Integer.parseInt(prevID.substring(1)); 
        numericPart ++;
        return String.format("M%03d", numericPart);
    }
    /**
     * Reloads the inventory list from the Excel file.
     *
     * @return a {@code List} of {@code Medicine} objects.
     */
    public static List<Medicine> inventoryReload(){
        List<Medicine> medsList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
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
        return medsList;
    }
}
