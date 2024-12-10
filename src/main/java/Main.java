import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProfileManager socialNetwork = new ProfileManager();
        socialNetwork.populateInitialGraph();

        Scanner console = new Scanner(System.in);
        System.out.println("--- Welcome to Social Network! ---");
        String profileName = createProfileFirstTime(console, socialNetwork);
        menu(console, profileName, socialNetwork);
    }


    /**
     * This is the main menu that lets us navigate an instance of ProfileManager.
     * @param console receives user input
     * @param username current user's username
     * @param socialNetwork this instance of profileManger
     */
    public static void menu(Scanner console, String username, ProfileManager socialNetwork) {
        //Get current users Profile
        Profile userProfile = socialNetwork.getProfile(username);
        //variables
        int mode = -1;

        //Display menu options
        do {
            System.out.println();
            System.out.println("--- Social Network! ---");
            System.out.println("Current user: " + userProfile.getUsername());
            System.out.println();
            System.out.println("Hello " + userProfile.getName() + "!");
            System.out.println("Choose from the following options: ");
            System.out.println();
            System.out.println("--- Profile Options ---");
            System.out.println("1. View your profile");
            System.out.println("2. Modify your profile");
            System.out.println("3. Add a friend");
            System.out.println("4. View your friends list");
            System.out.println("5. View your friends' friends list");
            System.out.println();
            System.out.println("--- Admin Options ---");
            System.out.println("6. Add a new profile");
            System.out.println("7. View all profiles");
            System.out.println("8. Delete a profile");
            System.out.println("9. Switch current user");
            System.out.println();
            System.out.println("0. Logout (exit program)");
            System.out.print("Choose an option 1-9 or 0 to quit: ");

            //Respond to bad user not inputting integers
            while (!console.hasNextInt()) {
                console.next();
                System.out.println();
                System.out.println("That is not a valid option.");
                System.out.print("Choose an option 1-9 or 0 to quit: ");
            }

            mode = console.nextInt();
            System.out.println();

            //Switch statements for menu options
            switch(mode) {
                case 1:
                    viewProfile(username, socialNetwork, console);
                    break;
                case 2:
                    modifyProfile(username, console, socialNetwork);
                    break;
                case 3:
                    addFriend(username, console, socialNetwork);
                    break;
                case 4:
                    viewFriends(username, socialNetwork, console);
                    break;
                case 5:
                    viewFriendsFriends(username, socialNetwork, console);
                    break;
                case 6:
                    createProfile(console, socialNetwork);
                    break;
                case 7:
                    viewAllProfiles(socialNetwork, console);
                    break;
                case 8:
                    deleteProfile(username, console, socialNetwork);
                    break;
                case 9:
                    username = switchUser(username, console, socialNetwork);
                    userProfile = socialNetwork.getProfile(username);
                    break;
                case 0:
                    break;
                default:
                    System.out.print("Please choose an option 1-9 or 0 to quit: ");
                    break;
            }
         } while (mode != 0);
        System.out.println("Thank you for using Social Network! Goodbye!");
    }


    /**
     * Displays current user's profile.
     *
     * @param console receives user input
     * @param username current user's username
     * @param socialNetwork this instance of profileManger
     */
    public static void viewProfile(String username, ProfileManager socialNetwork, Scanner console) {
        Profile currentUser = socialNetwork.getProfile(username);
        System.out.println("--- " + currentUser.getName() + "'s Profile ---");
        currentUser.displayInformation();
        System.out.println();
        returnToMainMenu(console);
    }

    /**
     * Allows user to modify fields in their profile.
     * @param username current user's username
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     */
    public static void modifyProfile(String username, Scanner console, ProfileManager socialNetwork) {
        int modifyMode = -1;
        System.out.println("--- Modify Profile ---");
        do {
            System.out.println("Current user: " + username);
            System.out.println("Select from the following: ");
            System.out.println("1. Change name");
            System.out.println("2. Change picture file name");
            System.out.println("3. Change current status");
            System.out.println();
            System.out.println("0. Quit to main menu");
            System.out.print("Please choose 1-3 or 0 to quit to main menu: ");

            while (!console.hasNextInt()) {
                console.next();
                System.out.println();
                System.out.println("That is not a valid option.");
                System.out.print("Choose an option 1-3 or 0 to quit to main menu: ");
            }

            modifyMode = console.nextInt();
            System.out.println();

            switch (modifyMode) {
                case 1:
                    changeName(username, console, socialNetwork);
                    break;
                case 2:
                    changePicture(username, console, socialNetwork);
                    break;
                case 3:
                    changeStatus(username, console, socialNetwork);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Please choose an option 1-3 or 0 to quit to main menu.");
                    System.out.println();
                    break;
            }

        } while (modifyMode != 0);
    }

    /**
     * private helper method for modifyProfile - changes user's Name
     * @param console receives user input
     * @param username current user's username
     * @param socialNetwork this instance of profileManger
     */
    private static void changeName(String username, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(username);
        String newName;
        do {
            System.out.println("Current Profile Name: " + userProfile.getName());
            System.out.print("Enter new profile name or 0 to quit to Modify Profile menu: ");
            if (console.hasNextInt() && console.nextInt() == 0) {
                System.out.println();
                return;
            }
            newName = console.next();
            if (newName.isEmpty() || newName == null) {
                System.out.println("Profile name cannot be empty or null");
                System.out.print("Enter new profile name or type current profile name ("
                                        + userProfile.getName() + ") to exit: ");
            }
        } while (newName.isEmpty() || newName == null);

        userProfile.setName(newName);
    }

    /**
     * private helper method for modifyProfile - changes user's picture file name
     * @param username current user's username
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     */
    private static void changePicture(String username, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(username);
        System.out.println("Current Picture File Name: " + userProfile.getPicture());
        System.out.print("Enter new picture file name or 0 to quit to Modify Profile menu: ");
        if (console.hasNextInt() && console.nextInt() == 0) {
            System.out.println();
            return;
        }
        String newPicture = console.next();
        userProfile.setPicture(newPicture);
    }

    /**
     * private helper method for modifyProfile - changes user's status
     * @param username current user's username
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     */
    private static void changeStatus(String username, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(username);
        String newStatus;
        do {
            System.out.println("Current Profile Status: " + userProfile.getStatus());
            System.out.print("Enter new profile status or 0 to quit to Modify Profile menu: ");
            if (console.hasNextInt() && console.nextInt() == 0) {
                System.out.println();
                return;
            }
            newStatus = console.next();
            if (newStatus.isEmpty() || newStatus == null) {
                System.out.println("Profile Status cannot be empty or null.");
                System.out.print("Enter New Profile Status: ");
            }
        } while (newStatus.isEmpty() || newStatus == null);

        userProfile.setStatus(newStatus);
    }

    /**
     * Creates a friendship between current user and another person in the social network.
     * @param username current user's username
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     */
    public static void addFriend(String username, Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Add A Friend ---");

        //Displays current user's friendships
        System.out.println("Current Friendships: ");
        socialNetwork.displayCurrentUsersFriends(username);
        System.out.println();
        System.out.println();

        //Displays all usernames current user is not friends with
        System.out.println("Possible New Friends usernames: ");
        socialNetwork.availableFriends(username);
        System.out.println();

        //Create Friendship or quit to main
        System.out.println("Who do you want create a friendship with?");
        System.out.println("Enter their username or 0 to quit to main menu: ");
        if (console.hasNextInt() && console.nextInt() == 0) {
            return;
        }
        String friend = console.next();

        //Check if friendship was created and give user this information
        boolean didFriendshipCreate = socialNetwork.createFriendship(username, friend);
        if (didFriendshipCreate) {
            System.out.println("Link Successful. " + username + " and " + friend + " are now friends.");
        } else {
            System.out.println("Link Unsuccessful. New friend must be an existing user and not currently a friend.");
        }

        returnToMainMenu(console); // Controls flow of program so main menu doesn't appear right away
    }

    /**
     * Displays all current user's friends.
     * @param username current user's username
     * @param socialNetwork this instance of profileManger
     * @param console receives user input
     */
    public static void viewFriends(String username, ProfileManager socialNetwork, Scanner console) {
        Profile userProfile = socialNetwork.getProfile(username);
        System.out.println("--- " + userProfile.getName() + "'s Friends List ---");
        socialNetwork.displayCurrentUsersFriends(username);
        System.out.println();
        System.out.println();
        returnToMainMenu(console); // Controls flow of program so main menu doesn't appear right away
    }

    /**
     * Displays all current user's friends' friend lists.
     * @param username current user's username
     * @param socialNetwork this instance of profileManger
     * @param console receives user input
     */
    public static void viewFriendsFriends(String username, ProfileManager socialNetwork, Scanner console) {
        Profile userProfile = socialNetwork.getProfile(username);
        System.out.println("--- " + userProfile.getName() + "'s Friends' Friends List ---");
        socialNetwork.displayCurrentUsersFriendsFriends(username);
        returnToMainMenu(console); // Controls flow of program so main menu doesn't appear right away
    }

    /**
     * Allows user to creat a new profile in the network.
     * @param socialNetwork this instance of profileManger
     * @param console receives user input
     */
    public static void createProfile(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println();
        System.out.println("--- Create Profile ---");

        //Prompts the user to create a unique username
        String username;
        do {
            System.out.println("Create Profile User Name.");
            System.out.println("This will be a unique handle to identify your account in the network.");
            System.out.println("Must contain characters other than numbers.\n");
            System.out.print("Profile User Name?  ");
            username = checkUsername(console, allUsers);
        } while (username.isEmpty() || username == null || allUsers.contains(username) || username.matches("\\d+"));

        //Prompts the user to enter their name, does not need to be unique can be changed
        String name;
        do {
            System.out.print("Your Name?  ");
            if (console.hasNextInt() && console.nextInt() == 0) {
                System.out.println("\nCreate profile aborted. No new profile added.");
                return;
            }
            name = console.next();
            if (name.isEmpty() || name == null) {
                System.out.println("Name cannot be empty or null");
            }
        } while (name.isEmpty() || name == null);

        //Prompts user to enter a file name for their picture
        System.out.print("Picture File Name? ");
        if (console.hasNextInt() && console.nextInt() == 0) {
            System.out.println("\nCreate profile aborted. No new profile added.");
            return;
        }
        String picture = console.next();

        //Prompts user to enter a profile status
        String status;
        do {
            System.out.print("Profile Status? ");
            if (console.hasNextInt() && console.nextInt() == 0) {
                System.out.println("\nCreate profile aborted. No new profile added.");
                return;
            }
            status = console.next();
            if (status.isEmpty() || status == null) {
                System.out.println("Status cannot be empty or null.");
            }
        } while (status.isEmpty() || status == null);

        //Creates a profile with the new information and adds it to the network
        Profile newProfile = new Profile(username, name, picture, status);
        boolean didProfileAdd = socialNetwork.addProfile(newProfile);
        if (didProfileAdd) {
            System.out.println("Profile successfully added.");
        }

        returnToMainMenu(console);
    }


    /**
     * checks to make sure username fits the parameters
     * @param console receives user input
     * @param allUsers all users in the network
     * @return
     */
    private static String checkUsername(Scanner console, ArrayList<String> allUsers) {
        String username = console.next();
        if (username.isEmpty() || username == null) {
            System.out.println("Name cannot be empty or null.");
        } else if (allUsers.contains(username)) {
            System.out.println("Name already exists in network. Must choose a unique username.\n");

        } else if (username.matches("\\d+")) {
            System.out.println("Name cannot contain only digits.\n");
        }
        return username;
    }

    /**
     * Displays all usernames currently in the network.

     * @param socialNetwork this instance of profileManger
     * @param console receives user input
     */
    public static void viewAllProfiles(ProfileManager socialNetwork, Scanner console) {
        System.out.println("--- All Profiles in Social Network! ---");
        socialNetwork.displayProfiles();
        System.out.println();
        System.out.println();
        returnToMainMenu(console); // Controls flow of program so main menu doesn't appear right away
    }

    /**
     * Deletes a profile in the network.
     *
     * @param username current user's username
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     */
    public static void deleteProfile(String username, Scanner console, ProfileManager socialNetwork) {
        //Title
        System.out.println("--- Delete Profile ---");
        System.out.println("Current user: " + username);
        System.out.println();

        //Display current profiles in the network
        System.out.println("Current profiles in network: ");
        socialNetwork.displayProfiles();
        System.out.println();


        String userToDelete;
        System.out.println("\nWhich profile would you like to delete?");
        System.out.print("Enter username to delete or 0 to quit to main menu: ");
        if (console.hasNextInt() && console.nextInt() == 0) {
            System.out.println();
            return;
        }
        userToDelete = console.next();

        //If user tries to delete the profile that is currently logged in, it double-checks this action, and logs the
        //user out after deletion if they continue.
        if (userToDelete.equals(username)) {
            int mode = -1;
            do {
                System.out.println("\nYou are attempting to delete the current profile.");
                System.out.println("This profile will be removed and you will be logged out of Social Network if you continue.");
                System.out.println("1. To delete current profile and log out.");
                System.out.println("0. To return to main menu.");
                System.out.print("Choose 1 or 0 to quit to main menu: ");
                while (!console.hasNextInt()) {
                    console.next();
                    System.out.println();
                    System.out.println("That is not a valid option.");
                    System.out.println("1. To delete current profile and log out.");
                    System.out.println("0. To return to main menu.");
                    System.out.print("Choose 1 or 0 to quit to main menu: ");
                }
                mode = console.nextInt();

                switch (mode) {
                    case 1:
                        System.out.println(userToDelete + " was successfully removed.");
                        System.out.println("Logging " + userToDelete + " out.");
                        System.exit(0);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.print("Please choose an option: ");
                        break;
                }
            } while (mode != 0);
        } else { // deleting another profile in the network.
            boolean didUserDelete = socialNetwork.removeProfile(userToDelete);
            if (didUserDelete) {
                System.out.println(userToDelete + " was successfully removed.");
            } else {
                System.out.println("User " + userToDelete + " not found. No deletion executed.");
            }
        }
        returnToMainMenu(console); // Controls flow of program so main menu doesn't appear right away
    }


    /**
     * Switches the current user of the network.
     *
     * @param username current user
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     * @return username of user being switched to
     */
    public static String switchUser(String username, Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Switch user ---");
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println("List of users: ");
        socialNetwork.displayProfiles();
        System.out.println();
        System.out.print("Switch to which user or 0 to quit to main menu: ");
        if (console.hasNextInt() && console.nextInt() == 0) {
            System.out.println();
            return username;
        }
        String newUsername;
        do {
            if (console.hasNextInt() && console.nextInt() == 0) {
                System.out.println();
                return username;
            }
            newUsername = console.next();
            if (!allUsers.contains(newUsername)) {
                System.out.println("\n" + newUsername +  " is not in the system.");
                System.out.println("List of users: ");
                socialNetwork.displayProfiles();
                System.out.println();
                System.out.print("Switch to which user or 0 to quit to main menu: ");
            }
        } while (!allUsers.contains(newUsername));
        return newUsername;
    }

    /**
     * Private helper function that helps control the flow of the program. Prompts user to press a key to return
     * to the main menu instead of having it automatically appear.
     * @param console receives user input
     */
    private static void returnToMainMenu(Scanner console) {
        System.out.print("Choose 0 and press Enter to return to main menu: ");
        int menuMode;
        do {
            while (!console.hasNextInt()) {
                console.next();
                System.out.print("Invalid choice. Choose 0 and Press Enter to return to main menu: ");
            }

            menuMode = console.nextInt();
            if (menuMode == 0) {
                System.out.println("Returning to main menu...");
                return;
            } else {
                System.out.print("Invalid choice. Choose 0 and Press Enter to return to main menu: ");
            }
        } while (true);
    }

    /**
     * Separate method to for first time profile creation as it has different return type and slightly different wording
     * for some actions.
     * @param console receives user input
     * @param socialNetwork this instance of profileManger
     * @return username of profile created
     */
    public static String createProfileFirstTime(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println();
        System.out.println("--- Create Profile ---");

        //Prompts the user to create a unique username
        String username;
        do {
            System.out.println("Create Profile User Name.");
            System.out.println("This will be a unique handle to identify your account in the network.");
            System.out.println("Must contain characters other than numbers.\n");
            System.out.print("Profile User Name?  ");
            username = checkUsername(console, allUsers);
        } while (username.isEmpty() || username == null || allUsers.contains(username) || username.matches("\\d+"));

        //Prompts the user to enter their name, does not need to be unique can be changed
        String name;
        do {
            System.out.print("Your Name?  ");
            name = console.next();
            if (name.isEmpty() || name == null) {
                System.out.println("Name cannot be empty or null");
            }
        } while (name.isEmpty() || name == null);

        //Prompts user to enter a file name for their picture
        System.out.print("Picture File Name? ");
        String picture = console.next();

        //Prompts user to enter a profile status
        String status;
        do {
            System.out.print("Profile Status? ");
            status = console.next();
            if (status.isEmpty() || status == null) {
                System.out.println("Status cannot be empty or null.");
            }
        } while (status.isEmpty() || status == null);

        //Creates a profile with the new information and adds it to the network
        Profile newProfile = new Profile(username, name, picture, status);
        boolean didProfileAdd = socialNetwork.addProfile(newProfile);
        if (didProfileAdd) {
            System.out.println("Profile successfully added.");
        }
        return username;
    }
}
