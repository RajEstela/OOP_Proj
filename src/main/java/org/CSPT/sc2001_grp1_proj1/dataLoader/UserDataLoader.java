package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.CSPT.sc2001_grp1_proj1.HospitalManagementApp.refreshHashMaps;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserDataLoader {

    private static final String EXCEL_FILE_PATH = "./data/UserList.xlsx";

    public static void populateUsers(HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers, HashMap<String, Users> validUsersByID) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
            
                String userId = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String role = row.getCell(2).getStringCellValue();
                String gender = row.getCell(3).getStringCellValue();
                int age = (int) row.getCell(4).getNumericCellValue();
                String username = row.getCell(5).getStringCellValue();
                String pwd = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();
                int phoneNo = (int) row.getCell(8).getNumericCellValue();
                validUsersLogin.put(username, pwd);
                Users user = new Users(userId,name,role,gender,age,username,pwd,email,phoneNo);
                validUsers.put(username, user);
                validUsersByID.put(userId, user);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    public static HashMap<String, Users> populateValidUsers(HashMap<String, Users> validUsers) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
            
                String userId = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String role = row.getCell(2).getStringCellValue();
                String gender = row.getCell(3).getStringCellValue();
                int age = (int) row.getCell(4).getNumericCellValue();
                String username = row.getCell(5).getStringCellValue();
                String pwd = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();
                int phoneNo = (int) row.getCell(8).getNumericCellValue();
                Users user = new Users(userId,name,role,gender,age,username,pwd,email,phoneNo);
                validUsers.put(username, user);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return validUsers;
    }

    public static boolean resetPassword(String userName, HashMap<String, Users> validUsers,String newPwd) {
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

    public static void addUser(Users userToAdd) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0); 
            int lastRowNum = sheet.getLastRowNum(); 
            Row lastRow = sheet.getRow(lastRowNum);
            Row newRow = sheet.createRow(lastRowNum + 1);
    
            newRow.createCell(0).setCellValue(generateNextHospitalID(lastRow.getCell(0).getStringCellValue())); 
            newRow.createCell(1).setCellValue(userToAdd.getname());       
            newRow.createCell(2).setCellValue(userToAdd.getRole());       
            newRow.createCell(3).setCellValue(userToAdd.getGender());     
            newRow.createCell(4).setCellValue(userToAdd.getAge());        
            newRow.createCell(5).setCellValue(userToAdd.getUsername());   
            newRow.createCell(6).setCellValue(userToAdd.getPassword());   
            newRow.createCell(7).setCellValue(userToAdd.getEmail());      
            newRow.createCell(8).setCellValue(userToAdd.getPhoneNo());    
    
            try (FileOutputStream fos = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(fos);
            }
            refreshHashMaps();
            System.out.println("User added successfully!");
        } catch (IOException e) {
            System.err.println("Error while adding user: " + e.getMessage());
        }
    }

    public static void updateRole(String userName, String newRole) {
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
                    row.getCell(2).setCellValue(newRole);                                              
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    refreshHashMaps();  
                }
            }
        } catch (IOException e) {
        }
    }

    public static void updateStaff(HospitalStaff staff) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(5).getStringCellValue().equals(staff.gethospitalID())) {
                    row.getCell(1).setCellValue(staff.getname());       
                    row.getCell(2).setCellValue(staff.getrole());       
                    row.getCell(3).setCellValue(staff.getgender());     
                    row.getCell(4).setCellValue(staff.getAge());        
                    row.getCell(5).setCellValue(staff.getUsername());   
                    row.getCell(6).setCellValue(staff.getPassword());   
                    row.getCell(7).setCellValue(staff.getEmail());      
                    row.getCell(8).setCellValue(staff.getPhoneNo());                    
                                                                       
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    refreshHashMaps(); 
                }
            }
        } catch (IOException e) {
        }
    }

    public static void updateRole(String userName, HashMap<String, Users> validUsers, String newRole) {
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
                    row.getCell(2).setCellValue(newRole);                            
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    refreshHashMaps(); 
                }
            }
        } catch (IOException e) {
        }
    }

    public static void removeUser(String staffID) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
            Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (row.getCell(0).getStringCellValue().equals(staffID)) {
                    sheet.removeRow(row);                   
                    
                    try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                        workbook.write(outFile);
                    }
                    refreshHashMaps();                       
                }
            }
        } catch (IOException e) {
        }
    }

    public static String generateNextHospitalID(String prevID) {
        int numericPart = Integer.parseInt(prevID.substring(1)); 
        numericPart ++;
        return String.format("H%03d", numericPart);
    }

}
