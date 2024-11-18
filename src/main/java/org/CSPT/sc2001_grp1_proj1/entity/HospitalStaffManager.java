package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
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
                        UserDataLoader.updateRole(validUsers.get(staffUserName).getUsername(),validUsers,role);
                        validUsers = UserDataLoader.populateValidUsers(validUsers);
                        
                        HospitalStaff staffMem =  new HospitalStaff(
                            validUsers.get(staffUserName).gethospitalID(),
                            validUsers.get(staffUserName).getname(),
                            validUsers.get(staffUserName).getRole(),
                            validUsers.get(staffUserName).getGender(),
                            validUsers.get(staffUserName).getAge()
                        );
                        staffList.add(staffMem);                   
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
                
                    String password = "DEFAULT_PASSWORD";
                
                    System.out.printf("\nEnter Email: ");
                    String email = scanner.nextLine();
                
                    System.out.printf("\nEnter Phone Number: ");
                    int phoneNo = Integer.parseInt(scanner.nextLine());
                
                    // Create a new Users object with the entered details
                    Users newUser = new Users("x", name, role, gender, age, username, password, email, phoneNo);
                    UserDataLoader.addUser(newUser);
                    validUsers = UserDataLoader.populateValidUsers(validUsers);
                    if(role != "Patient"){
                        HospitalStaff staffMem =  new HospitalStaff(
                            validUsers.get(username).gethospitalID(),
                            validUsers.get(username).getname(),
                            validUsers.get(username).getRole(),
                            validUsers.get(username).getGender(),
                            validUsers.get(username).getAge()
                        );
                        staffList.add(staffMem);
                    }
                    System.out.printf("\nUser added with the default password.");

                }
                case 3 -> selection = false;
                default -> throw new AssertionError();
            }
        }
    
    }

    @Override
    public void removeStaffMember() {
        boolean removedCondition = true;
        Scanner scanner = new Scanner(System.in);
        while(removedCondition){
            System.out.printf
            (
                "\nEnter Staff ID To Remove:"
            );
            String idToremove = scanner.nextLine();
            for (HospitalStaff staff : staffList) {
                if (staff.gethospitalID().equals(idToremove))
                {
                    staffList.remove(staff);  
                    UserDataLoader.removeUser(idToremove);
                    removedCondition = false;
                    System.out.printf
                    (
                        "\nStaff Removed Successfully\n"
                    );
                    break;          
                }
            }  
            if(removedCondition)
            {
                System.out.printf
                (
                    "\nStaff does not exists try again.\n"
                ); 
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

        for (var hStaff : this.staffList) {
            if(hStaff.gethospitalID().equals(idToUpdate))
            {
                boolean updatingStaff = true;
                while(updatingStaff)
                {
                    System.out.printf
                    (
                        "\nWhat would you like to update:\n1 Role\n2 Age\n3 Gender\n4 Cancel\n Enter Choice: "
                    );
                    try {
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine(); 
                        switch (updateChoice) {
                            case 1 -> {
                                System.out.print("\nEnter new Role: ");
                                hStaff.role = scanner.nextLine();
                            }
                            case 2 -> {
                                System.out.print("\nEnter new Age: ");
                                hStaff.age = scanner.nextInt();
                                scanner.nextLine(); 
                            }
                            case 3 -> {
                                System.out.print("\nEnter new Gender: ");
                                hStaff.gender = scanner.nextLine();
                            }
                            case 4 -> {
                                System.out.println("\nUpdate cancelled.");
                                updatingStaff = false;
                            }
                            default -> System.out.println("\nInvalid selection. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input. Please enter a number.");
                        scanner.nextLine(); // Clear invalid input
                    }
                } 
                break;          
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
                "\nOrder By:\n1 Staff ID\n2 Age\n3 Gender\n4 Roles\n5 Back\nEnter Choice: "
            );
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.staffList.sort(Comparator.comparing(HospitalStaff::gethospitalStaffID));
                case 2 -> this.staffList.sort(Comparator.comparingInt(HospitalStaff::getAge));
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
                    staff.gethospitalStaffID(),
                    staff.getname(), 
                    staff.getrole(), 
                    staff.getgender(), 
                    staff.getAge()
                );
            }
        }
    }

    public void displayUser(HashMap<String, Users> validUsers) {
        System.out.printf("\n%-20s %-20s %-20s %-20s%n", "Hospital ID","Username","Name","Role");
        System.out.println("------------------------------------------------------------------------------");
        
        for (Map.Entry<String, Users> users : validUsers.entrySet()) {
            if(users.getValue().getRole().equals("Patient") || users.getValue().getRole().equals("Pending")){
                System.out.printf(
                    "%-20s %-20s %-20s %-20s%n", // Add %n for a new line and %d for the age (integer)
                    users.getValue().gethospitalID(),
                    users.getValue().getUsername(),
                    users.getValue().getname(), 
                    users.getValue().getRole()
                );
            }
        }
    }


    @Override
    public void addUser(HashMap<String, Users> validUsers) {
        Scanner scanner = new Scanner(System.in);

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
    
        String password = "DEFAULT_PASSWORD";
    
        System.out.printf("\nEnter Email: ");
        String email = scanner.nextLine();
    
        System.out.printf("\nEnter Phone Number: ");
        int phoneNo = Integer.parseInt(scanner.nextLine());
    
        Users newUser = new Users("x", name, role, gender, age, username, password, email, phoneNo);
        UserDataLoader.addUser(newUser);
        validUsers = UserDataLoader.populateValidUsers(validUsers);

        if(role != "Patient"){
            HospitalStaff staffMem =  new HospitalStaff(
                validUsers.get(username).gethospitalID(),
                validUsers.get(username).getname(),
                validUsers.get(username).getRole(),
                validUsers.get(username).getGender(),
                validUsers.get(username).getAge()
            );
            staffList.add(staffMem);
        }
        System.out.printf("\nUser added with the default password.");
    }
}
