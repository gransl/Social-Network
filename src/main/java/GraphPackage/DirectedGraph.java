package GraphPackage;

import ADTPackage.*;

import java.util.Iterator;

/**
 * Directed Graph data structure
 * @param <T> generic of type T
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
    /** dictionary of vertices */
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    /** number of edges in the graph */
    private int edgeCount;

    /**
     * Full Constructor
     */
    public DirectedGraph()
    {
        vertices = new UnsortedLinkedDictionary<>();
        edgeCount = 0;
    } // end default constructor

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addVertex(T vertexLabel)
    {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null; // Was addition to dictionary successful?
    } // end addVertex

    /**
     * Removes a vertex from the graph
     *
     * @param vertexLabel label associated with vertex
     * @return true if the vertex was removed, false otherwise
     */
    public boolean removeVertex(T vertexLabel) {
        VertexInterface<T> removeOutcome = vertices.remove(vertexLabel);
        return removeOutcome != null; // Was the removal to dictionary successful?
    } // end removeVertex

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ( (beginVertex != null) && (endVertex != null) )
            result = beginVertex.connect(endVertex, edgeWeight);
        if (result)
            edgeCount++;
        return result;
    } // end addEdge

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(T begin, T end)
    {
        return addEdge(begin, end, 0);
    } // end addEdge

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasEdge(T begin, T end)
    {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ( (beginVertex != null) && (endVertex != null) )
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if

        return found;
    } // end hasEdge

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty()
    {
        return vertices.isEmpty();
    } // end isEmpty

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        vertices.clear();
        edgeCount = 0;
    } // end clear

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfVertices()
    {
        return vertices.getSize();
    } // end getNumberOfVertices

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfEdges()
    {
        return edgeCount;
    } // end getNumberOfEdges

    /**
     * private helper method for traversal methods
     */
    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    /**
     * {@inheritDoc}
     */
    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin)
    {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>(); // Queue of vertex labels
        // Queue for visit vertex neighbors in correct order
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

        // Fetch the origin vertex from the vertices dictionary
        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit(); // mark as visited
        traversalOrder.enqueue(origin); // enqueue origin label
        vertexQueue.enqueue(originVertex); // enqueue origin vertex

        while(!vertexQueue.isEmpty()) {
            VertexInterface<T> topVertex = vertexQueue.dequeue();
            while (topVertex.getUnvisitedNeighbor() != null) { // if topVertex has an unvisited neighbor
                VertexInterface<T> neighbor = topVertex.getUnvisitedNeighbor();
                neighbor.visit();
                traversalOrder.enqueue(neighbor.getLabel());
                vertexQueue.enqueue(neighbor);
            }
        }
        return traversalOrder;
    } // end getBreadthFirstTraversal

    /**
     * {@inheritDoc}
     */
    @Override
    // Exercise 10, Chapter 29
    public QueueInterface<T> getDepthFirstTraversal(T origin)
    {
        // Assumes graph is not empty
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<T>();
        StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();

        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin); // Enqueue vertex label
        vertexStack.push(originVertex); // Enqueue vertex

        while (!vertexStack.isEmpty())
        {
            VertexInterface<T> topVertex = vertexStack.peek();
            VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

            if (nextNeighbor != null)
            {
                nextNeighbor.visit();
                traversalOrder.enqueue(nextNeighbor.getLabel());
                vertexStack.push(nextNeighbor);
            }
            else // All neighbors are visited
                vertexStack.pop();
        } // end while

        return traversalOrder;
    } // end getDepthFirstTraversal

    /**
     * {@inheritDoc}
     */
    @Override
    public StackInterface<T> getTopologicalOrder()
    {
        resetVertices();

        StackInterface<T> vertexStack = new LinkedStack<>();
        int numberOfVertices = getNumberOfVertices();
        for (int counter = 1; counter <= numberOfVertices; counter++)
        {
            VertexInterface<T> nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push(nextVertex.getLabel());
        } // end for

        return vertexStack;
    } // end getTopologicalOrder

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path)
    {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();

        // Assertion: resetVertices() has executed setCost(0)
        // and setPredecessor(null) for originVertex

        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                } // end if

                if (nextNeighbor.equals(endVertex))
                    done = true;
            } // end while
        } // end while

        // Traversal ends; construct shortest path
        int pathLength = (int)endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while

        return pathLength;
    } // end getShortestPath

    /**
     * {@inheritDoc}
     */
    @Override
    // Exercise 15, Chapter 29
    /** Precondition: path is an empty stack (NOT null) */
    public double getCheapestPath(T begin, T end, StackInterface<T> path) // STUDENT EXERCISE
    {
        resetVertices();
        boolean done = false;

        // Use EntryPQ instead of Vertex because multiple entries contain
        // the same vertex but different costs - cost of path to vertex is EntryPQ's priority value
        PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();

        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        priorityQueue.add(new EntryPQ(originVertex, 0, null));

        while (!done && !priorityQueue.isEmpty())
        {
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex();

            if (!frontVertex.isVisited())
            {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor());

                if (frontVertex.equals(endVertex))
                    done = true;
                else
                {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                    while (neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Double weightOfEdgeToNeighbor = edgeWeights.next();

                        if (!nextNeighbor.isVisited())
                        {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        } // end if
                    } // end while
                } // end if
            } // end if
        } // end while

        // Traversal ends, construct cheapest path
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while

        return pathCost;
    } // end getCheapestPath

    /**
     * returns the last vertex in a path
     * @return last vertex in a path
     */
    protected VertexInterface<T> findTerminal()
    {
        boolean found = false;
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

        while (!found && vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();

            // If nextVertex is unvisited AND has only visited neighbors)
            if (!nextVertex.isVisited())
            {
                if (nextVertex.getUnvisitedNeighbor() == null )
                {
                    found = true;
                    result = nextVertex;
                } // end if
            } // end if
        } // end while

        return result;
    } // end findTerminal

    /**
     * Displays Edges
     */
    // Used for testing
    public void displayEdges()
    {
        System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
        System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            ((Vertex<T>)(vertexIterator.next())).display();
        } // end while
    } // end displayEdges


    /**
     * Creates object to work with priority queues for certain graph algorithms
     */
    private class EntryPQ implements Comparable<EntryPQ>
    {
        /** a vertex*/
        private VertexInterface<T> vertex;
        /** vertex previous to this one*/
        private VertexInterface<T> previousVertex;
        /** cost to nextVertex */
        private double cost;

        /**
         * Full constructor
         * @param vertex a vertex
         * @param cost the cost the path up until that point
         * @param previousVertex the previous the vertex in the path
         */
        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
        {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        } // end constructor

        /**
         * returns the vertex
         * @return the vertex
         */
        public VertexInterface<T> getVertex()
        {
            return vertex;
        } // end getVertex

        /**
         * returns the previous vertex
         * @return the previous vertex
         */
        public VertexInterface<T> getPredecessor()
        {
            return previousVertex;
        } // end getPredecessor

        /**
         * returns the cost
         * @return the cost
         */
        public double getCost()
        {
            return cost;
        } // end getCost

        /**
         * compares two EntryPQs
         * @param otherEntry the object to be compared.
         * @return a positive number if this EntryPQ is larger than other Entry, a negative number if it is
         * less than, and 0 if they are equal.
         */
        public int compareTo(EntryPQ otherEntry)
        {
            // Using opposite of reality since our priority queue uses a maxHeap;
            // could revise using a minheap
            return (int)Math.signum(otherEntry.cost - cost);
        } // end compareTo

        /**
         * returns a string containing information about EntryPQ
         * @return EntryPQ as a formatted String
         */
        public String toString()
        {
            return vertex.toString() + " " + cost;
        } // end toString
    } // end EntryPQ
} // end DirectedGraph