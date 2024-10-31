/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author RajEs
 */
public class Admin {

    public static void main() {
        HospitalStaffManager initHospitalStaffManager = null;
        var hospitalStaff = hospitalStaffInit();
        if ( hospitalStaff != null)
        {
            initHospitalStaffManager = hospitalStaff;
        }

        boolean loggedIn = true;
        while(loggedIn){
            switch (administratorMenu()) {
                case 1 -> {
                    hospitalStaff(initHospitalStaffManager);
                }
                case 2 -> {
                    appointmentDetails();
                }
                case 3 -> {
                    medicationInventory();
                }
                case 4 -> {
                    approveRepReq();
                }
                case 5 -> {
                    logout();
                    System.out.printf
                    (
                    "Bye!"
                    );
                    loggedIn = false;

                    break;
                }
            }
        }
        
    }

    private static int administratorMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf
        (
            "\n 1 View and Manage Hospital Staff\n 2 View Appointments details\n 3 View and Manage Medication Inventory\n 4 Approve Replenishment Requests\n 5 Logout\n"
        );
        return scanner.nextInt();
    }

    private static void hospitalStaff(HospitalStaffManager staff) {
        Scanner scanner = new Scanner(System.in);
        boolean inStaffMenu = true;
        while (inStaffMenu)
        {
            System.out.printf
            (
                "\n 1 View Staff\n 2 Add Staff\n 3 Remove Staff\n 4 Update Staff\n 5 Administrator Main Menu\n"
            );
            switch (scanner.nextInt()) {
                case 1 -> {
                    staff.displayStaff();
                }
                case 2 -> {
                    staff.addStaffMember();
                }
                case 3 -> {
                    staff.removeStaffMember();
                }
                case 4 -> {
                    staff.updateStaffMember();
                }
                default -> {
                    System.out.printf
                    (
                        "Back\n"
                    );                    
                    inStaffMenu = false;
                }
            }    
        }
        
    }

    private static void appointmentDetails() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void medicationInventory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void approveRepReq() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static HospitalStaffManager hospitalStaffInit() {
        //Hospital Staff initialization
        HospitalStaffManager hospitalStaffManager = null;
        String excelFilePath = "./data/Staff_List.xlsx";  // Replace with your actual file path
        List<HospitalStaff> staffList = new ArrayList<>(); // List to store HospitalStaff objects

        try (FileInputStream file = new FileInputStream(new File(excelFilePath)); // Create Workbook instance holding reference to .xlsx file
                Workbook workbook = new XSSFWorkbook(file)) {

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through each row, skipping the header row
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                // Read each cell value for the columns in the row
                String staffId = row.getCell(0).getStringCellValue();
                String role = row.getCell(2).getStringCellValue();
                String gender = row.getCell(3).getStringCellValue();
                int age = (int) row.getCell(4).getNumericCellValue(); // Cast age to int

                // Create a new HospitalStaff object and add it to the list
                HospitalStaff staff = new HospitalStaff(staffId, role, gender, age);
                staffList.add(staff);
            }
            // Close workbook           

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!staffList.isEmpty())
        {
            Date getDate = java.sql.Date.valueOf(LocalDate.now());
            hospitalStaffManager = new HospitalStaffManager(staffList,getDate,"SYSTEM");
            return hospitalStaffManager;
        } 
        return hospitalStaffManager;

    }


}
