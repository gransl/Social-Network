import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProfileManager socialNetwork = new ProfileManager();
        socialNetwork.populateInitialGraph();

        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to Social Network!");
        //TODO: add function that asks user to create profile
        menu(console, "Mustard", socialNetwork);
    }

    public static void menu(Scanner console, String user, ProfileManager socialNetwork) {
        //variables
        int mode = -1;
        String currentUser = user;

        //menu
        do {
            System.out.println();
            System.out.println("Current user: " + currentUser);
            System.out.println("Choose from the following options: ");
            System.out.println();
            System.out.println("1. Modify your profile");
            System.out.println("2. Add a friend");
            System.out.println("3. View your friends list");
            System.out.println("4. View your friends' friends list");
            System.out.println();
            System.out.println("5. Add a new profile");
            System.out.println("6. View all profiles");
            System.out.println("7. Delete a profile");
            System.out.println("8. Switch current user");
            System.out.println();
            System.out.println("0. Logout (exit program)");
            System.out.print("Choose an option 1-8 or 0 to quit: ");

            while (!console.hasNextInt()) {
                console.next();
                System.out.println();
                System.out.println("That is not a valid option.");
                System.out.print("Choose an option 1-8 or 0 to quit: ");
            }

            mode = console.nextInt();
            System.out.println();

            switch(mode) {
                case 1:
                    modifyProfile(currentUser, console, socialNetwork);
                    break;
                case 2:
                    addFriend(currentUser, console, socialNetwork);
                    break;
                case 3:
                    viewFriends(currentUser, socialNetwork);
                    break;
                case 4:
                    viewFriendsFriends(currentUser, socialNetwork);
                    break;
                case 5:
                    createProfile(console, socialNetwork);
                    break;
                case 6:
                    viewAllProfiles(socialNetwork);
                    break;
                case 7:
                    deleteProfile(console, socialNetwork);
                    break;
                case 8:
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

            switch (modifyMode) {
                case 1:
                    //TODO: change username
                    break;
                case 2:
                    //TODO: change profile picture
                    break;
                case 3:
                    //TODO: change current status
                    break;
                case 0:
                    break;
                default:
                    System.out.print("Please choose an option 1-3 or 0 to quit: ");
            }

        } while (modifyMode != 0);

    }

    //TODO: Not Finished
    public static void addFriend(String user, Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Add A Friend ---");
        System.out.println("Current Friendships: ");
        socialNetwork.displayCurrentUsersFriends(user);

        System.out.println();
        System.out.println();
        System.out.println("Possible Friends: ");
        Iterator<String> userItr = socialNetwork.getNameIterator();
        while (userItr.hasNext()) {
            String nextUser = userItr.next();
            if (nextUser != user);
            //TODO: Need a way to get current user friends list
        }
        System.out.println();
        System.out.println();
        System.out.println("Who do you want create a friendship with?" );
        String friend = console.next();
        boolean didFriendshipCreate = socialNetwork.createFriendship(user, friend);
        if (didFriendshipCreate) {
            System.out.println("Link Successful. " + user + " and " + friend + " are now friends.");
        } else {
            System.out.println("Link Unsuccessful. New friend must be an existing user and not currently a friend.");
        }
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

    //Need to add a way to check that it's also not a current Profile
    //TODO: Need a way to get all users that is a list.
    public static void createProfile(Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Create Profile ---");
        System.out.println();

        String name = "";
        do {
            System.out.println("Profile Name?  ");
            name = console.next();
            if (name.isEmpty() || name == null) {
                System.out.println("Name cannot be empty or null");
            }
        } while (name.isEmpty() || name == null );

        System.out.println();
        System.out.print("Picture File Name (Optional, can leave blank)? ");
        String picture = console.next();

        String status = "";
        do {
            System.out.println();
            System.out.print("Profile Status? ");
            status = console.next();
            if (status.isEmpty() || status == null) {
                System.out.println("Status cannot be empty or null.");
            }
        } while (status.isEmpty() || status == null);

        Profile newProfile = new Profile(name, picture, status);
        socialNetwork.addProfile(newProfile);

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
        System.out.println("List of users: ");
        socialNetwork.displayProfiles();
        System.out.println();
        System.out.print("Switch to which user: ");
        String user = console.next();
        return user;
    }

}
