import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public static void menu(Scanner console, String username, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(username);
        //variables
        int mode = -1;

        //menu
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

            while (!console.hasNextInt()) {
                console.next();
                System.out.println();
                System.out.println("That is not a valid option.");
                System.out.print("Choose an option 1-9 or 0 to quit: ");
            }

            mode = console.nextInt();
            System.out.println();

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
                    username = switchUser(console, socialNetwork);
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

    public static void viewProfile(String username, ProfileManager socialNetwork, Scanner console) {
        Profile currentUser = socialNetwork.getProfile(username);
        System.out.println("--- " + currentUser.getName() + "'s Profile ---");
        currentUser.displayInformation();
        System.out.println();
        returnToMainMenu(console);
    }

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

    private static String changeName(String username, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(username);
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        String newName = "";
        do {
            System.out.println("Current Profile Name: " + userProfile.getName());
            System.out.println("(Re-enter current profile name to exit without making changes)");
            System.out.print("New Profile Name: ");
            newName = console.next();
            if (newName.isEmpty() || newName == null) {
                System.out.println("Profile name cannot be empty or null");
                System.out.print("Enter new profile name or type current profile name ("
                                        + userProfile.getName() + ") to exit: ");
            }
        } while (newName.isEmpty() || newName == null);

        userProfile.setName(newName);
        return newName;
    }

    private static void changePicture(String user, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(user);
        System.out.println("Current Picture File Name: " + userProfile.getPicture());
        System.out.println("(Re-enter current picture file name to exit without making changes)");
        System.out.print("New Picture File Name: ");
        String newPicture = console.next();
        userProfile.setPicture(newPicture);
    }

    private static void changeStatus(String user, Scanner console, ProfileManager socialNetwork) {
        Profile userProfile = socialNetwork.getProfile(user);
        String newStatus = "";
        do {
            System.out.println("Current Profile Status: " + userProfile.getStatus());
            System.out.println("(Re-enter current profile status to exit without making changes)");
            System.out.print("New Profile Status: ");
            newStatus = console.next();
            if (newStatus.isEmpty() || newStatus == null) {
                System.out.println("Profile Status cannot be empty or null.");
                System.out.print("Enter New Profile Status: ");
            }
        } while (newStatus.isEmpty() || newStatus == null);

        userProfile.setStatus(newStatus);

    }

    public static void addFriend(String user, Scanner console, ProfileManager socialNetwork) {
        String[] notYetFriends = createNotFriendsList(socialNetwork, user);

        System.out.println("--- Add A Friend ---");
        System.out.println("Current Friendships: ");
        socialNetwork.displayCurrentUsersFriends(user);
        System.out.println();
        System.out.println();

        if (notYetFriends.length < 1) {
            System.out.println("You are already friends with everyone in the network!");
        } else {
            System.out.println("Possible New Friends: ");
            System.out.println(Arrays.toString(notYetFriends));
            System.out.println();
            System.out.print("Who do you want create a friendship with? ");
            String friend = console.next();
            boolean didFriendshipCreate = socialNetwork.createFriendship(user, friend);
            if (didFriendshipCreate) {
                System.out.println("Link Successful. " + user + " and " + friend + " are now friends.");
            } else {
                System.out.println("Link Unsuccessful. New friend must be an existing user and not currently a friend.");
            }
        }
        returnToMainMenu(console);
    }

    private static String[] createNotFriendsList(ProfileManager socialNetwork, String user){
        // Populate List of Potential New Friends
        // Does not include self and current friends
        ArrayList<String> userFriends = socialNetwork.getFriendsList(user);
        Iterator<String> userItr = socialNetwork.getNameIterator();
        ArrayList<String> notFriends = new ArrayList<>();

        while (userItr.hasNext()) {
            String nextUser = userItr.next();
            if (nextUser != user && !userFriends.contains(nextUser)) {
                notFriends.add(nextUser);
            }
        }

        return notFriends.toArray(new String[notFriends.size()]);
    }

    //works
    public static void viewFriends(String user, ProfileManager socialNetwork, Scanner console) {
        System.out.println("--- " + user + "'s Friends List ---");
        socialNetwork.displayCurrentUsersFriends(user);
        System.out.println();
        returnToMainMenu(console);
    }

    //works
    public static void viewFriendsFriends(String user, ProfileManager socialNetwork, Scanner console) {
        System.out.println("--- " + user + "'s Friends' Friends List ---");
        socialNetwork.displayCurrentUsersFriendsFriends(user);
        returnToMainMenu(console);
    }


    public static String createProfile(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println();
        System.out.println("--- Create Profile ---");

        //Prompts the user to create a unique username
        String username = "";
        do {
            System.out.println("Create Profile User Name.");
            System.out.println("This will be a unique handle to identify your account in the network.");
            System.out.print("Profile User Name?  ");
            username = console.next();
            if (username.isEmpty() || username == null) {
                System.out.println("Name cannot be empty or null");
            } else if (allUsers.contains(username)) {
                System.out.println("Name already exists in network");
            }
        } while (username.isEmpty() || username == null || allUsers.contains(username));

        //Prompts the user to enter their name, does not need to be unique can be changed
        String name = "";
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
        String status = "";
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

        //Since this has a return value, specialized return to main
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
                return username;
            } else {
                System.out.print("Invalid choice. Choose 0 and Press Enter to return to main menu: ");
            }
        } while (true);
    }

    //works
    public static void viewAllProfiles(ProfileManager socialNetwork, Scanner console) {
        System.out.println("--- All Profiles in Social Network! ---");
        socialNetwork.displayProfiles();
        System.out.println();
        returnToMainMenu(console);
    }

    //works
    public static void deleteProfile(String username, Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Delete Profile ---");
        System.out.println("Current user: " + username);
        System.out.println();
        System.out.println("Current profiles in network: ");
        socialNetwork.displayProfiles();
        System.out.println();

        String userToDelete = "";
        System.out.print("\nWhich profile would you like to delete? ");
        userToDelete = console.next();

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
                        boolean didUserDelete = socialNetwork.removeProfile(userToDelete);
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
        } else {
            boolean didUserDelete = socialNetwork.removeProfile(userToDelete);
            if (didUserDelete) {
                System.out.println(userToDelete + " was successfully removed.");
            } else {
                System.out.println("User " + userToDelete + " not found. No deletion executed.");
            }
        }
        returnToMainMenu(console);
    }

    //works
    public static String switchUser(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println("List of users: ");
        socialNetwork.displayProfiles();
        System.out.println();
        System.out.print("Switch to which user: ");
        String user = "";
        do {
            user = console.next();
            if (!allUsers.contains(user)) {
                System.out.println("\n" + user +  " is not in the system.");
                System.out.println("List of users: ");
                socialNetwork.displayProfiles();
                System.out.println();
                System.out.print("Switch to which user: ");
            }
        } while (!allUsers.contains(user));
        return user;
    }

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

    public static String createProfileFirstTime(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println();
        System.out.println("--- Create Profile ---");

        //Prompts the user to create a unique username
        String username = "";
        do {
            System.out.println("Create Profile User Name.");
            System.out.println("This will be a unique handle to identify your account in the network.");
            System.out.print("Profile User Name?  ");
            username = console.next();
            if (username.isEmpty() || username == null) {
                System.out.println("Name cannot be empty or null");
            } else if (allUsers.contains(username)) {
                System.out.println("Name already exists in network");
            }
        } while (username.isEmpty() || username == null || allUsers.contains(username));

        //Prompts the user to enter their name, does not need to be unique can be changed
        String name = "";
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
        String status = "";
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
