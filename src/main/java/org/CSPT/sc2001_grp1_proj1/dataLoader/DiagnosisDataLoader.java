package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.Diagnosis;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class handles loading, retrieving, and updating diagnosis records from an Excel file.
 */
public class DiagnosisDataLoader {
    /**
     * Path to the Excel file containing diagnosis data.
     */
    private final String EXCEL_FILE_PATH = "./data/DiagnosisList.xlsx";
    /**
     * Static list of all loaded diagnosis records.
     */
    private static List<Diagnosis> diagnoses = new ArrayList<>();
    /**
     * Constructor that initializes the loader and loads diagnosis data.
     */
    public DiagnosisDataLoader() {
        loadDiagnosis();
    }
    /**
     * Loads diagnosis data from the Excel file into the static list of diagnoses.
     */
    public void loadDiagnosis() {
        // Clear diagnosis
        diagnoses.clear();
        // Load excel sheet into diagnosis
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                String doctorID = row.getCell(0).getStringCellValue();
                String patientID = row.getCell(1).getStringCellValue();
                String diagnosisDetails = row.getCell(2).getStringCellValue();
                String treatmentPlan = row.getCell(3).getStringCellValue();
                String prescription = row.getCell(4).getStringCellValue();
                String diagnosisDateTime = row.getCell(5).getStringCellValue();

                Diagnosis diagnosis = new Diagnosis(doctorID, patientID, diagnosisDetails, treatmentPlan, prescription, diagnosisDateTime);
                diagnoses.add(diagnosis);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Retrieves all loaded diagnoses.
     *
     * @return A list of all diagnoses.
     */
    public static List<Diagnosis> getDiagnosis() {
        return diagnoses;
    }
    /**
     * Retrieves all diagnoses associated with a specific patient.
     *
     * @param patientID The ID of the patient.
     * @return A list of diagnoses for the specified patient.
     */
    public List<Diagnosis> getDiagnosisByPatient(String patientID) {
        List<Diagnosis> filteredDiagnosis = diagnoses.stream().filter(diagnosis -> 
            diagnosis.getPatientID().equals(patientID)
        ).toList();
        return filteredDiagnosis;
    }
    /**
     * Retrieves all diagnoses associated with a specific doctor.
     *
     * @param doctorID The ID of the doctor.
     * @return A list of diagnoses for the specified doctor.
     */
    public List<Diagnosis> getDiagnosisByDoctor(String doctorID) {
        List<Diagnosis> filteredDiagnosis = diagnoses.stream().filter(diagnosis -> 
            diagnosis.getDoctorID().equals(doctorID)
        ).toList();
        return filteredDiagnosis;
    }
    /**
     * Adds a new diagnosis record to the Excel file.
     *
     * @param diagnosis The diagnosis record to be added.
     */
    public void setNewDiagnosis(Diagnosis diagnosis) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);
    
            // Populate the new row with diagnosis details
            newRow.createCell(0).setCellValue(diagnosis.getDoctorID());
            newRow.createCell(1).setCellValue(diagnosis.getPatientID());
            newRow.createCell(2).setCellValue(diagnosis.getDiagnosis());
            newRow.createCell(3).setCellValue(diagnosis.getTreatmentPlan());
            newRow.createCell(4).setCellValue(diagnosis.getPrescription());
            newRow.createCell(5).setCellValue(diagnosis.getDiagnosisDateTime());
    
            try (FileOutputStream outFile = new FileOutputStream(new File("./data/DiagnosisList.xlsx"))) {
                workbook.write(outFile);
            }
    
            System.out.println("Diagnosis added");
    
        } catch (IOException e) {
            System.out.println("Error updating the diagnosis list: " + e.getMessage());
        }
    }  
    
}
