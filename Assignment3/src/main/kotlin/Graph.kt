package org.example

interface Graph<VertexType> {
    fun getVertices(): Set<VertexType>
    fun addEdge(from: VertexType, to: VertexType, cost: Double)
    fun getEdges(from: VertexType): Map<VertexType, Double>
    fun clear()
}
