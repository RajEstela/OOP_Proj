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
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
