package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.UserLogin;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicalRecordDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.CSPT.sc2001_grp1_proj1.entity.MedicalRecord;

public class Patient {
    public static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();

    public void main() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");

        boolean loggedIn = true;
        while (loggedIn) { 
            Scanner choice_scanner = new Scanner(System.in);
            int choice = choice_scanner.nextInt();
            switch(choice) {
                case 1:
                    //View Medical Record
                    viewMedicalRecord();
                    main();
                    break;
                case 2:
                    //Update Personal Information
                    updatePersonalInformation();
                    break;
                case 3:
                    // View Available Appoointment Slots
                    viewAvailableAppointmentSlots();
                    main();
                    break;
                case 4:
                    // Schedule an Appointment
                case 5:
                    // Reschedule an appointment
                case 6:
                    // Cancel an appointment
                case 7:
                    // View Scheduled Appointments
                case 8:
                    // View Past appointment outcome records
                case 9:
                    loggedIn = false;
                    HospitalManagementApp.logout();
                    break;
            }
        }

    }

    private List<MedicalRecord> filterMedicalRecords(String userID) {
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();
        loadMedicalRecords.populateMedicalRecords(medicalRecords);

        //Filter the medical records based on userID
        List<MedicalRecord> filteredMedicalRecords = medicalRecords.stream().filter(record -> record.getPatientID().equals(userID)).toList();
        return filteredMedicalRecords;
    }

    private void viewMedicalRecord() {
        String userID = UserLogin.getLoginUserID();
        List<MedicalRecord> filteredMedicalRecords = filterMedicalRecords(userID);
        System.out.println("");
        System.out.println("Please find your medical record.");
        filteredMedicalRecords.forEach(record -> record.printMedicalRecord());
    }

    private void updatePersonalInformation() {
        System.out.println("Please select your choice of contact update.");
        System.out.println("1. Phone Number");
        System.out.println("2. Email");
        System.out.println("3. Back");

        while (true) {
            Scanner choice_scanner = new Scanner(System.in);
            int choice = choice_scanner.nextInt();
            switch(choice) {
                case 1:
                    //Update Phone Number
                    updatePhoneNumber();
                    main();
                    break;
                case 2:
                    //Update Email
                    updateEmail();
                    main();
                    break;
                case 3:
                    //Go back
                    main();
                    break;
            }
        }
    }
    private void updatePhoneNumber() {
        String userID = UserLogin.getLoginUserID();
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();

        System.out.println("Please enter your new phone number: ");    
        Scanner newPhone_scanner = new Scanner(System.in);
        String newPhone = newPhone_scanner.nextLine();
        loadMedicalRecords.updatePhoneNumber(userID, Integer.parseInt(newPhone));
    }
    private void updateEmail() {
        String userID = UserLogin.getLoginUserID();
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();

        System.out.println("Please enter your new email address: ");
        Scanner newEmail_scanner = new Scanner(System.in);
        String newEmail = newEmail_scanner.nextLine();
        loadMedicalRecords.updateEmail(userID, newEmail);
    }
    
    private void viewAvailableAppointmentSlots() {
        String userID = UserLogin.getLoginUserID();
        List<Appointment> appointments = appointmentData.getAppointmentsByPatientID(userID);
        appointments.forEach(appointment -> {
            appointment.printAppointmentDetails();
        });
    }
}
