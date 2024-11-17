package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
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
    public void addStaffMember(HashMap<String, Users> validUsers) {
        
        Scanner scanner = new Scanner(System.in);
        boolean selection = true;
        while (selection) { 
            System.out.printf
            (
                "\nPlease select your option:\n1 Add Existing User\n2 Add New User\n3 Back\nEnter Choice:"
            );
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    displayUser(validUsers);
                    System.out.printf
                            (
                                    "\nEnter UserName:"
                            );
                    String staffUserName = scanner.nextLine();
        
                    if(validUsers.containsKey(staffUserName))
                    {
        
                        System.out.printf
                                (
                                        "\nEnter Role:"
                                );
                        String role = scanner.nextLine();
                        HospitalStaff staff = new HospitalStaff("x", validUsers.get(staffUserName).name, role, validUsers.get(staffUserName).gender, validUsers.get(staffUserName).age);
                        UserDataLoader.updateRole(validUsers.get(staffUserName).username,validUsers,role);
                        staffList.add(staff);
                        
                        System.out.printf
                                (
                                        "\nStaff successfully added"
                                ); 
                    }
                    else{
                        System.out.printf
                                (
                                        "\nUser does not exists!\n"
                                );            
                    }
                }
                case 2 -> {             
                    System.out.printf("\nEnter Name: ");
                    String name = scanner.nextLine();
                
                    System.out.printf("\nEnter Role: ");
                    String role = scanner.nextLine();
                
                    System.out.printf("\nEnter Gender: ");
                    String gender = scanner.nextLine();
                
                    System.out.printf("\nEnter Age: ");
                    int age = Integer.parseInt(scanner.nextLine());
                
                    System.out.printf("\nEnter Username: ");
                    String username = scanner.nextLine();
                
                    System.out.printf("\nEnter Password: ");
                    String password = scanner.nextLine();
                
                    System.out.printf("\nEnter Email: ");
                    String email = scanner.nextLine();
                
                    System.out.printf("\nEnter Phone Number: ");
                    int phoneNo = Integer.parseInt(scanner.nextLine());
                
                    // Create a new Users object with the entered details
                    Users newUser = new Users("x", name, role, gender, age, username, password, email, phoneNo);
                    UserDataLoader.addUser(newUser);
                }
                case 3 -> selection = false;
                default -> throw new AssertionError();
            }
        }
    
    }

    @Override
    public void removeStaffMember() {
        boolean removedSuccessfully = false;
        Scanner scanner = new Scanner(System.in);

        System.out.printf
        (
            "\nEnter Staff ID To Remove:"
        );
        String idToremove = scanner.nextLine();
        for (HospitalStaff staff : staffList) {
            if (staff.hospitalStaffID.equals(idToremove))
            {
                staffList.remove(staff);  
                removedSuccessfully = UserDataLoader.removeUser(idToremove);
                break;          
            }
        }  
        if(removedSuccessfully)  
        {
            System.out.printf
            (
                "\nStaff Removed Successfully\n"
            );
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
                    "\nWhat would you like to update:\n 1 Staff ID\n 2 Role\n 3 Age?\n 4 Gender\n 5 Cancel\n Enter Choice: "
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
                    case 5 ->{
                        UserDataLoader.updateStaff(staffToUpdate);                
                        System.out.printf
                        (
                            "\nCancelled "
                        );   
                        updatingStaff = false;
                        break;          
                    }
                    default -> {
                        System.out.printf
                        (
                            "\nInvalid Selection "
                        );        
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
                "\nOrder By:\n1 Staff ID\n2 Age\n3 Gender?\n4 Roles\n5 Back\nEnter Choice: "
            );
            int choice = scanner.nextInt();

            switch (choice) {
                case 2 -> this.staffList.sort(Comparator.comparingInt(HospitalStaff::getAge));
                case 1 -> this.staffList.sort(Comparator.comparing(HospitalStaff::gethospitalStaffID));
                case 3 -> this.staffList.sort(Comparator.comparing(HospitalStaff::getgender));
                case 4 -> this.staffList.sort(Comparator.comparing(HospitalStaff::getrole));
                case 5 -> System.out.println("Returning to the previous menu...");
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }
            if (choice == 5) {
                break;
            }
            
            System.out.printf("\n%-20s %-20s %-20s %-20s %-20s%n", "Staff ID", "Name","Role", "Gender", "Age");
            System.out.println("------------------------------------------------------------------------------");
            
            for (HospitalStaff staff : this.staffList) {
                System.out.printf(
                    "%-20s %-20s %-20s %-20s %-20d%n", // Add %n for a new line and %d for the age (integer)
                    staff.hospitalStaffID,
                    staff.name, 
                    staff.role, 
                    staff.gender, 
                    staff.age
                );
            }
        }
    }

    public void displayUser(HashMap<String, Users> validUsers) {
        System.out.printf("\n%-20s %-20s %-20s %-20s%n", "Hospital ID","Username","Name","Role");
        System.out.println("------------------------------------------------------------------------------");
        
        for (Map.Entry<String, Users> users : validUsers.entrySet()) {
            System.out.printf(
                "%-20s %-20s %-20s %-20s%n", // Add %n for a new line and %d for the age (integer)
                users.getValue().hospitalID,
                users.getValue().username,
                users.getValue().name, 
                users.getValue().role
            );
        }
    }


    @Override
    public void addUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }
}
