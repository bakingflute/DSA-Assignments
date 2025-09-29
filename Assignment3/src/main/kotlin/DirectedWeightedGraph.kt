package org.example

/**
 * Simple adjacency-list graph.
 * Think: for each city (vertex) we store a page listing roads leaving it.
 */
class DirectedWeightedGraph<VertexType> : Graph<VertexType> {

    // Set of all vertices we’ve seen (no duplicates).
    private val vertices: MutableSet<VertexType> = mutableSetOf()

    // For each vertex, a map of (neighbor -> weight).
    private val adj: MutableMap<VertexType, MutableMap<VertexType, Double>> = mutableMapOf()

    override fun getVertices(): Set<VertexType> = vertices

    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        require(cost.isFinite()) { "Edge weight must be a finite number, got $cost" }
        vertices.add(from)
        vertices.add(to)
        val page = adj.getOrPut(from) { mutableMapOf() }
        page[to] = cost // add or replace
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        // Return a copy so callers can’t mutate our internals.
        return adj[from]?.toMap() ?: emptyMap()
    }

    override fun clear() {
        vertices.clear()
        adj.clear()
    }
}
