package GraphPackage;

import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DirectedGraphTest {
    static DirectedGraph<Double> emptyGraph;
    static DirectedGraph<Integer> intGraph;

    @BeforeAll
    static void setUp() {
        emptyGraph = new DirectedGraph<>();
        intGraph = new DirectedGraph<>();
        assertTrue(intGraph.addVertex(0));
        assertTrue(intGraph.addVertex(1));
        assertTrue(intGraph.addVertex(2));
        assertTrue(intGraph.addVertex(3));
        assertTrue(intGraph.addVertex(4));
        assertTrue(intGraph.addVertex(5));
        assertTrue(intGraph.addVertex(6));
        assertTrue(intGraph.addEdge(0,1,3));
        assertTrue(intGraph.addEdge(1,2,7));
        assertTrue(intGraph.addEdge(2,3,5));
        assertTrue(intGraph.addEdge(3,0,2));
        assertTrue(intGraph.addEdge(0,4,1));
        assertTrue(intGraph.addEdge(1,5));
    }

    @AfterEach
    void tearDown() {

    }


    @Test
    void addVertex() {
        DirectedGraph<String> strGraph = new DirectedGraph<>();
        assertTrue(strGraph.addVertex("A"));
        assertFalse(strGraph.addVertex("A"));
    }

    /* It's not possible to add a null key to this dictionary, so I can't test the
    case where beginVertex or endVertex would be null*/
    @Test
    void addEdge() {
        DirectedGraph<String> strGraph = new DirectedGraph<>();
        strGraph.addVertex("A");
        strGraph.addVertex("B");
        assertTrue(strGraph.addEdge("A","B"));
        assertFalse(strGraph.addEdge("A","A"));
    }

    @Test
    void testAddEdge() {
        DirectedGraph<String> strGraph = new DirectedGraph<>();
        strGraph.addVertex("A");
        strGraph.addVertex("B");
        assertTrue(strGraph.addEdge("A","B"));
    }

    /* It's not possible for a key to be null in this dictionary, so I can't test the
    case where beginVertex or endVertex would be null*/
    @Test
    void hasEdge() {
        assertTrue(intGraph.hasEdge(1,5));
        assertTrue(intGraph.hasEdge(3,0));
        assertFalse(intGraph.hasEdge(0,6));
    }

    @Test
    void isEmpty() {
        assertTrue(emptyGraph.isEmpty());
        assertFalse(intGraph.isEmpty());
    }

    DirectedGraph<String> populateGraph() {
        DirectedGraph<String> strGraph = new DirectedGraph<>();
        strGraph.addVertex("A");
        strGraph.addVertex("B");
        strGraph.addVertex("C");
        strGraph.addVertex("D");
        strGraph.addEdge("A","B");
        strGraph.addEdge("B","C");
        return strGraph;
    }

    @Test
    void clear() {
        DirectedGraph<String> strGraph = populateGraph();
        assertFalse(strGraph.isEmpty());
        strGraph.clear();
        assertTrue(strGraph.isEmpty());
    }

    @Test
    void getNumberOfVertices() {
        assertEquals(0, emptyGraph.getNumberOfVertices());
        assertEquals(7, intGraph.getNumberOfVertices());
    }

    @Test
    void getNumberOfEdges() {
        assertEquals(0, emptyGraph.getNumberOfEdges());
        assertEquals(6, intGraph.getNumberOfEdges());
    }

    @Test
    void resetVertices() {

    }

    /*Not actually implemented */
    @Test
    void getBreadthFirstTraversal() {
        QueueInterface<Integer> bfsTraversal = intGraph.getBreadthFirstTraversal(0);
    }

    @Test
    void getDepthFirstTraversal() {
        QueueInterface<Integer> dfsTraversal = intGraph.getDepthFirstTraversal(0);
        assertEquals(0, dfsTraversal.dequeue());
        assertEquals(1, dfsTraversal.dequeue());
        assertEquals(2, dfsTraversal.dequeue());
        assertEquals(3, dfsTraversal.dequeue());
        assertEquals(5, dfsTraversal.dequeue());
        assertEquals(4, dfsTraversal.dequeue());
    }

    DirectedGraph<Integer> populateAcyclicGraph() {
        DirectedGraph<Integer> acylicGraph = new DirectedGraph<>();
        acylicGraph.addVertex(1);
        acylicGraph.addVertex(2);
        acylicGraph.addVertex(3);
        acylicGraph.addVertex(4);
        acylicGraph.addVertex(5);
        acylicGraph.addVertex(6);
        acylicGraph.addVertex(7);
        acylicGraph.addVertex(8);
        acylicGraph.addEdge(1,2);
        acylicGraph.addEdge(2,3);
        acylicGraph.addEdge(2,5);
        acylicGraph.addEdge(3,4);
        acylicGraph.addEdge(5,6);
        acylicGraph.addEdge(5,7);
        acylicGraph.addEdge(7,8);
        acylicGraph.addEdge(6,8);
        return acylicGraph;
    }

    /* Need a graph without cycles to have a topological order */
    @Test
    void getTopologicalOrder() {
        DirectedGraph<Integer> acyclicGraph = populateAcyclicGraph();
        StackInterface<Integer> topologicalOrder = acyclicGraph.getTopologicalOrder();
        assertEquals(1, topologicalOrder.pop());
        assertEquals(2, topologicalOrder.pop());
        assertEquals(3, topologicalOrder.pop());
        assertEquals(4, topologicalOrder.pop());
        assertEquals(5, topologicalOrder.pop());
        assertEquals(6, topologicalOrder.pop());
        assertEquals(7, topologicalOrder.pop());
        assertEquals(8, topologicalOrder.pop());
        assertThrows(NullPointerException.class, () -> intGraph.getTopologicalOrder());
    }

    DirectedGraph<Integer> populateGraph2() {
        DirectedGraph<Integer> intGraph = new DirectedGraph<>();
        intGraph.addVertex(0);
        intGraph.addVertex(1);
        intGraph.addVertex(2);
        intGraph.addVertex(3);
        intGraph.addVertex(4);
        intGraph.addVertex(5);
        intGraph.addVertex(6);
        intGraph.addVertex(7);
        intGraph.addVertex(8);
        intGraph.addEdge(0,1,10);
        intGraph.addEdge(1,2,11);
        intGraph.addEdge(2,3,12);
        intGraph.addEdge(0,5,3);
        intGraph.addEdge(5,6,2);
        intGraph.addEdge(6,0,1);
        intGraph.addEdge(6,7,1);
        intGraph.addEdge(7,1,1);
        intGraph.addEdge(7,2,4);
        return intGraph;
    }
    @Test
    void getShortestPath() {
        DirectedGraph<Integer> pathGraph = populateGraph2();
        StackInterface<Integer> shortestPath = new LinkedStack<>();
        assertEquals(2, pathGraph.getShortestPath(0,2, shortestPath));
        assertEquals(0, shortestPath.pop());
        assertEquals(1, shortestPath.pop());
        assertEquals(2, shortestPath.pop());
        assertEquals(0, pathGraph.getShortestPath(0,8, shortestPath));
    }

    @Test
    void getCheapestPath() {
        DirectedGraph<Integer> pathGraph = populateGraph2();
        StackInterface<Integer> cheapestPath = new LinkedStack<>();
        assertEquals(10.0, pathGraph.getCheapestPath(0,2, cheapestPath));
        assertEquals(0, cheapestPath.pop());
        assertEquals(5, cheapestPath.pop());
        assertEquals(6, cheapestPath.pop());
        assertEquals(7, cheapestPath.pop());
        assertEquals(2, cheapestPath.pop());
        assertEquals(0.0, pathGraph.getCheapestPath(0,8, cheapestPath));
    }

    @Test
    void displayEdges() {
        DirectedGraph<String> strGraph = new DirectedGraph<>();
        strGraph.addVertex("A");
        strGraph.addVertex("B");
        assertTrue(strGraph.addEdge("A","B"));

        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        strGraph.displayEdges();

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        String expectedString = "\nEdges exist from the first vertex in each line to the other vertices in the line." +
                                "\n(Edge weights are given; weights are zero for unweighted graphs):\n\n" +
                                    "B \n" +
                                    "A B 0.0 \n";
        assertEquals(expectedString, testOutString);
    }

    @Test
    void testToString() {

        assertEquals("0", intGraph.toString());
    }
}