import ADTPackage.UnsortedLinkedDictionary;
import GraphPackage.UndirectedGraph;

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
    }
}
