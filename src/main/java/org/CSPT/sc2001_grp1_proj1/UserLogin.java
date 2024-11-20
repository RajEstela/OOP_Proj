package org.CSPT.sc2001_grp1_proj1;

import java.util.HashMap;
import java.util.Scanner;

import static org.CSPT.sc2001_grp1_proj1.HospitalManagementApp.refreshHashMaps;
import org.CSPT.sc2001_grp1_proj1.dataLoader.UserDataLoader;
import org.CSPT.sc2001_grp1_proj1.entity.Users;

/**
 * Class for managing user login, password management, and authentication.
 * Handles login, password reset, and password change functionalities for users.
 */
public class UserLogin {

    protected final HashMap<String, String> validUsersLogin;
    protected final HashMap<String, Users> validUsers;
    private static String loginUser;

    /**
     * Constructor for initializing UserLogin with valid user credentials and details.
     *
     * @param validUsersLogin A HashMap containing valid usernames and passwords.
     * @param validUsers      A HashMap containing valid user details mapped by username.
     */
    public UserLogin(HashMap<String, String> validUsersLogin, HashMap<String, Users> validUsers) {
        this.validUsersLogin = validUsersLogin;
        this.validUsers = validUsers;
    }

    /**
     * Displays the login menu and handles user authentication.
     *
     * @param scanner A Scanner object for reading user input.
     * @return A {@link Users} object representing the successfully logged-in user, or null if login fails.
     */
    public Users loginMenu(Scanner scanner) {
        try {
            while (true) {
                System.out.printf("\nLogin:\nUsername (or type 'Exit' to quit): ");
                String userName = scanner.nextLine();

                if ("Exit".equalsIgnoreCase(userName)) {
                    System.out.println("\nExiting the program. Goodbye!");
                    System.exit(0);
                }

                if (!validUsers.containsKey(userName)) {
                    System.out.println("\nInvalid username. Please try again.");
                    continue;
                }

                String checkPwd = validUsers.get(userName).getPassword();

                System.out.printf("\nPassword (Enter \"Forgot\" to reset password): ");
                String pwd = scanner.nextLine();

                if ("Forgot".equalsIgnoreCase(pwd)) {
                    forgotPassword(scanner);
                    continue;
                } else if ("DEFAULT_PASSWORD".equals(pwd) && "DEFAULT_PASSWORD".equals(checkPwd)) {
                    changePassword(scanner, userName);
                    continue;
                }

                Users userLogin = login(userName, pwd);
                if (userLogin != null) {
                    System.out.println("\nLogin Successfully");
                    loginUser = userLogin.gethospitalID();
                    return userLogin;
                } else {
                    System.out.println("\nLogin Failed, invalid credentials. Please log in again.");
                }
            }
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    /**
     * Authenticates a user based on their username and password.
     *
     * @param userName The username entered by the user.
     * @param pwd      The password entered by the user.
     * @return A {@link Users} object if authentication is successful, otherwise null.
     */
    private Users login(String userName, String pwd) {
        if (validUsersLogin.containsKey(userName) && validUsersLogin.get(userName).equals(pwd)) {
            return validUsers.get(userName);
        }
        return null;
    }

    /**
     * Handles the process for resetting a forgotten password.
     *
     * @param scanner A Scanner object for reading user input.
     */
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

    /**
     * Allows the user to change their password after login or when required.
     *
     * @param scanner  A Scanner object for reading user input.
     * @param username The username of the user whose password is being changed.
     */
    public void changePassword(Scanner scanner, String username) {
        System.out.println("\nEnter New Password:");
        String newPassword = scanner.nextLine();
        UserDataLoader.resetPassword(username, validUsers, newPassword);
        refreshHashMaps();

        System.out.println("\nPassword changed successfully. Please log in again.");
    }

    /**
     * Retrieves the hospital ID of the currently logged-in user.
     *
     * @return A string representing the hospital ID of the logged-in user.
     */
    public static String getLoginUserID() {
        return loginUser;
    }
}
