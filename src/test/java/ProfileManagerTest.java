import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ProfileManagerTest {

    private ProfileManager profileManager;

    @BeforeEach
    void setUp() {
        ProfileManager profileManager = new ProfileManager();
        profileManager.populateInitialGraph();
    }

    @AfterEach
    void tearDown() {
        profileManager = null;
    }

    @Test
    void populateInitialGraph() {
    }

    @Test
    void addProfile() {
    }

    @Test
    void createFriendship() {
    }

    @Test
    void displayProfiles() {
    }

    @Test
    void displayCurrentUsersFriends() {
    }

    @Test
    void displayCurrentUsersFriendsFriends() {
    }

    @Test
    void displayAllFriends() {
    }
}