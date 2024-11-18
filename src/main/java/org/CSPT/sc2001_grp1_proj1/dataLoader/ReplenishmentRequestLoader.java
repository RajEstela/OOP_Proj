package org.CSPT.sc2001_grp1_proj1.dataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryManager;
import org.CSPT.sc2001_grp1_proj1.entity.InventoryService;
import org.CSPT.sc2001_grp1_proj1.entity.Medicine;
import org.CSPT.sc2001_grp1_proj1.entity.ReplenishmentRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReplenishmentRequestLoader {
    private static final String EXCEL_FILE_PATH = "./data/ReplenishmentRequest_List.xlsx";

    public static List<ReplenishmentRequest> loadReplenishmentRequests() {
        List<ReplenishmentRequest> requestList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String requestID = row.getCell(0).getStringCellValue();
                String medicineName = row.getCell(1).getStringCellValue();
                int quantity = (int) row.getCell(2).getNumericCellValue();
                String requestedBy = row.getCell(3).getStringCellValue();
                String status = row.getCell(4).getStringCellValue();

                ReplenishmentRequest request = new ReplenishmentRequest(medicineName, quantity, requestedBy, status);
                requestList.add(request);
            }
        } catch (IOException e) {
            System.err.println("Error loading replenishment requests: " + e.getMessage());
        }

        return requestList;
    }

    public static void addReplenishmentRequest(ReplenishmentRequest newRequest) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            // Retrieve the last request ID to generate the next one
            String lastRequestID = null;
            if (lastRowNum > 0) {
                Row lastRow = sheet.getRow(lastRowNum);
                lastRequestID = lastRow.getCell(0).getStringCellValue();
            }

            // Generate the next request ID using the last ID
            String newRequestID = generateNextRequestID(lastRequestID);
            newRequest.setRequestID(newRequestID); // Set the generated requestID



            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(newRequest.getRequestID());
            newRow.createCell(1).setCellValue(newRequest.getMedicineName());
            newRow.createCell(2).setCellValue(newRequest.getQuantity());
            newRow.createCell(3).setCellValue(newRequest.getRequestedBy());
            newRow.createCell(4).setCellValue(newRequest.getStatus());

            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }

            System.out.println("Replenishment request added successfully!");
        } catch (IOException e) {
            System.err.println("Error adding replenishment request: " + e.getMessage());
        }
    }

    public static void updateReplenishmentRequest(ReplenishmentRequest updateRequest)
    {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(0).getStringCellValue().equals(updateRequest.getRequestID())) {
                    row.getCell(1).setCellValue(updateRequest.getMedicineName());
                    row.getCell(2).setCellValue(updateRequest.getQuantity());
                    row.getCell(3).setCellValue(updateRequest.getRequestedBy());
                    row.getCell(4).setCellValue(updateRequest.getStatus());

                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating replenishment request: " + e.getMessage());
        }
    }

    public static String generateNextRequestID(String prevID) {
        if (prevID == null || prevID.isEmpty()) {
            return "R001"; // Start from R001 if no previous ID exists
        }
        int numericPart = Integer.parseInt(prevID.substring(1));
        numericPart++;
        return String.format("R%03d", numericPart);
    }
}
