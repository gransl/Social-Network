import ADTPackage.QueueInterface;
import ADTPackage.UnsortedLinkedDictionary;
import GraphPackage.UndirectedGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class ProfileManager {
    private UnsortedLinkedDictionary<String, Profile> users;
    private UndirectedGraph<String> socialNetwork;

    public ProfileManager() {
        users = new UnsortedLinkedDictionary<>();
        socialNetwork = new UndirectedGraph<>();
    }

    public void populateInitialGraph() {
        Profile mustard = new Profile("Mustard", "mustard.png", "Active");
        Profile plum = new Profile("Plum", "plum.png", "Active");
        Profile peacock = new Profile("Peacock", "peacock.png", "Active");
        Profile green = new Profile("Green", "green.png", "Inactive");
        Profile white = new Profile("White", "white.png", "Active");
        Profile scarlet = new Profile("Scarlet", "scarlet.png", "Active");
        Profile boddy = new Profile("Boddy", "boddy.png", "Active");

        addProfile(mustard);
        addProfile(plum);
        addProfile(peacock);
        addProfile(green);
        addProfile(white);
        addProfile(scarlet);
        addProfile(boddy);

        createFriendship(mustard, plum);
        createFriendship(mustard, green);
        createFriendship(mustard, scarlet);

        createFriendship(plum, green);

        createFriendship(green, white);

        createFriendship(scarlet, peacock);
    }

    public boolean addProfile(Profile profile) {
        if(!users.contains(profile.getName())) {
            socialNetwork.addVertex(profile.getName());
            users.add(profile.getName(), profile);
            return true;
        }
        return false;
    }

    public boolean createFriendship(Profile profile1, Profile profile2) {
        if(users.contains(profile1.getName()) && users.contains(profile2.getName())) {
            socialNetwork.addEdge(profile1.getName(), profile2.getName());
            profile1.addFriend(profile2.getName());
            profile2.addFriend(profile1.getName());
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

    public void displayCurrentUsersFriends(String name) {
        Profile profile = users.getValue(name);
        ArrayList<String> friends = profile.getFriendsList();

        if (!friends.isEmpty()) {
            for (int i = 0; i < friends.size(); i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(friends.get(i));
            }
        } else {
            System.out.println("No friends");
        }
    }

    public void displayCurrentUsersFriendsFriends(String name) {
        Profile userProfile = users.getValue(name);
        ArrayList<String> userFriends = userProfile.getFriendsList();


        for (String friend : userFriends) {
            Profile friendProfile = users.getValue(friend);
            ArrayList<String> friendsFriends = friendProfile.getFriendsList();

            System.out.println(friend + " Friends:");
            for (int i = 1; i < friendsFriends.size(); i++) {
                if (i > 1) {
                    System.out.print(", ");
                }
                System.out.print(friendsFriends.get(i));
            }
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
                System.out.print(friends.get(i));
            }
            System.out.println();
            System.out.println();
        }
    }
}
