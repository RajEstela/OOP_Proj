package org.CSPT.sc2001_grp1_proj1.FunctionRoles;

import java.util.ArrayList;
import java.util.HashMap;
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
                    scheduleAppointment();
                    main();
                    break;
                case 5:
                    // Reschedule an appointment
                    rescheduleAppointment();
                    main();
                    break;
                case 6:
                    // Cancel an appointment
                    cancelAppointment();
                    main();
                    break;
                case 7:
                    // View Scheduled Appointments
                    viewScheduledAppointments();
                    main();
                    break;
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
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> appointments = appointmentData.getAvailableAppointments();
        HashMap<Integer, Appointment> hashedAppointments = appointments.stream().collect(HashMap<Integer, Appointment>::new,(map, streamValue) -> map.put(map.size(), streamValue),(map, map2) -> {});

        if(hashedAppointments.isEmpty()) {
            System.out.println("Currently, there are no available appointments.\n");
            main();
        }

        hashedAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : "+i);
            appointment.printAppointmentDetails();
        });
    }

    private void scheduleAppointment() {
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> appointments = appointmentData.getAvailableAppointments();
        HashMap<Integer, Appointment> hashedAppointments = appointments.stream().collect(HashMap<Integer, Appointment>::new,(map, streamValue) -> map.put(map.size(), streamValue),(map, map2) -> {});

        if(hashedAppointments.isEmpty()) {
            System.out.println("Currently, there are no available appointments.\n");
            main();
        }

        hashedAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : "+i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();
        
        if(hashedAppointments.get(choice) != null) {
            String appointmentID = hashedAppointments.get(choice).getAppointmentID();
            String userID = UserLogin.getLoginUserID();
            appointmentData.scheduleAppointment(appointmentID, userID);
            System.out.println("Your appointment has been scheduled successfully.\n");
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }

    private void rescheduleAppointment() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream().collect(HashMap<Integer, Appointment>::new,(map, streamValue) -> map.put(map.size(), streamValue),(map, map2) -> {});

        if(hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }
        
        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : "+i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment you would like to reschedule.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();

        if(hashedScheduledAppointments.get(choice) != null) {
            String appointmentID = hashedScheduledAppointments.get(choice).getAppointmentID();
            appointmentData.cancelAppointment(appointmentID);
            System.out.println("Your appointment has been cancelled successfully. Please schedule the available appointments.\n");
            appointmentData.loadAppointments();
            scheduleAppointment();
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }
    
    private void cancelAppointment() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream().collect(HashMap<Integer, Appointment>::new,(map, streamValue) -> map.put(map.size(), streamValue),(map, map2) -> {});

        if(hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }
        
        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : "+i);
            appointment.printAppointmentDetails();
        });

        System.out.println("Please enter the index number for the appointment you would like to cancel.");
        Scanner choice_scanner = new Scanner(System.in);
        int choice = choice_scanner.nextInt();

        if(hashedScheduledAppointments.get(choice) != null) {
            String appointmentID = hashedScheduledAppointments.get(choice).getAppointmentID();
            appointmentData.cancelAppointment(appointmentID);
            System.out.println("Your appointment has been cancelled successfully.\n");
        } else {
            System.out.println("The index value is not correct.\n");
        }
    }

    private void viewScheduledAppointments() {
        String userID = UserLogin.getLoginUserID();
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        List<Appointment> scheduledAppointments = appointmentData.getScheduledAppointments(userID);
        HashMap<Integer, Appointment> hashedScheduledAppointments = scheduledAppointments.stream().collect(HashMap<Integer, Appointment>::new,(map, streamValue) -> map.put(map.size(), streamValue),(map, map2) -> {});

        if(hashedScheduledAppointments.isEmpty()) {
            System.out.println("Currently, there are no scheduled appointments.");
            main();
        }
        System.out.println("The scheduled appointments:");
        hashedScheduledAppointments.forEach((i, appointment) -> {
            System.out.println("\nIndex : "+i);
            appointment.printAppointmentDetails();
        });
    }
}
