package GraphPackage;

import ADTPackage.LinkedListWithIterator;
import ADTPackage.ListWithIteratorInterface;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creates a Vertex object usable in Graph Classes
 * @param <T> generic of type T
 */
class Vertex<T> implements VertexInterface<T>
{
    /** data to be stored in vertex*/
    private T label;
    /** Edges to neighbors */
    private ListWithIteratorInterface<Edge> edgeList;
    /** True if visited */
    private boolean visited;
    /** On path to this vertex */
    private VertexInterface<T> previousVertex;
    /** Of path to this vertex */
    private double cost;

    /**
     * Full constructor
     *
     * @param vertexLabel data to be stored in vertex
     */
    public Vertex(T vertexLabel)
    {
        label = vertexLabel;
        edgeList = new LinkedListWithIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor

    /**
     * {@inheritDoc}
     */
    @Override
    // Ex 8 from here to ....
    public T getLabel()
    {
        return label;
    } // end getLabel

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPredecessor()
    {
        return previousVertex != null;
    } // end hasPredecessor

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPredecessor(VertexInterface<T> predecessor)
    {
        previousVertex = predecessor;
    } // end setPredecessor

    /**
     * {@inheritDoc}
     */
    @Override
    public VertexInterface<T> getPredecessor()
    {
        return previousVertex;
    } // end getPredecessor

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit()
    {
        visited = true;
    } // end visit

    /**
     * {@inheritDoc}
     */
    @Override
    public void unvisit()
    {
        visited = false;
    } // end unvisit

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisited()
    {
        return visited;
    } // end isVisited

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCost()
    {
        return cost;
    } // end getCost

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCost(double newCost)
    {
        cost = newCost;
    } // end setCost

    /**
     * Returns Vertex's label as a string.
     * @return a string representation of the vertex
     */
    public String toString()
    {
        return label.toString();
    } // end toString

    /**
     * Private Inner WeightIterator Class. Provides an iteration for the weights of edges.
     */
    private class WeightIterator implements Iterator<Double>
    {
        /** Iterator of all edges for the Vertex*/
        private Iterator<Edge> edges;

        /**
         * Constructor
         */
        private WeightIterator()
        {
            edges = edgeList.getIterator();
        } // end default constructor

        /**
         * Determines if there is another edge in the iteration.
         * @return true if there is another edge in the iteration, false otherwise.
         */
        public boolean hasNext()
        {
            return edges.hasNext();
        } // end hasNext

        /**
         * Returns the next edge in the iteration.
         * @return the next edge in the iteration.
         */
        public Double next()
        {
            Double edgeWeight = 0.0;
            if (edges.hasNext())
            {
                Edge edgeToNextNeighbor = edges.next();
                edgeWeight = edgeToNextNeighbor.getWeight();
            }
            else
                throw new NoSuchElementException();

            return edgeWeight;
        } // end next

        /**
         * Removes an edge. Currently unsupported.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end WeightIterator

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Double> getWeightIterator()
    {
        return new WeightIterator();
    } // end getWeightIterator
// . . . to here Ex 8

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
    {
        boolean result = false;

        if (!this.equals(endVertex))
        {  // Vertices are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;

            while (!duplicateEdge && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            } // end while

            if (!duplicateEdge)
            {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if

        return result;
    } // end connect

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex, 0);
    } // end connect

    /**
     *{@inheritDoc}
     */
    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator()
    {
        return new NeighborIterator();
    } // end getNeighborIterator

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbor()
    {
        return !edgeList.isEmpty();
    } // end hasNeighbor

    /**
     * {@inheritDoc}
     */
    @Override
    public VertexInterface<T> getUnvisitedNeighbor()
    {
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while ( neighbors.hasNext() && (result == null) )
        {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while

        return result;
    } // end getUnvisitedNeighbor


    /**
     * Compares if a vertex is equal to a given parameter.
     * @param other other object to compare against
     * @return if this vertex is equal to parameter object
     */
    @Override
    public boolean equals(Object other)
    {
        boolean result;

        if ((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {
            // The cast is safe within this else clause
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        } // end if

        return result;
    } // end equals

    /**
     * Iterator for this Vertex's neighbor
     */
    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        /** an iterator for the edges associated with this vertex*/
        private Iterator<Edge> edges;

        /**
         * Full constructor
         */
        private NeighborIterator()
        {
            edges = edgeList.getIterator();
        } // end default constructor

        /**
         * Determines if there is another edge in the iteration
         * @return true if there is another edge in the iteration, false otherwise.
         */
        public boolean hasNext()
        {
            return edges.hasNext();
        } // end hasNext

        /**
         * Returns the next edge in the iteration
         * @return the next edge in the iteration
         */
        public VertexInterface<T> next()
        {
            VertexInterface<T> nextNeighbor = null;

            if (edges.hasNext())
            {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();

            return nextNeighbor;
        } // end next

        /**
         * Can remove an edge. Currently an unsupported operation.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end NeighborIterator

    /**
     * Inner class Edge, allows vertex to connect to other vertices.
     */
    protected class Edge
    {
        /** Vertex at end of edge */
        private VertexInterface<T> vertex;
        /** the weight of the edge */
        private double weight;

        /**
         * Full Constructor
         *
         * @param endVertex the other vertex that the edge connects to
         * @param edgeWeight the weight we are assigning to the edge
         */
        protected Edge(VertexInterface<T> endVertex, double edgeWeight)
        {
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor

        /**
         * Partial Constructor, for when edges do not have a weight
         *
         * @param endVertex the other vertex that the edge connects to
         */
        protected Edge(VertexInterface<T> endVertex)
        {
            vertex = endVertex;
            weight = 0;
        } // end constructor

        /**
         * Gets the other vertex connected to this edge
         * @return the other vertex this edge connects
         */
        protected VertexInterface<T> getEndVertex()
        {
            return vertex;
        } // end getEndVertex

        /**
         * Returns the weight of the edge.
         * @return the weight of the edge
         */
        protected double getWeight()
        {
            return weight;
        } // end getWeight
    } // end Edge

    /**
     * Displays information about the vertex
     */
    public void display() // For testing
    {
        System.out.print(label + " " );
        Iterator<VertexInterface<T>> i = getNeighborIterator();
        Iterator<Double> w = getWeightIterator();

        while (i.hasNext())
        {
            Vertex<T> v = (Vertex<T>)i.next();
            System.out.print(v + " " + w.next() + " ");
        } // end while

        System.out.println();
    } // end display
} // end Vertex