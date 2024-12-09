import ADTPackage.QueueInterface;
import ADTPackage.UnsortedLinkedDictionary;
import GraphPackage.UndirectedGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class ProfileManager {
    /** Unsorted Linked Dictionary storing profiles
     * with the key being the profiles username */
    private UnsortedLinkedDictionary<String, Profile> users;
    /** Undirected Graph storing usernames of profiles */
    private UndirectedGraph<String> socialNetwork;

    /**
     * Full Constructor
     */
    public ProfileManager() {
        users = new UnsortedLinkedDictionary<>();
        socialNetwork = new UndirectedGraph<>();
    }

    /**
     * Populates the social network with seven different profiles
     * and connect them with each other as friends as seen in the UML diagram
     */
    public void populateInitialGraph() {
        // Create the seven different profiles
        Profile mustard = new Profile("mustard", "Mustard", "mustard.png", "Active");
        Profile plum = new Profile("plum", "Plum", "plum.png", "Active");
        Profile peacock = new Profile("peacock", "Peacock", "peacock.png", "Active");
        Profile green = new Profile("green", "Green", "green.png", "Inactive");
        Profile white = new Profile("white", "White", "white.png", "Active");
        Profile scarlet = new Profile("scarlet", "Scarlet", "scarlet.png", "Active");
        Profile boddy = new Profile("boddy", "Boddy", "boddy.png", "Active");

        // Add the profiles to the social network
        addProfile(mustard);
        addProfile(plum);
        addProfile(peacock);
        addProfile(green);
        addProfile(white);
        addProfile(scarlet);
        addProfile(boddy);

        // Connect the profiles as friends
        createFriendship(mustard.getUsername(), plum.getUsername());
        createFriendship(mustard.getUsername(), green.getUsername());
        createFriendship(mustard.getUsername(), scarlet.getUsername());

        createFriendship(plum.getUsername(), green.getUsername());

        createFriendship(green.getUsername(), white.getUsername());

        createFriendship(scarlet.getUsername(), peacock.getUsername());
    }

    /**
     * Adds profile to the social network
     *
     * @return boolean true if the profile has been added
     * and false if the profile has already been added
     */
    public boolean addProfile(Profile profile) {

        // Check if the given username already exists in the social network
        if(!users.contains(profile.getUsername())) {

            // Add the username to the socialNetwork
            socialNetwork.addVertex(profile.getUsername());
            // Add the username as a key to the users and the given profile as a value
            users.add(profile.getUsername(), profile);
            return true; // Return True because the profile has been added
        }

        return false; // Return false because the profile wasn't added
    }

    /**
     * Removes a profile from the social network
     *
     * @param username a username of a profile to remove
     * @return boolean true if the user has been remove
     * false if the given username doesn't exist in the social network
     */
    public boolean removeProfile(String username) {

        // Check if users contains the given username
        if(users.contains(username)) {

            // Create a profile to remove getting the profile value from users with the username as the key
            Profile profileToRemove = users.getValue(username);
            // Get the profiles list of friends
            ArrayList<String> friendsList = profileToRemove.getFriendsList();

            // Loop through each of the profile friends
            for (String friend : friendsList) {
                // Create a profile of the friend
                Profile friendProfile = users.getValue(friend);
                // Remove the given username from the list of friends of the friend profile
                friendProfile.getFriendsList().remove(username);
            }

            // Remove the username from users and socialNetwork
            // Return true because the profile has been removed
            users.remove(username);
            socialNetwork.removeVertex(username);
            return true;
        }

        // Return false because the given username doesn't exist in the social network
        // and cannot be removed
        return false;
    }

    /**
     * Create a friendship between two profiles
     *
     * @param username1 a username of a profile to connect
     * @param username2 a username of a profile to connect
     * @return boolean true if the friends have been connected and false if they have not
     */
    public boolean createFriendship(String username1, String username2) {
        // Check if both profiles exist in the social network
        // and check that the two profiles aren't already friends
        if(users.contains(username1) && users.contains(username2) && !users.getValue(username1).getFriendsList().contains(username2)) {

            // Connect the usernames in the graph
            socialNetwork.addEdge(username1, username2);

            // Add each username to the others friends list
            users.getValue(username1).addFriend(username2);
            users.getValue(username2).addFriend(username1);

            return true; // Return true the connection has been made
        }

        return false; // Return false the connection has not been made
    }

    /**
     * Displays all profiles in the social network
     * This method prints out every users username seperated by commas
     */
    public void displayProfiles() {
        // Open an iterator for the keys in the dictionary
        Iterator<String> keyIterator = users.getKeyIterator();

        // Loop through all the keys in the dictionary
        if (keyIterator.hasNext()) {

            // Print out the key
            String key = keyIterator.next();
            System.out.print(key);

            // Print out a comma in between the keys
            while (keyIterator.hasNext()) {
                key = keyIterator.next();
                System.out.print(", " + key);
            }
        }

    }

    /**
     * Displays the current users friends names
     * This method prints out all the current users friends names seperated by commas
     *
     * @param username a username representing the current user of the social network
     */
    public void displayCurrentUsersFriends(String username) {
        // Get the current users profile from the dictionary
        Profile profile = users.getValue(username);
        // Create an ArrayList of the current users friends
        ArrayList<String> friends = profile.getFriendsList();

        // Check if the user has friends
        if (!friends.isEmpty()) {

            // Loop through the users friends
            for (int i = 0; i < friends.size(); i++) {

                // Print out each friends username seperated by commas
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(users.getValue(friends.get(i)).getName());
            }
        } else {

            // If the current user does not have friends print out no friends
            System.out.println("No friends");
        }
    }

    /**
     * Displays the current users friends usernames
     * This method prints out all the current users friends usernames seperated by commas
     *
     * @param username a username representing the current user of the social network
     */
    public void displayCurrentUserFriendsUsername(String username) {
        // Get the current users profile from the dictionary
        Profile profile = users.getValue(username);
        // Create an ArrayList of the current users friends
        ArrayList<String> friends = profile.getFriendsList();

        // Check if the user has friends
        if (!friends.isEmpty()) {

            // Loop through the users friends
            for (int i = 0; i < friends.size(); i++) {

                // Print out each friends username seperated by commas
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(users.getValue(friends.get(i)).getUsername());
            }
        } else {

            // If the current user does not have friends print out no friends
            System.out.println("No friends");
        }
    }

    /**
     * Displays the current users friends friends
     * This method prints out the users friends followed by that friends friends names
     *
     * @param username
     */
    public void displayCurrentUsersFriendsFriends(String username) {
        // Get the current users profile from the dictionary
        Profile userProfile = users.getValue(username);
        // Create an ArrayList of the current users friends
        ArrayList<String> userFriends = userProfile.getFriendsList();

        // Loop through each of the users friends
        for (String friend : userFriends) {

            // Get the friends profile from the dictionary
            Profile friendProfile = users.getValue(friend);
            // Create an ArrayList of the friends friends
            ArrayList<String> friendsFriends = friendProfile.getFriendsList();

            // Open an iterator on the friends friend list
            Iterator<String> friendsIterator = friendsFriends.iterator();
            // Create a string builder that will be filled with the friends friends names seperated by commas
            StringBuilder friendsFriendsList = new StringBuilder();

            // Print out the friends name followed by friends on its own line
            System.out.println(friendProfile.getName() + " Friends:");

            // Loop through the friends friend list
            while (friendsIterator.hasNext()) {

                // Add the name of the friends friend to the string builder seperated by commas
                String name = friendsIterator.next();
                if (!name.equals(username)) {

                    if (!friendsFriendsList.isEmpty()) {
                        friendsFriendsList.append(", ");
                    }
                    friendsFriendsList.append(users.getValue(name).getName());
                }
            }

            // Display the string builder
            System.out.println(friendsFriendsList);

            // Print two spaces of lines to separate from the users next friend
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Displays all the friends for each user in a breadth-first traversal starting from the origin user.
     * The traversal order is determined by the breadth-first search algorithm.
     *
     * @param originName The username of the origin user from which the breadth-first traversal starts.
     */
    public void displayAllFriends(String originName) {
        // Get the breadth first starting from the given origin name
        QueueInterface<String>  traversalOrder = socialNetwork.getBreadthFirstTraversal(originName);

        // Loop through the breadth first traversal
        while (!traversalOrder.isEmpty()) {

            // Get the name from the breadth first traversal
            String name = traversalOrder.dequeue();

            // Get the names profile from the dictionary
            Profile profile = users.getValue(name);
            // Create an ArrayList of the names friends
            ArrayList<String> friends = profile.getFriendsList();

            // Print out the name follow by its friend
            System.out.println(name + " Friends:");
            for (int i = 0; i < friends.size(); i++) {

                // Print out the names friends seperated by commas
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print((users.getValue(friends.get(i)).getName()));
            }

            // Print two spaces of lines to separate from the traversals next user
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Returns an iterator for iterating over the usernames friends lists
     *
     * @return An iterator for the friends in the profiles friends list.
     */
    public Iterator<String> getNameIterator() {
        return users.getKeyIterator();
    }

    /**
     * Retrieves the profile of a user by their username from the dictionary
     * @param username a username to get the corresponding profile from
     * @return Profile with the given username
     */
    public Profile getProfile(String username) {
        return users.getValue(username);
    }

    /**
     * Retrieves the friends list of the given username
     *
     * @param username The username of a profile to retireve the friendsList from
     * @return An ArrayList of usernames representing the user's friends.
     */
    public ArrayList<String> getFriendsList(String username) {
        return users.getValue(username).getFriendsList();
    }


    /**
     * Retrieves a list of all usernames in the social network.
     *
     * @return An ArrayList containing all usernames.
     */
    public ArrayList<String> getAllNamesList() {
        // Create a ArrayList of usernames to return
        ArrayList<String> allNamesList = new ArrayList<>();
        // Open an iterator for the keys in the dictionary
        Iterator<String> keyIterator = users.getKeyIterator();

        // Loop through all the keys in the dictionary
        while (keyIterator.hasNext()) {

            // Add the key to the ArrayList
            allNamesList.add(keyIterator.next());
        }

        // Return the ArrayList of all the usernames in the social network
        return allNamesList;
    }

    /**
     * Displays all available friends for a given user. Available friends are users who are not already friends with the given user.
     *
     * @param username The username of the user for whom available friends will be displayed.
     */
    public void availableFriends(String username) {
        // Get the friends list of the current user
        ArrayList<String> friendsList = users.getValue(username).getFriendsList();
        // Open an iterator for the keys in the dictionary
        Iterator<String> keyIterator = users.getKeyIterator();
        // Create a string builder that will be filled with the available friends of the current user operated by commas
        StringBuilder availableFriends = new StringBuilder();

        // Loop through all the keys in the dictionary
        while (keyIterator.hasNext()) {
            String name = keyIterator.next();

            // Check if the user is already a friend of the current user or is the current user
            if (!name.equals(username) && !friendsList.contains(name)) {

                // Add the name to the available friends seperated by a comma
                if (!availableFriends.isEmpty()) {
                    availableFriends.append(", ");
                }
                availableFriends.append(name);
            }
        }

        // Print out the string builder of the available friends of the current user operated by commas
        System.out.println(availableFriends);
    }
}
