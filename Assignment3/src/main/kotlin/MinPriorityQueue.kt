package org.example

/**
 * Lower priority value = comes out sooner.
 */
interface MinPriorityQueue<T> {
    fun isEmpty(): Boolean
    fun addWithPriority(elem: T, priority: Double)
    fun next(): T?                 // remove & return next item (or null if empty)
    fun adjustPriority(elem: T, newPriority: Double)
}
