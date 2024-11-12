package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.interfaces.HospitalStaffManagerInterface;

public class HospitalStaffManager implements HospitalStaffManagerInterface {

    public List<HospitalStaff> staffList;
    private LocalDateTime lastUpdatedTime;
    private String lastUpdatedBy;

    public HospitalStaffManager(List<HospitalStaff> staffList1, LocalDateTime date, String system) {
        this.staffList = staffList1;
        this.lastUpdatedTime = date;
        this.lastUpdatedBy = system;
    }


    @Override
    public void addStaffMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf
        (
            "\nEnter Staff details \n Enter Staff ID:"
        );
        String staffID = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Role:"
        );
        String role = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Gender:"
        );
        String gender = scanner.nextLine();

        System.out.printf
        (
            "\nEnter Age:"
        );
        int age = scanner.nextInt();

        HospitalStaff newStaff = new HospitalStaff(staffID,role,gender,age);

        staffList.add(newStaff);    
    }

    @Override
    public void removeStaffMember() {
        System.out.printf
        (
            "\nEnter Staff ID To Remove:"
        );
        Scanner scanner = new Scanner(System.in);
        String idToremove = scanner.nextLine();
        for (HospitalStaff staff : staffList) {
            if (staff.hospitalStaffID.equals(idToremove))
            {
                staffList.remove(staff);  
                break;          
            }
        }    
    }

    @Override
    public void updateStaffMember() {
        System.out.printf
        (
            "\nEnter Staff ID To Update:"
        );
        Scanner scanner = new Scanner(System.in);
        String idToUpdate = scanner.nextLine();

        HospitalStaff staffToUpdate = null;

        for (var hStaff : this.staffList) {
            if(hStaff.hospitalStaffID.equals(idToUpdate))
            {
                staffToUpdate = hStaff;
                break;
            }
        }

        if (staffToUpdate!=null)
        {
            boolean updatingStaff = true;
            while(updatingStaff)
            {
                System.out.printf
                (
                    "\nWhat would you like to update?\n 1 Staff ID?\n 2 Role?\n 3 Age?\n 4 Gender?\n 5 Cancel\n Enter Choice: "
                );
                int actionStaff = scanner.nextInt();
                scanner.nextLine();
                switch (actionStaff) {
                    case 1 -> {
                        System.out.printf
                        (
                            "\nEnter new Staff ID: "
                        );
                        staffToUpdate.hospitalStaffID = scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.printf
                        (
                            "\nEnter new Role: "
                        );            
                        staffToUpdate.role = scanner.nextLine();
                    }
                    case 3 -> {
                        System.out.printf
                        (
                            "\nEnter new Age: "
                        );            
                        staffToUpdate.age = scanner.nextInt();
                        scanner.nextLine();

                    }
                    case 4 -> {
                        System.out.printf
                        (
                            "\nEnter new Gender: "
                        );  
                        staffToUpdate.gender = scanner.nextLine();            
                    }
                    default -> {
                        System.out.printf
                        (
                            "\nCancelled "
                        );   
                        updatingStaff = false;
                        break;                
                    }
                } 
            }           
        }    
    }

    @Override
    public void displayStaff() {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.printf
            (
                "\nOrder By?:\n 1 Staff ID\n 2 Age?\n 3 Gender?\n 4 Roles?\n 5 Back\n Enter Choice: "
            );
            int choice = scanner.nextInt();

            switch (choice) {
                case 2:   
                    this.staffList.sort(Comparator.comparingInt(HospitalStaff::getAge));                 
                    break;
                case 1:
                    this.staffList.sort(Comparator.comparing(HospitalStaff::gethospitalStaffID));                 
                    break;
                case 3:
                    this.staffList.sort(Comparator.comparing(HospitalStaff::getgender));                     
                    break;
                case 4:
                    this.staffList.sort(Comparator.comparing(HospitalStaff::getgender));                     
                    break;
                default:
                    break;
            }

            for (HospitalStaff staff : this.staffList) {
                String printStaff = String.format("\n---------------\nStaff ID: %s\n Role: %s\n Gender: %s\n Age: %d\n---------------\n",staff.hospitalStaffID,staff.role,staff.gender,staff.age);
                System.out.printf(printStaff);
            }   
            break;
        }
    }
}
