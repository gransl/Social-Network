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
            System.out.println("4. View your friends' friend list");
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
                    modifyProfile(currentUser, console);
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
                    createProfile(console);
                    break;
                case 6:
                    viewAllProfiles(socialNetwork);
                    break;
                case 7:
                    deleteProfile(console);
                    break;
                case 8:
                    currentUser = switchUser(console, socialNetwork);
                    break;
                default:
                    //System.out.print("Please choose an option 1-8 or 0 to quit: ");
                    break;
            }
         } while (mode != 0);
    }



    public static void modifyProfile(String user, Scanner console) {
        int modifyMode = -1;
        do {

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
        //TODO: Iterate through the friends instead of displaying all so I can remove current friends and self
        socialNetwork.displayProfiles();
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

    public static void viewFriends(String user, ProfileManager socialNetwork) {
        System.out.println("--- " + user + "'s Friends List ---");
        socialNetwork.displayCurrentUsersFriends(user);
        System.out.println();
    }

    public static void viewFriendsFriends(String user, ProfileManager socialNetwork) {
        System.out.println("--- " + user + "'s Friends' Friends List ---");
        socialNetwork.displayCurrentUsersFriendsFriends(user);
    }

    public static void createProfile(Scanner console) {
        System.out.println("--- Delete Profile ---");
        System.out.println("Which profile do you want to delete?");

    }

    public static void viewAllProfiles(ProfileManager socialNetwork) {
        System.out.println("--- All Profiles in Social Network! ---");
        socialNetwork.displayProfiles();
        System.out.println();
    }

    public static void deleteProfile(Scanner console, ProfileManager socialNetwork) {
        System.out.println("--- Delete Profile ---");
        System.out.println("Which profile would you like to delete?");
        socialNetwork.displayProfiles();
        String userToDelete = console.next();

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
