package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.UserLogin;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentOutcomeRecordsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicalRecordDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeRecord;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentStatusEnum;
import org.CSPT.sc2001_grp1_proj1.entity.MedicalRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor {
    public static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private final AppointmentsDataLoader appointmentsDataLoader = new AppointmentsDataLoader();
    public static List<Appointment> getdAppointments = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);


    public void main() {
        boolean loggedIn = true;
        while(loggedIn){
            switch (doctorMenu()) {
                case 1 -> {
                    //View Patient Medical Records 
                    viewPatientMedicalRecord();
                }
                case 2 -> {
                    //Update Patient Medical Records
                }
                case 3 -> {
                    //View personal schedule
                    viewPersonalSchedule();
                }
                case 4 -> {
                    //Set Availability for Appointments
                    setAvailableAppointmentSchedule();
                }
                case 5 -> {
                    //Accept or Decline Appointment Requests
                    acceptOrDeclineAppointment();
                }
                case 6 -> {
                    //View Upcoming Appointments
                }
                case 7 -> {
                    //Record Appointment Outcome
                   recordAppointmentOutcome();
                }
                case 8 -> {
                    //logout
                    HospitalManagementApp.logout();
                    System.out.printf("Bye!");
                    loggedIn = false;
                    break;
                }
            }
        }
    }

    private static int doctorMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
    
        while (true) {
            System.out.printf(
                "\n 1 View Patient Medical Records\n 2 Update Patient Medical Records\n 3 View personal schedule\n 4 Set Availability for Appointments\n 5 Accept or Decline Appointment Requests \n" + //
                                        " 6 View Upcoming Appointments\n 7 Record Appointment Outcome\n 8 Logout \n Enter Choice: "
            );
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 8) {
                    throw new IllegalArgumentException("Please enter a valid option (1-5).");
                }
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); 
            }
        }
    
        return choice;
    }

public void viewPatientMedicalRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID to view the medical record: ");
        String patientID = scanner.nextLine();

        boolean found = false;
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();
        loadMedicalRecords.populateMedicalRecords(medicalRecords);

        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equals(patientID)) {
                record.printMedicalRecord();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient record not found.");
        }
    }

public void viewPersonalSchedule() {
        System.out.println("\n Here is your schedule");
        String userID = UserLogin.getLoginUserID();
        List<Appointment> personalAppointments = appointmentsDataLoader.getAppointments().stream()
                .filter(appointment -> appointment.getDoctorID().equals(userID))
                .toList();

        if (personalAppointments.isEmpty()) {
            System.out.println("There is no appointments scheduled.");
        } else {
            for (Appointment appointment : personalAppointments) {
                appointment.printAppointmentDetails();
            }
        }
    }

public void acceptOrDeclineAppointment() {
        System.out.println("\n--- Pending Appointments ---");
        String userID = UserLogin.getLoginUserID();
        List<Appointment> pendingAppointments = appointmentsDataLoader.getAppointmentsByStatus(AppointmentStatusEnum.Pending)
                .stream()
                .filter(appointment -> appointment.getDoctorID().equals(userID))
                .toList();

        if (pendingAppointments.isEmpty()) {
            System.out.println("There is no pending appointments.");
            return;
        }

        for (Appointment appointment : pendingAppointments) {
            appointment.printAppointmentDetails();
            System.out.print("Accept or Decline this appointment? (A/D): ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("A")) {
                appointmentsDataLoader.confirmAppointment(appointment.getAppointmentID(), userID);
                System.out.println("Appointment confirmed.");
            } else if (choice.equals("D")) {
                appointmentsDataLoader.cancelAppointment(appointment.getAppointmentID());
                System.out.println("Appointment declined.");
            } else {
                System.out.println("Sorry!Invalid choice.");
            }
        }
        appointmentsDataLoader.loadAppointments();
    }

    public void setAvailableAppointmentSchedule() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentsDataLoader = new AppointmentsDataLoader(); // Create an instance
    
        System.out.println("\n--- Set Available Appointment Schedule ---");
        System.out.print("Enter the number of available appointment slots to add: ");
        int numSlots;
    
        try {
            numSlots = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered. Operation aborted.");
            return;
        }
    
        for (int i = 0; i < numSlots; i++) {
            System.out.println("\nCreating appointment slot " + (i + 1) + " of " + numSlots);
    
            System.out.print("Enter appointment date (yyyy-MM-dd): ");
            String date = scanner.nextLine();
    
            System.out.print("Enter appointment time (HH:mm): ");
            String time = scanner.nextLine();
    
            String appointmentDateTime = date + "T" + time;
    
            // Generate the IDs
            String appointmentID = appointmentsDataLoader.getNextAppointmentID();
            String outcomeRecordID = appointmentsDataLoader.getNextOutcomeRecordID();
    
            // Create a new Appointment object
            Appointment newAppointment = new Appointment(
                appointmentID,
                appointmentDateTime,
                "N/A",           // No patient assigned yet
                userID,
                "Available",     // Marked as available
                outcomeRecordID  // New outcome record ID
            );
    
            // Call the method in AppointmentsDataLoader to add to Excel
            appointmentsDataLoader.setNewAppointment(newAppointment);
        }
    
        System.out.println("All appointment slots added successfully!");
    }
   
    public void recordAppointmentOutcome() {
        System.out.println("\n--- Record Appointment Outcome ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine().trim();
    
        AppointmentsDataLoader appointmentDataLoader = new AppointmentsDataLoader();
        Appointment appointment = appointmentDataLoader.getAppointments().stream()
                .filter(a -> a.getAppointmentID().equals(appointmentID))
                .findFirst()
                .orElse(null);
    
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }
    
        System.out.print("Enter Type of Service: ");
        String serviceType = scanner.nextLine().trim();
    
        System.out.print("Enter Prescribed Medications: ");
        String prescribedMedications = scanner.nextLine().trim();
    
        String prescribedStatus = "Pending";
    
        System.out.print("Enter Consultation Notes: ");
        String consultationNotes = scanner.nextLine().trim();
    
        String outcomeRecordID = appointment.getAppointmentOutcomeRecordID();
    if (outcomeRecordID == null || outcomeRecordID.isEmpty()) {
        System.out.println("No Outcome Record ID associated with this appointment.");
        return;
    }
        // Create a new AppointmentOutcomeRecord
        AppointmentOutcomeRecord newRecord = new AppointmentOutcomeRecord(
                appointmentID, appointment.getAppointmentDateTime(),
                appointment.getPatientID(), appointment.getDoctorID(),
                appointment.getAppointmentStatus(), outcomeRecordID,
                serviceType, prescribedMedications, prescribedStatus, consultationNotes
        );
    
        // Save the record
        AppointmentOutcomeRecordsDataLoader outcomeDataLoader = new AppointmentOutcomeRecordsDataLoader();
        outcomeDataLoader.addNewRecord(newRecord);
    
        // Update the appointment status to "Done"
        String status = "Done";
        appointmentDataLoader.updateAppointmentStatus(appointmentID,status);
    
        
    }
    
 }