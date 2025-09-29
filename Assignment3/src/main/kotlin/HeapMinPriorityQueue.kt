package org.example

/**
 * A very thin wrapper around MinHeap<T>.
 * Rules we choose:
 *  - addWithPriority: if elem exists, we UPDATE its priority; else insert.
 *  - adjustPriority: if elem exists, update; if missing, we INSERT it.
 *  - All priorities must be finite numbers (no NaN/Infinity).
 */
class HeapMinPriorityQueue<T> : MinPriorityQueue<T> {
    private val heap = MinHeap<T>()

    override fun isEmpty(): Boolean = heap.isEmpty()

    override fun addWithPriority(elem: T, priority: Double) {
        require(priority.isFinite()) { "Priority must be finite, got $priority" }
        if (heap.contains(elem)) {
            heap.adjustHeapNumber(elem, priority)
        } else {
            heap.insert(elem, priority)
        }
    }

    override fun next(): T? = heap.getMin()

    override fun adjustPriority(elem: T, newPriority: Double) {
        require(newPriority.isFinite()) { "Priority must be finite, got $newPriority" }
        if (heap.contains(elem)) {
            heap.adjustHeapNumber(elem, newPriority)
        } else {
            // Our chosen policy: if it wasn't there, add it.
            heap.insert(elem, newPriority)
        }
    }
}
