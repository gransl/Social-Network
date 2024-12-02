import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    static Profile profileOne;

    @BeforeEach
    void setUp() {
        profileOne = new Profile("jim", "Jim","Jim.PNG","ONLINE");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUsername() {
        assertEquals("jim",profileOne.getUsername());
    }

    @Test
    void getName() {
        assertEquals("Jim",profileOne.getName());
    }

    @Test
    void getPicture() {
        assertEquals("Jim.PNG",profileOne.getPicture());
    }

    @Test
    void getStatus() {
        assertEquals("ONLINE",profileOne.getStatus());
    }

    @Test
    void getFriendsList() {
        profileOne.addFriend("angela");
        profileOne.addFriend("bob");
        profileOne.addFriend("sara");

        assertEquals("angela",profileOne.getFriendsList().get(0));
        assertEquals("bob",profileOne.getFriendsList().get(1));
        assertEquals("sara",profileOne.getFriendsList().get(2));
    }

    @Test
    void setName() {
        profileOne.setName("Fred");
        assertEquals("Fred",profileOne.getName());
        assertThrows(IllegalArgumentException.class, () -> profileOne.setName(""));
        assertThrows(IllegalArgumentException.class, () -> profileOne.setName(null));
    }

    @Test
    void setPicture() {
        profileOne.setPicture("newPic.PNG");
        assertEquals("newPic.PNG",profileOne.getPicture());
    }

    @Test
    void setStatus() {
        profileOne.setStatus("OFFLINE");
        assertEquals("OFFLINE",profileOne.getStatus());
        assertThrows(IllegalArgumentException.class, () -> profileOne.setStatus(""));
        assertThrows(IllegalArgumentException.class, () -> profileOne.setStatus(null));
    }

    @Test
    void addFriend() {
        assertTrue(profileOne.getFriendsList().isEmpty());

        profileOne.addFriend("Angela");
        profileOne.addFriend("Bob");
        profileOne.addFriend("Sara");

        assertFalse(profileOne.getFriendsList().isEmpty());
        assertEquals(3, profileOne.getFriendsList().size());

        profileOne.addFriend("Angela");
        assertEquals(3, profileOne.getFriendsList().size());

        profileOne.addFriend(null);
        assertEquals(3, profileOne.getFriendsList().size());

        assertEquals("Angela",profileOne.getFriendsList().get(0));
        assertEquals("Bob",profileOne.getFriendsList().get(1));
        assertEquals("Sara",profileOne.getFriendsList().get(2));

        profileOne.addFriend("");
        assertEquals(3, profileOne.getFriendsList().size());

        assertEquals("Angela",profileOne.getFriendsList().get(0));
        assertEquals("Bob",profileOne.getFriendsList().get(1));
        assertEquals("Sara",profileOne.getFriendsList().get(2));
    }

    @Test
    void testToString() {
        profileOne.addFriend("Angela");
        profileOne.addFriend("Bob");
        profileOne.addFriend("Sara");

        String toString = profileOne.toString();

        assertTrue(toString.contains("Jim"));
        assertTrue(toString.contains("Jim.PNG"));
        assertTrue(toString.contains("ONLINE"));
        assertTrue(toString.contains("Angela"));
        assertTrue(toString.contains("Bob"));
        assertTrue(toString.contains("Sara"));
    }

    @Test
    void displayInformation() {
        profileOne.addFriend("Angela");

        //Save original out
        PrintStream originalOut = System.out;

        // Create ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut =  new PrintStream(outputStream);

        // Redirect System.out to testOut
        System.setOut(testOut);

        // Run method that prints code we want to test
        profileOne.displayInformation();

        // Restore original System.out
        System.setOut(System.out);

        String testOutString = outputStream.toString();
        assertEquals("Name: Jim\nPicture: Jim.PNG\nStatus: ONLINE\nFriends: [Angela]\n", testOutString);
    }
}