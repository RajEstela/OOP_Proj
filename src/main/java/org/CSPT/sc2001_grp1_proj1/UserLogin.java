package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import static org.CSPT.sc2001_grp1_proj1.HospitalManagementApp.refreshHashMaps;
import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Users;

public class UserLogin {

    private final HashMap<String, String> validUsersLogin;
    protected final HashMap<String, Users> validUsers;
    private static String loginUser;

    public UserLogin(HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers) {
        this.validUsersLogin = validUsersLogin;
        this.validUsers = validUsers;
    }

    public Users loginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\nLogin:\nUsername: ");
        String userName = scanner.nextLine();
        
        String checkPwd = validUsers.get(userName).getPassword();

        System.out.printf("\nPassword (Enter \"Forgot\" to reset password): ");
        String pwd = scanner.nextLine();
    
        if ("Forgot".equalsIgnoreCase(pwd)) {
            forgotPassword(scanner);
            return null;
        } else if ("DEFAULT_PASSWORD".equals(pwd) && "DEFAULT_PASSWORD".equals(checkPwd)) {
            changePassword(scanner, userName);
            return null; 
        }
    
        Users userLogin = login(userName, pwd);
        if (userLogin != null) {
            System.out.println("\nLogin Successfully");
            loginUser = userLogin.gethospitalID();
            return userLogin;
        } else {
            System.out.println("\nLogin Failed, invalid credentials. Please log in again");
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
        System.out.println("\nEnter Username:");
        String userName = scanner.nextLine();
    
        if (!validUsers.containsKey(userName)) {
            System.out.println("\nUsername not found. Please try again.");
            return;
        }
    
        System.out.println("\nEnter New Password:");
        String newPassword = scanner.nextLine();
        UserDataLoader.resetPassword(userName, validUsers, newPassword);
        
        refreshHashMaps();
    
        System.out.println("\nPassword reset successfully. Please log in again.");
    }
    

    public void changePassword(Scanner scanner, String username) {
        if (!validUsers.containsKey(username)) {
            System.out.println("\nUsername not found. Please try again.");
            return;
        }
    
        System.out.println("\nEnter New Password:");
        String newPassword = scanner.nextLine();
        UserDataLoader.resetPassword(username, validUsers, newPassword);
        refreshHashMaps();
    
        System.out.println("\nPassword reset successfully. Please log in again.");
    }
    

    public static String getLoginUserID() {
        return loginUser;
    }
    
}
