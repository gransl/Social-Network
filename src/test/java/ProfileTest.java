import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    static Profile profileOne;

    @BeforeEach
    void setUp() {
        profileOne = new Profile("Jim","Jim.PNG","ONLINE");
    }

    @AfterEach
    void tearDown() {
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
        profileOne.addFriend("Angela");
        profileOne.addFriend("Bob");
        profileOne.addFriend("Sara");

        assertEquals("Angela",profileOne.getFriendsList().get(0));
        assertEquals("Bob",profileOne.getFriendsList().get(1));
        assertEquals("Sara",profileOne.getFriendsList().get(2));
    }

    @Test
    void setName() {
        profileOne.setName("Fred");
        assertEquals("Fred",profileOne.getName());
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
}