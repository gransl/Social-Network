public class temp {
    public static void main(String[] args) {
        ProfileManager profileManager = new ProfileManager();
        profileManager.populateInitialGraph();
        profileManager.displayAllFriends("Mustard");

        profileManager.displayProfiles();
    }
}
