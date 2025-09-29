package org.example

/**
 * Dijkstra’s shortest path.
 * Returns the PATH (list of vertices from start to goal), or null if unreachable.
 *
 * We assume all edge weights are >= 0.
 */
fun <V> dijkstraShortestPath(graph: Graph<V>, start: V, goal: V): List<V>? {
    if (start == goal) return listOf(start)

    // Distance from start to each vertex (default Infinity).
    val dist = mutableMapOf<V, Double>().withDefault { Double.POSITIVE_INFINITY }
    dist[start] = 0.0

    // To rebuild the path at the end: prev[child] = parent
    val prev = mutableMapOf<V, V>()

    // Priority queue of "which vertex to explore next" by smallest dist
    val pq: MinPriorityQueue<V> = HeapMinPriorityQueue()
    pq.addWithPriority(start, 0.0)

    while (!pq.isEmpty()) {
        val u = pq.next() ?: break  // vertex with smallest dist
        if (u == goal) break         // we can early-exit when goal pops

        // Look at all neighbors v of u
        for ((v, w) in graph.getEdges(u)) {
            require(w >= 0.0) { "Dijkstra requires non-negative weights, found $w" }
            val alt = dist.getValue(u) + w
            if (alt < dist.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                dist[v] = alt
                prev[v] = u
                pq.adjustPriority(v, alt) // add or decrease-key
            }
        }
    }

    // If we never reached goal, there’s no path.
    if (!dist.containsKey(goal)) return null

    // Reconstruct path: goal -> ... -> start, then reverse.
    val path = generateSequence(seed = goal) { curr -> prev[curr] }
        .toList()
        .asReversed()

    // Optional sanity: ensure path actually starts at start.
    return if (path.isNotEmpty() && path.first() == start) path else null
}
