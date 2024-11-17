package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Users;

public class UserLogin {

    private final HashMap<String, String> validUsersLogin;
    private final HashMap<String, Users> validUsers;
    private static String loginUser;

    public UserLogin(HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers) {
        this.validUsersLogin = validUsersLogin;
        this.validUsers = validUsers;
    }

    public Users loginMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("\n Login\n Username: ");
        String userName = scanner.nextLine();

        System.out.printf("\n Password (Enter \"Forgot\" to reset password): ");
        String pwd = scanner.nextLine();

        if ("Forgot".equals(pwd)) {
            forgotPassword(scanner);
            return null;
        }

        Users userLogin = login(userName, pwd);
        if (userLogin != null) {
            System.out.println("\nLogin Successfully\n");
            this.loginUser = userLogin.getHospitalID();
            return userLogin;
        } else {
            System.out.println("Login Failed");
            return null;
        }
        
    }

    private Users login(String userName, String pwd) {
        if (validUsersLogin.containsKey(userName) && validUsersLogin.get(userName).equals(pwd)) {
            return validUsers.get(userName);
        }
        return null;
    }

    public void forgotPassword(Scanner scanner) {
        System.out.println("Enter Username:");
        String userName = scanner.nextLine();
    
        if (UserDataLoader.resetPassword(userName, validUsers, scanner)) {
            System.out.println("Password reset successfully.");
            
            // Reload user data to refresh the hash maps
            validUsersLogin.clear();
            validUsers.clear();
            UserDataLoader.populateUsers(validUsersLogin, validUsers);
        } else {
            System.out.println("Username not found. Please try again.");
        }
    }

    public static String getLoginUserID() {
        return loginUser;
    }
    
}
