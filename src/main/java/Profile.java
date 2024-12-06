import java.util.ArrayList;

/**
 * Profile class is an object referencing a persons profile on a social media network
 * holding information about the user
 */
public class Profile {
    /** Username of the profile user */
    private final String username;
    /** Name of the profile user */
    private String name;
    /** File Name for the profile picture */
    private String picture;
    /** Status of the User, can be any String, but usually Active/Inactive.*/
    private String status;
    /** List of profile user's friends*/
    private ArrayList<String> friendsList;

    /**
     * Full Constructor
     * @param name name of the Profile, cannot be empty or null.
     * @param picture filename for the picture
     * @param status status string, cannot be empty or null.
     */
    public Profile(String username, String name, String picture, String status) {
        if (username == null || username.isEmpty() || username.matches("\\d+")) {
            throw new IllegalArgumentException("Username cannot be null, empty, or contains only numbers");
        }

        this.username = username;
        setName(name);
        this.picture = picture;
        setStatus(status);
        friendsList = new ArrayList<>();
    }

    // GETTER METHODS

    /**
     * Returns the username associated with the Profile
     *
     * @return username of the profile
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the name associated with the Profile
     *
     * @return name of the profile
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the file name of the picture associated with the Profile.
     *
     * @return file name of the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Returns the current status of the profile
     *
     * @return status of profile
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the list of friends associated with this Profile.
     *
     * @return list of friends
     */
    public ArrayList<String> getFriendsList() {
        return friendsList;
    }

    // SETTER METHODS

    /**
     * Sets a new name to be associated with the Profile.
     *
     * @param name new name for Profile, must not be empty or null
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty or null");
        }
        this.name = name;
    }

    /**
     * Sets a new picture file name to be associated with the Profile. This can be left empty
     * as profile picture is optional.
     *
     * @param picture new file name for picture, can be empty
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Sets a new current status for the Profile.
     *
     * @param status new status, must not be empty or null
     */
    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty or null");
        }
        this.status = status;
    }

    /**
     * This method adds friends to your friendsList
     * @param friend a String referencing another profiles name
     */
    public void addFriend(String friend) {
        if(!friendsList.contains(friend) && friend != null && !friend.isEmpty()) {
            friendsList.add(friend);
        }
    }

    /**
     * This method displays the profile information
     */
    public void displayInformation() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Picture: " + picture);
        System.out.println("Status: " + status);
        System.out.println("Friends: " + friendsList);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", status='" + status + '\'' +
                ", friendsList=" + friendsList +
                '}';
    }
}
