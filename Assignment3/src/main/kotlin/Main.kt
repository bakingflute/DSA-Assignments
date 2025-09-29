package org.example

fun main() {
    // 1) Graph sanity
    val g = DirectedWeightedGraph<String>()
    g.addEdge("A", "B", 2.0)
    g.addEdge("A", "C", 5.0)
    g.addEdge("B", "C", 1.0)
    g.addEdge("B", "D", 4.0)
    g.addEdge("C", "D", 1.0)

    println("Vertices: " + g.getVertices())         // [A, B, C, D] (order may vary)
    println("Edges from A: " + g.getEdges("A"))     // {B=2.0, C=5.0}

    // 2) PQ sanity
    val pq = HeapMinPriorityQueue<String>()
    pq.addWithPriority("A", 5.0)
    pq.addWithPriority("B", 2.0)
    pq.addWithPriority("C", 3.0)
    println(pq.next()) // B
    println(pq.next()) // C
    println(pq.next()) // A
    println(pq.next()) // null

    // 3) Dijkstra sanity (A -> D)
    val path = dijkstraShortestPath(g, "A", "D")
    println("Shortest path A->D: $path") // Example: [A, B, C, D] with total cost 2+1+1=4
}
