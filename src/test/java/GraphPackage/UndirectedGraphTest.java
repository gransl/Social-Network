package GraphPackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UndirectedGraphTest {
    static DirectedGraph<Double> emptyGraph;
    static DirectedGraph<Integer> intGraph;

    @BeforeAll
    static void setUp() {
        emptyGraph = new UndirectedGraph<>();
        intGraph = new UndirectedGraph<>();
        assertTrue(intGraph.addVertex(0));
        assertTrue(intGraph.addVertex(1));
        assertTrue(intGraph.addVertex(2));
        assertTrue(intGraph.addVertex(3));
        assertTrue(intGraph.addVertex(4));
        assertTrue(intGraph.addVertex(5));
        assertTrue(intGraph.addVertex(6));
        assertTrue(intGraph.addEdge(0,1));
        assertTrue(intGraph.addEdge(1,2));
        assertTrue(intGraph.addEdge(2,3));
        assertTrue(intGraph.addEdge(3,0));
    }

    @Test
    void addEdge() {
        assertTrue(intGraph.addEdge(1,5,10));
    }

    @Test
    void testAddEdge() {
        assertTrue(intGraph.addEdge(0,4));
    }

    @Test
    void getNumberOfEdges() {
        assertEquals(6, intGraph.getNumberOfEdges());
        assertEquals(0, emptyGraph.getNumberOfEdges());
    }

    @Test
    void getTopologicalOrder() {
        assertThrows(UnsupportedOperationException.class, ()-> intGraph.getTopologicalOrder());
    }
}