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
        String profileName = createProfile(console, socialNetwork);
        menu(console, profileName, socialNetwork);
    }

    public static void menu(Scanner console, String user, ProfileManager socialNetwork) {
        //variables
        int mode = -1;
        String currentUser = user;

        //menu
        do {
            System.out.println();
            System.out.println("--- Social Network! ---");
            System.out.println("Current user: " + currentUser);
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
                System.out.print("Choose an option 1-9 or 0 to quit.");
            }

            mode = console.nextInt();
            System.out.println();

            switch(mode) {
                case 1:
                    viewProfile(currentUser, socialNetwork);
                    break;
                case 2:
                    modifyProfile(currentUser, console, socialNetwork);
                    break;
                case 3:
                    addFriend(currentUser, console, socialNetwork);
                    break;
                case 4:
                    viewFriends(currentUser, socialNetwork);
                    break;
                case 5:
                    viewFriendsFriends(currentUser, socialNetwork);
                    break;
                case 6:
                    createProfile(console, socialNetwork);
                    break;
                case 7:
                    viewAllProfiles(socialNetwork);
                    break;
                case 8:
                    deleteProfile(console, socialNetwork);
                    break;
                case 9:
                    currentUser = switchUser(console, socialNetwork);
                    break;
                case 0:
                    break;
                default:
                    System.out.print("Please choose an option 1-8 or 0 to quit: ");
                    break;
            }
         } while (mode != 0);
    }

    public static void viewProfile(String user, ProfileManager socialNetwork) {
        System.out.println("--- " + user + "'s Profile ---");
        Profile currentUser = socialNetwork.getProfile(user);
        currentUser.displayInformation();
        System.out.println();
    }

    public static void modifyProfile(String user, Scanner console, ProfileManager socialNetwork) {
        int modifyMode = -1;
        System.out.println("--- Modify Profile ---");
        do {
            System.out.println("Current user: " + user);
            System.out.println("Select from the following: ");
            System.out.println("1. Change name");
            System.out.println("2. Change picture file name");
            System.out.println("3. Change current status");
            System.out.println();
            System.out.println("0. Quit to main menu");
            System.out.print("Please choose 1-3 or 0 to quit: ");

            while (!console.hasNextInt()) {
                console.next();
                System.out.println();
                System.out.println("That is not a valid option.");
                System.out.print("Choose an option 1-3 or 0 to quit: ");
            }

            modifyMode = console.nextInt();
            System.out.println();

            switch (modifyMode) {
                case 1:
                    user = changeName(user, console, socialNetwork);
                    break;
                case 2:
                    changePicture(user, console, socialNetwork);
                    break;
                case 3:
                    changeStatus(user, console, socialNetwork);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Please choose an option 1-3 or 0 to quit.");
                    System.out.println();
                    break;
            }

        } while (modifyMode != 0);

    }

    private static String changeName(String user, Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        String newName = "";
        do {
            System.out.print("New Profile Name: ");
            newName = console.next();
            if (newName.isEmpty() || newName == null) {
                System.out.println("Profile name cannot be empty or null");
                System.out.print("Enter new profile name or type current profile name (" + user + ") to exit: ");
            } else if (allUsers.contains(newName)) {
                System.out.println("This Profile Name already exists in network.");
                System.out.print("Enter new profile name or type current profile name (" + user + ") to exit: ");
            }
        } while (newName.isEmpty() || newName == null || (allUsers.contains(newName) && newName != user));

        Profile userProfile = socialNetwork.getProfile(user);
        userProfile.setName(newName);
        return newName;
    }

    private static void changePicture(String user, Scanner console, ProfileManager socialNetwork) {
        System.out.print("New Picture File Name: ");
        String newPicture = console.next();
        Profile userProfile = socialNetwork.getProfile(user);
        userProfile.setPicture(newPicture);
    }

    private static void changeStatus(String user, Scanner console, ProfileManager socialNetwork) {
        String newStatus = "";
        do {
            System.out.print("New Profile Name: ");
            newStatus = console.next();
            if (newStatus.isEmpty() || newStatus == null) {
                System.out.println("Profile status cannot be empty or null.");
                System.out.print("Enter new profile status: ");
            }
        } while (newStatus.isEmpty() || newStatus == null);

        Profile userProfile = socialNetwork.getProfile(user);
        userProfile.setName(newStatus);

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
    public static void viewFriends(String user, ProfileManager socialNetwork) {
        System.out.println("--- " + user + "'s Friends List ---");
        socialNetwork.displayCurrentUsersFriends(user);
        System.out.println();
    }

    //works
    public static void viewFriendsFriends(String user, ProfileManager socialNetwork) {
        System.out.println("--- " + user + "'s Friends' Friends List ---");
        socialNetwork.displayCurrentUsersFriendsFriends(user);
    }


    public static String createProfile(Scanner console, ProfileManager socialNetwork) {
        ArrayList<String> allUsers = socialNetwork.getAllNamesList();
        System.out.println("--- Create Profile ---");

        String name = "";
        do {
            System.out.print("Profile Name?  ");
            name = console.next();
            if (name.isEmpty() || name == null) {
                System.out.println("Name cannot be empty or null");
            } else if (allUsers.contains(name)) {
                System.out.println("Name already exists in network");
            }
        } while (name.isEmpty() || name == null || allUsers.contains(name));

        System.out.print("Picture File Name? ");
        String picture = console.next();

        String status = "";
        do {
            System.out.print("Profile Status? ");
            status = console.next();
            if (status.isEmpty() || status == null) {
                System.out.println("Status cannot be empty or null.");
            }
        } while (status.isEmpty() || status == null);

        Profile newProfile = new Profile(name, picture, status);
        socialNetwork.addProfile(newProfile);
        return name;
    }

    //works
    public static void viewAllProfiles(ProfileManager socialNetwork) {
        System.out.println("--- All Profiles in Social Network! ---");
        socialNetwork.displayProfiles();
        System.out.println();
    }

    //works
    public static void deleteProfile(Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Delete Profile ---");
        System.out.println("Current profiles: ");
        socialNetwork.displayProfiles();
        System.out.println();
        System.out.print("Which profile would you like to delete? ");
        String userToDelete = console.next();
        boolean didUserDelete = socialNetwork.removeProfile(userToDelete);
        if (didUserDelete) {
            System.out.println(userToDelete + " was successfully removed.");
        } else {
            System.out.println("User " + userToDelete + " not found. No deletion executed.");
        }
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

}
