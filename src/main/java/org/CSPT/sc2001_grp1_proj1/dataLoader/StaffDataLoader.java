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

public class StaffDataLoader {
    private static final String EXCEL_FILE_PATH = "./data/UserList.xlsx";

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
                if(!row.getCell(2).getStringCellValue().equals("Patient"))
                {
                    refreshHashMaps();
                    String staffUserName = row.getCell(5).getStringCellValue();
                    HospitalStaff staff = new HospitalStaff(validUsers.get(staffUserName).hospitalID, validUsers.get(staffUserName).name, validUsers.get(staffUserName).role, validUsers.get(staffUserName).gender, validUsers.get(staffUserName).age);
                    staffList.add(staff);
                }

            }
        } catch (IOException e) {
        }
        HospitalStaffManager staffInit = new HospitalStaffManager(staffList, LocalDateTime.now(), "SYSTEM");
        return staffInit;
    }
}
