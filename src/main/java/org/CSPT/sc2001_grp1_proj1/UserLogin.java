/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.CSPT.sc2001_grp1_proj1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.FunctionRoles.Admin;
import org.CSPT.sc2001_grp1_proj1.entity.RolesEnum;
import org.CSPT.sc2001_grp1_proj1.entity.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author RajEs
 */

public class UserLogin {
    public static HashMap<String, String> validUsersLogin = new HashMap<>();
    public static HashMap<String, Users> validUsers = new HashMap<>();   
    public static void main(String[] args) {
        populateUsers(validUsersLogin, validUsers);

        while (true) { 
            Scanner scanner = new Scanner(System.in);
            System.out.printf
            (
                "\n Login\n Username:\n"
            );
            String userName = scanner.nextLine();
            System.out.printf
            (
                "\n Password:\n"
            );
            String pwd = scanner.nextLine();


            Users userLogin = login(userName,pwd,validUsersLogin,validUsers); 

            if (userLogin != null) 
            {
                System.out.printf("\nLogin Successfully\n");
                try {
                    RolesEnum roleEnum = RolesEnum.valueOf(userLogin.role);

                    switch (roleEnum) {
                        case Doctor:
                            System.out.println("This person is a Doctor.");
                            break;
                        case Administrator:
                            System.out.printf("\nWelcome %s\n",userLogin.username);
                            Admin.main();                            
                        break;
                        case Pharmacists:
                            System.out.println("This person is a Pharmacist.");
                            break;
                        case Patient:
                            System.out.println("This person is a Patient.");
                            break;
                        case SecurityGuard:
                            System.out.println("This person is a Security Guard.");
                            break;
                        case Janitor:
                            System.out.println("This person is a Janitor.");
                            break;
                        case Nurse:
                            System.out.println("This person is a Nurse.");
                            break;
                        default:
                            throw new AssertionError("Unknown role: " + roleEnum);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Login Failed");
                }
            }
            else{
                System.out.println("Login Failed");
            }
        }       
    }

    private static void populateUsers(HashMap<String, String> validUsersLogin,HashMap<String, Users> validUsers){
        String excelFilePath = "./data/UserList.xlsx";  

        try (FileInputStream file = new FileInputStream(new File(excelFilePath)); 
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

                validUsersLogin.put(username,pwd);

                Users user = new Users(staffId, username, pwd,  email,  gender,  age,  phoneNo, role);
                validUsers.put(username,user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }   


    private static Users login(String userName, String pwd, HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers) {
        if (validUsersLogin.containsKey(userName) && validUsersLogin.get(userName).equals(pwd)) {
            return validUsers.get(userName); 
        }
        return null; 
    }
    

}
