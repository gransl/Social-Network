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

    public boolean createFriendship(String username1, String username2) {
        if(users.contains(username1) && users.contains(username2) && !users.getValue(username1).getFriendsList().contains(username2)) {
            socialNetwork.addEdge(username1, username2);
            users.getValue(username1).addFriend(username2);
            users.getValue(username2).addFriend(username1);
            return true;
        }
        return false;
    }

    public void displayProfiles() {
        Iterator<String> keyIterator = users.getKeyIterator();

        if (keyIterator.hasNext()) {
            String key = keyIterator.next();
            System.out.print(key);
            while (keyIterator.hasNext()) {
                key = keyIterator.next();
                System.out.print(", " + key);
            }
        }

    }

    public void displayCurrentUsersFriends(String username) {
        Profile profile = users.getValue(username);
        ArrayList<String> friends = profile.getFriendsList();

        if (!friends.isEmpty()) {
            for (int i = 0; i < friends.size(); i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(users.getValue(friends.get(i)).getName());
            }
        } else {
            System.out.println("No friends");
        }
    }

    public void displayCurrentUserFriendsUsername(String username) {
        Profile profile = users.getValue(username);
        ArrayList<String> friends = profile.getFriendsList();

        if (!friends.isEmpty()) {
            for (int i = 0; i < friends.size(); i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(users.getValue(friends.get(i)).getUsername());
            }
        } else {
            System.out.println("No friends");
        }
    }

    public void displayCurrentUsersFriendsFriends(String username) {
        Profile userProfile = users.getValue(username);
        ArrayList<String> userFriends = userProfile.getFriendsList();

        for (String friend : userFriends) {
            Profile friendProfile = users.getValue(friend);
            ArrayList<String> friendsFriends = friendProfile.getFriendsList();
            Iterator<String> friendsIterator = friendsFriends.iterator();
            StringBuilder friendsFriendsList = new StringBuilder();

            System.out.println(friendProfile.getName() + " Friends:");

            while (friendsIterator.hasNext()) {
                String name = friendsIterator.next();
                if (!name.equals(username)) {

                    if (!friendsFriendsList.isEmpty()) {
                        friendsFriendsList.append(", ");
                    }
                    friendsFriendsList.append(users.getValue(name).getName());
                }
            }


            System.out.println(friendsFriendsList);
            System.out.println();
            System.out.println();
        }
    }

    public void displayAllFriends(String originName) {
        QueueInterface<String>  traversalOrder = socialNetwork.getBreadthFirstTraversal(originName);

        while (!traversalOrder.isEmpty()) {
            String name = traversalOrder.dequeue();
            Profile profile = users.getValue(name);
            ArrayList<String> friends = profile.getFriendsList();

            System.out.println(name + " Friends:");
            for (int i = 0; i < friends.size(); i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print((users.getValue(friends.get(i)).getName()));
            }
            System.out.println();
            System.out.println();
        }
    }

    public Iterator<String> getNameIterator() {
        return users.getKeyIterator();
    }

    public Profile getProfile(String name) {
        return users.getValue(name);
    }

    public ArrayList<String> getFriendsList(String name) {
        return users.getValue(name).getFriendsList();
    }

    public ArrayList<String> getAllNamesList() {
        ArrayList<String> allNamesList = new ArrayList<>();
        Iterator<String> keyIterator = users.getKeyIterator();

        while (keyIterator.hasNext()) {
            allNamesList.add(keyIterator.next());
        }
        return allNamesList;
    }

    public void availableFriends(String username) {
        ArrayList<String> friendsList = users.getValue(username).getFriendsList();
        Iterator<String> keyIterator = users.getKeyIterator();
        StringBuilder availableFriends = new StringBuilder();

        while (keyIterator.hasNext()) {
            String name = keyIterator.next();
            if (!name.equals(username) && !friendsList.contains(name)) {

                if (!availableFriends.isEmpty()) {
                    availableFriends.append(", ");
                }
                availableFriends.append(name);
            }
        }

        System.out.println(availableFriends);
    }
}
