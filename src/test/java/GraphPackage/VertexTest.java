package GraphPackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VertexTest {
    static Vertex v1;
    static Vertex v2;
    static Vertex v3;
    static Vertex v4;
    static Vertex v5;
    static Vertex v6;
    static Vertex v7;

    @BeforeAll
    static void setUp() {
        v1 = new Vertex("v1");
        v2 = new Vertex("v2");
        v3 = new Vertex("v3");
        v4 = new Vertex("v4");
        v5 = new Vertex("v5");
        v6 = new Vertex("v6");
        v7 = new Vertex("v7");
        v2.connect(v4, 1);
        v3.connect(v7, 2);
        v4.connect(v1, 8);
        v4.connect(v3, 3);
        v4.connect(v5, 4);
        v5.connect(v7, 5);
        v6.connect(v2, 6);
        v6.connect(v7);
        v3.visit();
        v5.visit();
        v7.visit();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLabel() {
        assertEquals("v2", v2.getLabel());
    }

    @Test
     void setAndHasPredecessor() {
        Vertex testVertex1 = new Vertex("Test1");
        Vertex testVertex2 = new Vertex("Test2");
        assertFalse(testVertex1.hasPredecessor());
        assertFalse(testVertex2.hasPredecessor());
        testVertex2.setPredecessor(testVertex1);
        assertFalse(testVertex1.hasPredecessor());
        assertTrue(testVertex2.hasPredecessor());
    }

    @Test
    void getPredecessor() {
        Vertex testVertex1 = new Vertex("Test1");
        Vertex testVertex2 = new Vertex("Test2");
        testVertex2.setPredecessor(testVertex1);
        assertEquals(testVertex1, testVertex2.getPredecessor());
        assertNull(testVertex1.getPredecessor());
    }

    @Test
    void visitUnvisitAndIsVisited() {
        Vertex testVertex1 = new Vertex("Test1");
        assertFalse(testVertex1.isVisited());
        testVertex1.visit();
        assertTrue(testVertex1.isVisited());
        testVertex1.unvisit();
        assertFalse(testVertex1.isVisited());
    }


    @Test
    void getAndSetCost() {
        Vertex testVertex1 = new Vertex("Test1");
        assertEquals(0, testVertex1.getCost());
        testVertex1.setCost(7);
        assertEquals(7, testVertex1.getCost());
        testVertex1.setCost(12);
        assertEquals(12, testVertex1.getCost());
    }


    @Test
    void testToString() {
        assertEquals("v2", v2.toString());
    }

    @Test
    void getWeightIterator() {
        Iterator<Double> weightItr = v4.getWeightIterator();
        assertTrue(weightItr.hasNext());
        assertEquals(8, weightItr.next());
        assertTrue(weightItr.hasNext());
        assertThrows(UnsupportedOperationException.class, ()-> weightItr.remove());
        assertEquals(3, weightItr.next());
        assertTrue(weightItr.hasNext());
        assertEquals(4, weightItr.next());
        assertFalse(weightItr.hasNext());
        assertThrows(NoSuchElementException.class, ()-> weightItr.next());
    }

    @Test
    void connect() {
        Vertex testVertex1 = new Vertex("Test1");
        Vertex testVertex2 = new Vertex("Test2");
        Vertex testVertex3 = new Vertex("Test3");
        assertTrue(testVertex1.connect(testVertex2));
        assertTrue(testVertex2.connect(testVertex3));
        assertFalse(testVertex1.connect(testVertex2));
        assertFalse(testVertex3.connect(testVertex3));
    }


    @Test
    void getNeighborIterator() {
        Iterator<Vertex> neighborItr = v4.getNeighborIterator();
        assertTrue(neighborItr.hasNext());
        assertEquals(v1, neighborItr.next());
        assertTrue(neighborItr.hasNext());
        assertThrows(UnsupportedOperationException.class, ()-> neighborItr.remove());
        assertEquals(v3, neighborItr.next());
        assertTrue(neighborItr.hasNext());
        assertEquals(v5, neighborItr.next());
        assertFalse(neighborItr.hasNext());
        assertThrows(NoSuchElementException.class, ()-> neighborItr.next());
    }

    @Test
    void hasNeighbor() {
        assertTrue(v2.hasNeighbor());
        assertFalse(v7.hasNeighbor());
    }

    @Test
    void getUnvisitedNeighbor() {
       assertEquals(v1, v4.getUnvisitedNeighbor());
       assertNull(v5.getUnvisitedNeighbor());

    }

    @Test
    void testEquals() {
        Vertex testVertex1 = new Vertex("Test1");
        Vertex testVertexOne = new Vertex("Test1");
        Vertex testVertex2 = new Vertex("Test2");
        String hello = "hello!";
        assertTrue(testVertex1.equals(testVertex1));
        assertFalse(testVertex1.equals(testVertex2));
        assertTrue(testVertex1.equals(testVertexOne));
        assertFalse(testVertex1.equals(null));
        assertFalse(testVertex1.equals(hello));
    }

    @Test
    void display() {
        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        v2.display();

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        assertEquals("v2 v4 1.0 \n", testOutString);
    }
}