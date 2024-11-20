package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;
import org.CSPT.sc2001_grp1_proj1.UserLogin;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentOutcomeRecordsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.dataLoader.MedicalRecordDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeRecord;
import org.CSPT.sc2001_grp1_proj1.entity.MedicalRecord;

/**
 * Class representing the Patient role in the hospital management system.
 * Provides functionalities such as viewing and updating personal information, 
 * managing medical records, and handling appointments.
 */
public class Patient {

    /**
     * A list of medical records associated with the patient.
     */
    public static List<MedicalRecord> medicalRecords = new ArrayList<>();

    /**
     * Main menu for patient-specific operations. Provides options to view and manage medical records,
     * appointments, and personal information.
     */
    public void main() {
        System.out.print("\nPatient's Menu:\n");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout\n");
        System.out.print("Enter your choice : ");

        boolean loggedIn = true;
        while (loggedIn) { 
            Scanner choice_scanner = new Scanner(System.in);
            int choice = choice_scanner.nextInt();
            switch(choice) {
                case 1 -> {
                    viewMedicalRecord();
                    main();
                }
                case 2 -> updatePersonalInformation();
                case 3 -> {
                    viewAvailableAppointmentSlots();
                    main();
                }
                case 4 -> {
                    scheduleAppointment();
                    main();
                }
                case 5 -> {
                    rescheduleAppointment();
                    main();
                }
                case 6 -> {
                    cancelAppointment();
                    main();
                }
                case 7 -> {
                    viewScheduledAppointments();
                    main();
                }
                case 8 -> {
                    viewPastAppointmentOutcomeRecords();
                    main();
                }
                case 9 -> {
                    loggedIn = false;
                    HospitalManagementApp.logout(null);
                }
            }
        }
    }

    /**
     * Filters medical records based on the patient ID.
     *
     * @param userID The patient ID for which medical records are to be filtered.
     * @return A list of filtered medical records.
     */
    private List<MedicalRecord> filterMedicalRecords(String userID) {
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();
        loadMedicalRecords.populateMedicalRecords(medicalRecords);

        // Filter the medical records based on userID
        return medicalRecords.stream().filter(record -> record.getPatientID().equals(userID)).toList();
    }

    /**
     * Displays the patient's medical records.
     */
    private void viewMedicalRecord() {
        String userID = UserLogin.getLoginUserID();
        List<MedicalRecord> filteredMedicalRecords = filterMedicalRecords(userID);
        System.out.println("");
        System.out.println("Please find your medical record.");
        filteredMedicalRecords.forEach(MedicalRecord::printMedicalRecord);
    }

    /**
     * Provides options to update the patient's personal information.
     */
    private void updatePersonalInformation() {
        System.out.println("\nPlease select your choice of contact update.");
        System.out.println("1. Phone Number");
        System.out.println("2. Email");
        System.out.println("3. Back\n");
        System.out.print("Enter your choice : ");

        while (true) {
            Scanner choice_scanner = new Scanner(System.in);
            int choice = choice_scanner.nextInt();
            switch(choice) {
                case 1 -> {
                    updatePhoneNumber();
                    main();
                }
                case 2 -> {
                    updateEmail();
                    main();
                }
                case 3 -> main();
            }
        }
    }

    /**
     * Updates the patient's phone number.
     */
    private void updatePhoneNumber() {
        String userID = UserLogin.getLoginUserID();
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();

        System.out.println("Please enter your new phone number: ");    
        Scanner newPhone_scanner = new Scanner(System.in);
        String newPhone = newPhone_scanner.nextLine();
        loadMedicalRecords.updatePhoneNumber(userID, Integer.parseInt(newPhone));
    }

    /**
     * Updates the patient's email address.
     */
    private void updateEmail() {
        String userID = UserLogin.getLoginUserID();
        MedicalRecordDataLoader loadMedicalRecords = new MedicalRecordDataLoader();

        System.out.println("Please enter your new email address: ");
        Scanner newEmail_scanner = new Scanner(System.in);
        String newEmail = newEmail_scanner.nextLine();
        loadMedicalRecords.updateEmail(userID, newEmail);
    }

    /**
     * Displays the available appointment slots.
     */
    private void viewAvailableAppointmentSlots() {
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> appointments = appointmentData.getAvailableAppointments();
        HashMap<Integer, Appointment> hashedAppointments = appointments.stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (hashedAppointments.isEmpty()) {
            System.out.println("Currently, there are no available appointments.\n");
            main();
        }

        System.out.println("\nPlease find the available appointments.");
        hashedAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentDetails();
        });
    }

    /**
     * Allows the patient to schedule an appointment.
     */
    private void scheduleAppointment() {
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> appointments = appointmentData.getAvailableAppointments();
        HashMap<Integer, Appointment> hashedAppointments = appointments.stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (hashedAppointments.isEmpty()) {
            System.out.println("Currently, there are no available appointments.\n");
            main();
        }

        System.out.println("\nPlease find the available appointments.");
        hashedAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();

        if (hashedAppointments.get(choice) != null) {
            String appointmentID = hashedAppointments.get(choice).getAppointmentID();
            String userID = UserLogin.getLoginUserID();
            appointmentData.scheduleAppointment(appointmentID, userID);
            System.out.println("Your appointment has been scheduled successfully.\n");
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }

    /**
     * Allows the patient to reschedule an existing appointment.
     */
    private void rescheduleAppointment() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }

        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment you would like to reschedule.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();

        if (hashedScheduledAppointments.get(choice) != null) {
            String appointmentID = hashedScheduledAppointments.get(choice).getAppointmentID();
            appointmentData.cancelAppointment(appointmentID);
            System.out.println("Your appointment has been cancelled successfully. Please schedule the available appointments.\n");
            appointmentData.loadAppointments();
            scheduleAppointment();
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }

    /**
     * Allows the patient to cancel an existing appointment.
     */
    private void cancelAppointment() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }

        System.out.println("\nPlease find the scheduled appointments.");
        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment you would like to cancel.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();

        if (hashedScheduledAppointments.get(choice) != null) {
            String appointmentID = hashedScheduledAppointments.get(choice).getAppointmentID();
            appointmentData.cancelAppointment(appointmentID);
            System.out.println("Your appointment has been cancelled successfully.\n");
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }

    /**
     * Displays the patient's scheduled appointments.
     */
    private void viewScheduledAppointments() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }

        System.out.println("The scheduled appointments:");
        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentDetails();
        });
    }

    /**
     * Displays the patient's past appointment outcome records.
     */
    private void viewPastAppointmentOutcomeRecords() {
        String userID = UserLogin.getLoginUserID();
        AppointmentOutcomeRecordsDataLoader appointmentOutcomeRecordData = new AppointmentOutcomeRecordsDataLoader();
        HashMap<Integer, AppointmentOutcomeRecord> pastAppointmentOutcomeRecords = appointmentOutcomeRecordData
                .getPastAppointmentOutcomeRecords(userID).stream()
                .collect(HashMap::new, (map, streamValue) -> map.put(map.size(), streamValue), HashMap::putAll);

        if (pastAppointmentOutcomeRecords.isEmpty()) {
            System.out.println("Currently, there are no past appointment outcome records.");
            main();
        }

        System.out.println("Past appointment outcome records:");
        pastAppointmentOutcomeRecords.forEach((i, appointment) -> {
            System.out.println("\nIndex : " + i);
            appointment.printAppointmentOutcomeRecordDetails();
        });
    }
}