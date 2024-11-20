package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.CSPT.sc2001_grp1_proj1.HospitalManagementApp.refreshHashMaps;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.CSPT.sc2001_grp1_proj1.entity.Users;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The {@code StaffDataLoader} class is responsible for loading hospital staff data 
 * from an Excel file into a {@code HospitalStaffManager} object.
 */
public class StaffDataLoader {
    /**
     * Path to the Excel file containing the user list data.
     */
    private static final String EXCEL_FILE_PATH = "./data/UserList.xlsx";

        /**
     * Loads hospital staff data from an Excel file, filtering out patients and pending users.
     * Associates staff with valid user data from the provided {@code validUsers} map.
     *
     * @param validUsers a {@code HashMap} containing valid user data with usernames as keys.
     * @return a {@code HospitalStaffManager} object initialized with the loaded staff data.
     */
    public static HospitalStaffManager loadHospitalStaff(HashMap<String, Users> validUsers) {
        List<HospitalStaff> staffList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; 
                }
                if(!row.getCell(2).getStringCellValue().equals("Patient") && !row.getCell(2).getStringCellValue().equals("Pending"))
                {
                    refreshHashMaps();
                    String staffUserName = row.getCell(5).getStringCellValue();
                    HospitalStaff staff = new HospitalStaff(
                        validUsers.get(staffUserName).gethospitalID(), 
                        validUsers.get(staffUserName).getname(), 
                        validUsers.get(staffUserName).getRole(), 
                        validUsers.get(staffUserName).getGender(), 
                        validUsers.get(staffUserName).getAge());
                    staffList.add(staff);
                }

            }
        } catch (IOException e) {
        }
        HospitalStaffManager staffInit = new HospitalStaffManager(staffList, LocalDateTime.now(), "SYSTEM");
        return staffInit;
    }
}
