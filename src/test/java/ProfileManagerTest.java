import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
class ProfileManagerTest {

    private ProfileManager profileManager;
    private Iterator<String> nameIterator;

    @BeforeEach
    void setUp() {
        profileManager = new ProfileManager();
        profileManager.populateInitialGraph();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void populateInitialGraph() {
        ArrayList<String> allNames = profileManager.getAllNamesList();

        assertTrue(allNames.contains("mustard"));
        assertTrue(allNames.contains("plum"));
        assertTrue(allNames.contains("peacock"));
        assertTrue(allNames.contains("green"));
        assertTrue(allNames.contains("white"));
        assertTrue(allNames.contains("scarlet"));
        assertTrue(allNames.contains("boddy"));

        assertTrue(profileManager.getProfile("plum").getFriendsList().contains("mustard"));
        assertTrue(profileManager.getProfile("green").getFriendsList().contains("mustard"));
        assertTrue(profileManager.getProfile("scarlet").getFriendsList().contains("mustard"));

        assertTrue(profileManager.getProfile("green").getFriendsList().contains("plum"));

        assertTrue(profileManager.getProfile("white").getFriendsList().contains("green"));

        assertTrue(profileManager.getProfile("peacock").getFriendsList().contains("scarlet"));
    }

    @Test
    void addProfile() {
        Profile testProfile = new Profile("test", "Test", "test.png", "Active");

        assertTrue(profileManager.addProfile(testProfile));
        assertFalse(profileManager.addProfile(testProfile));
    }

    @Test
    void removeProfile() {
        assertTrue(profileManager.getProfile("plum").getFriendsList().contains("mustard"));
        assertTrue(profileManager.getProfile("green").getFriendsList().contains("mustard"));
        assertTrue(profileManager.getProfile("scarlet").getFriendsList().contains("mustard"));

        assertTrue(profileManager.removeProfile("mustard"));
        assertFalse(profileManager.removeProfile("mustard"));

        assertFalse(profileManager.getProfile("plum").getFriendsList().contains("mustard"));
        assertFalse(profileManager.getProfile("green").getFriendsList().contains("mustard"));
        assertFalse(profileManager.getProfile("scarlet").getFriendsList().contains("mustard"));
    }

    @Test
    void createFriendship() {
        Profile testProfile = new Profile("test", "Test", "test.png", "Active");

        assertTrue(profileManager.addProfile(testProfile));

        assertTrue(profileManager.createFriendship(testProfile.getUsername(), "mustard"));
        assertFalse(profileManager.createFriendship(testProfile.getUsername(), "mustard"));

        assertTrue(profileManager.getProfile("mustard").getFriendsList().contains(testProfile.getUsername()));
        assertTrue(profileManager.getProfile(testProfile.getUsername()).getFriendsList().contains("mustard"));
    }

    @Test
    void displayProfiles() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.displayProfiles();

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("boddy, scarlet, white, green, peacock, plum, mustard", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }

    @Test
    void displayCurrentUsersFriends() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.displayCurrentUsersFriends("mustard");

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("Plum, Green, Scarlet", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }

    @Test
    void displayCurrentUserFriendsUsername() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.displayCurrentUserFriendsUsername("mustard");

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("plum, green, scarlet", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }

    @Test
    void displayCurrentUsersFriendsFriends() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.displayCurrentUsersFriendsFriends("mustard");

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("Plum Friends:\n" +
                "Green\n" +
                "\n" +
                "\n" +
                "Green Friends:\n" +
                "Plum, White\n" +
                "\n" +
                "\n" +
                "Scarlet Friends:\n" +
                "Peacock", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }

    @Test
    void displayAllFriends() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.displayAllFriends("mustard");

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("mustard Friends:\n" +
                "Plum, Green, Scarlet\n" +
                "\n" +
                "plum Friends:\n" +
                "Mustard, Green\n" +
                "\n" +
                "green Friends:\n" +
                "Mustard, Plum, White\n" +
                "\n" +
                "scarlet Friends:\n" +
                "Mustard, Peacock\n" +
                "\n" +
                "white Friends:\n" +
                "Green\n" +
                "\n" +
                "peacock Friends:\n" +
                "Scarlet", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }

    @Test
    void getNameIterator() {
        nameIterator = profileManager.getNameIterator();
        assertNotNull(nameIterator);
        ArrayList<String> allNames = profileManager.getAllNamesList();

        while (nameIterator.hasNext()) {
            String name = nameIterator.next();
            assertTrue(allNames.contains(name));
        }
    }

    @Test
    void getProfile() {
        Profile testProfile = new Profile("test", "Test", "test.png", "Active");
        profileManager.addProfile(testProfile);

        assertNotNull(profileManager.getProfile("test"));

        assertEquals("test", profileManager.getProfile("test").getUsername());
        assertEquals("Test", profileManager.getProfile("test").getName());
        assertEquals("test.png", profileManager.getProfile("test").getPicture());
        assertEquals("Active", profileManager.getProfile("test").getStatus());
    }

    @Test
    void getFriendsList() {
        assertTrue(profileManager.getProfile("mustard").getFriendsList().contains("plum"));
        assertTrue(profileManager.getProfile("mustard").getFriendsList().contains("green"));
        assertTrue(profileManager.getProfile("mustard").getFriendsList().contains("scarlet"));
    }

    @Test
    void getAllNamesList() {
        nameIterator = profileManager.getNameIterator();
        assertNotNull(nameIterator);
        ArrayList<String> allNames = profileManager.getAllNamesList();

        while (nameIterator.hasNext()) {
            String name = nameIterator.next();
            assertTrue(allNames.contains(name));
        }
    }

    @Test
    void availableFriends() {
        // Set up the output stream to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.out to our custom PrintStream
        System.setOut(printStream);

        // Create an instance of the class and call the print method
        profileManager.availableFriends("mustard");

        // Verify the captured output
        String printedOutput = outputStream.toString().trim(); // Trim to remove any extra newlines
        assertEquals("boddy, white, peacock", printedOutput);

        // Reset System.out back to the original stream to avoid affecting other tests
        System.setOut(System.out);
    }
}