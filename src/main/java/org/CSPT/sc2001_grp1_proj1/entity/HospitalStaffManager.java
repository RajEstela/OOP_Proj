package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

        while (true) { 
            System.out.printf
            (
                "\nPlease select your option:\n1 Add Existing User\n2 Add New User\nEnter Choice:"
            );
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 :
                    System.out.printf
                    (
                        "\nEnter UserName:"
                    );
                    String staffUserName = scanner.nextLine();
        
                    if(validUsers.containsKey(staffUserName))
                    {
                        System.out.printf
                        (
                            "\nEnter StaffID:"
                        );
                        String StaffID = scanner.nextLine();
        
                        System.out.printf
                        (
                            "\nEnter Role:"
                        );
                        String role = scanner.nextLine();
                        HospitalStaff staff = new HospitalStaff(StaffID, validUsers.get(staffUserName).name, role, validUsers.get(staffUserName).gender, validUsers.get(staffUserName).age);
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
                        break;
                case 2:
                    System.out.printf("\nEnter Hospital Staff ID: ");
                    String hospitalID = scanner.nextLine();
                
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
                    Users newUser = new Users(hospitalID, name, role, gender, age, username, password, email, phoneNo);
                    UserDataLoader.addUser(newUser);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    
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
                case 5:
                    System.out.println("Returning to the previous menu...");
                    break; 
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
            if (choice == 5) {
                break;
            }
            
            System.out.printf("\n%-20s %-20s %-20s %-20s%n", "Staff ID", "Role", "Gender", "Age");
            System.out.println("-------------------------------------------------------------");
            
            for (HospitalStaff staff : this.staffList) {
                System.out.printf(
                    "%-20s %-20s %-20s %-20d%n", // Add %n for a new line and %d for the age (integer)
                    staff.hospitalStaffID, 
                    staff.role, 
                    staff.gender, 
                    staff.age
                );
            }
        }
    }


    @Override
    public void addUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }
}
