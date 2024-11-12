package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaff;
import org.CSPT.sc2001_grp1_proj1.entity.HospitalStaffManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StaffDataLoader {

    public static HospitalStaffManager loadHospitalStaff(String excelFilePath) {
        List<HospitalStaff> staffList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                String staffId = row.getCell(0).getStringCellValue();
                String role = row.getCell(2).getStringCellValue();
                String gender = row.getCell(3).getStringCellValue();
                int age = (int) row.getCell(4).getNumericCellValue();

                HospitalStaff staff = new HospitalStaff(staffId, role, gender, age);
                staffList.add(staff);
            }
        } catch (IOException e) {
        }
        HospitalStaffManager staffInit = new HospitalStaffManager(staffList, LocalDateTime.now(), "SYSTEM");
        return staffInit;
    }
}
