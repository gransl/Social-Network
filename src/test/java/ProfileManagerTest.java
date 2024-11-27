import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
class ProfileManagerTest {

    private ProfileManager profileManager;
    private Iterator<String> nameIterator;

    @BeforeEach
    void setUp() {
        ProfileManager profileManager = new ProfileManager();
        profileManager.populateInitialGraph();
        nameIterator = profileManager.getNameIterator();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void populateInitialGraph() {
        assertTrue(nameIterator.hasNext());


        assertEquals("Boddy", nameIterator.next());
        assertEquals("Scarlet", nameIterator.next());
        assertEquals("White", nameIterator.next());
        assertEquals("Green", nameIterator.next());
        assertEquals("Peacock", nameIterator.next());
        assertEquals("Plum", nameIterator.next());
        assertEquals("Mustard", nameIterator.next());
    }

    @Test
    void addProfile() {
        Profile profile = new Profile("Test","test.png","Active");

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