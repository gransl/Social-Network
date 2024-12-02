public class temp {
    public static void main(String[] args) {
        ProfileManager profileManager = new ProfileManager();
        profileManager.populateInitialGraph();
        //profileManager.displayProfiles();
        //profileManager.displayCurrentUsersFriends("mustard");
        //profileManager.displayCurrentUsersFriendsFriends("mustard");
        //profileManager.availableFriends("boddy");
        profileManager.removeProfile("mustard");
        profileManager.getProfile("scarlet").displayInformation();
    }
}
