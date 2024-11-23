import java.util.ArrayList;

/**
 * Profile class is an object referencing a persons profile on a social media network
 * holding information about the user
 */
public class Profile {
    private String name;
    private String picture;
    private String status;
    private ArrayList<String> friendsList;

    public Profile(String name, String picture, String status) {
        this.name = name;
        this.picture = picture;
        this.status = status;
        friendsList = new ArrayList<>();
    }

    // GETTER METHODS

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getFriendsList() {
        return friendsList;
    }

    // SETTER METHODS

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method adds friends to your friendsList
     * @param friend a String referencing another profiles name
     */
    public void addFriend(String friend) {
        if(!friendsList.contains(friend) && friend != null) {
            friendsList.add(friend);
        }
    }

    /**
     * toSting method
     * @return String containing profile information
     */
    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", status='" + status + '\'' +
                ", friendsList=" + friendsList +
                '}';
    }
}
