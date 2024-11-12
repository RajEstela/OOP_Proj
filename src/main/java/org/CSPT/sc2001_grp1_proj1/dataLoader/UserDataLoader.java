package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.entity.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserDataLoader {

    private static final String EXCEL_FILE_PATH = "./data/UserList.xlsx";

    public static void populateUsers(HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String staffId = row.getCell(0).getStringCellValue();
                String role = row.getCell(2).getStringCellValue();
                String gender = row.getCell(3).getStringCellValue();
                int age = (int) row.getCell(4).getNumericCellValue();
                String username = row.getCell(5).getStringCellValue();
                String pwd = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();
                int phoneNo = (int) row.getCell(8).getNumericCellValue();

                validUsersLogin.put(username, pwd);
                Users user = new Users(staffId, username, pwd, email, gender, age, phoneNo, role);
                validUsers.put(username, user);
            }
        } catch (IOException e) {
        }
    }

    // Modify the resetPassword method to accept Scanner as a parameter
    public static boolean resetPassword(String userName, HashMap<String, Users> validUsers, Scanner scanner) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(5).getStringCellValue().equals(userName)) {
                    System.out.println("Enter New Password:");
                    String newPwd = scanner.nextLine();
                    row.getCell(6).setCellValue(newPwd);
                    validUsers.get(userName).setPassword(newPwd);
                    
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

}
