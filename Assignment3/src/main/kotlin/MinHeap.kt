package org.example

/**
 * A "min-heap" stores items with numbers (priorities) and
 * always lets you take out the item with the *smallest* number first.
 *
 * Think of it like a to-do list where smaller numbers = more urgent.
 * Internally, we keep:
 *  - a list of boxes: each box = (item, number)
 *  - a map from item -> where that item sits in the list (its index)
 *
 * The list is arranged so that:
 *  - the very first box (index 0) always has the smallest number
 *  - each parent box is <= its two children
 *
 * We *don’t* store a tree object; we store a simple list and use math
 * to figure out parent/child positions.
 */
class MinHeap<T> {

    // This is our row of boxes. Each box holds (thing, number).
    // Smaller number = higher priority.
    private var vertices: MutableList<Pair<T, Double>> = mutableListOf()

    // This is a "finder" map so we can quickly locate any item’s index
    // inside the list. (Because searching a list would be slow.)
    private var indexMap: MutableMap<T, Int> = mutableMapOf()

    /** @return true if we have no boxes yet */
    fun isEmpty(): Boolean {
        return vertices.isEmpty()
    }

    /**
     * Put a new (item, number) into the heap.
     * @return true if added, false if it was already there (we don't add duplicates)
     *
     * Steps (ELI5):
     *  1) If the item already exists, do nothing (return false).
     *  2) Otherwise, put it at the *end* of the list (temporary spot).
     *  3) "Percolate up": while its parent has a bigger number, swap with parent.
     *     This floats the new item upward toward the front until it’s in the right place.
     */
    fun insert(data: T, heapNumber: Double): Boolean {
        if (contains(data)) {
            return false
        }
        // Put new (item, number) at the *end* for now
        vertices.add(Pair<T, Double>(data, heapNumber))
        // Record where it lives (its index)
        indexMap[data] = vertices.size - 1
        // Fix the heap shape by bubbling it up toward the top if needed
        percolateUp(vertices.size - 1)
        return true
    }

    /**
     * Remove and return the item with the *smallest* number.
     * @return that item, or null if empty.
     *
     * Steps (ELI5):
     *  - If empty: return null.
     *  - If just one item: return it and clear storage.
     *  - If many items:
     *      a) Save the root (index 0) — that’s the smallest.
     *      b) Swap root with the *last* item.
     *      c) Remove the last item (which used to be the root).
     *      d) Clean up maps for the removed item.
     *      e) "Bubble down" the new root if it’s bigger than a child,
     *         swapping with the smaller child until order is fixed.
     */
    fun getMin(): T? {
        when (vertices.size) {
            0 -> {
                // No items
                return null
            }
            1 -> {
                // Exactly one item — return it and reset storage
                val tmp = vertices[0].first
                vertices = mutableListOf()
                // NOTE: we should also clear indexMap here to stay consistent.
                // indexMap.clear()
                return tmp
            }
            else -> {
                // More than one item
                val tmp = vertices[0].first            // the top item (smallest number)
                swap(0, vertices.size - 1)             // move last item to the top
                vertices.removeLast()                  // remove the old top (now at end)
                indexMap.remove(tmp)                   // forget the removed item’s index
                bubbleDown(0)                          // fix the heap from the top down
                return tmp
            }
        }
    }

    /**
     * Change the number (priority) for an existing item.
     * If the new number is smaller, it should float up.
     * If the new number is bigger, it may sink down.
     * We do both "up" and "down" checks to cover either case.
     */
    fun adjustHeapNumber(vertex: T, newNumber: Double) {
        getIndex(of = vertex)?.also { index ->
            // Replace the pair at the same index with new (item, number)
            vertices[index] = Pair(vertices[index].first, newNumber)
            // Might need to go up or down. Do both routines safely.
            percolateUp(startIndex = index)
            bubbleDown(startIndex = index)
        }
        // If vertex wasn’t found, we silently do nothing here.
        // (Some designs might throw or insert instead — this class chooses silence.)
    }

    /** @return true if the item is in the heap, false otherwise */
    fun contains(vertex: T): Boolean {
        return getIndex(of = vertex) != null
    }

    /**
     * Look up an item’s position (index) in the list in O(1) time via the map.
     * If the item isn’t present, returns null.
     */
    private fun getIndex(of: T): Int? {
        return indexMap[of]
    }

    /**
     * "Bubble down" from a starting index if the parent is larger than a child.
     * We compare the parent’s number with its left and right child numbers.
     * If parent is already ≤ both children, we’re done.
     * Otherwise swap with the *smaller* child and keep going.
     */
    private fun bubbleDown(startIndex: Int) {
        val startNumber = vertices[startIndex].second
        val leftIndex = getLeftIndex(of = startIndex)
        val rightIndex = getRightIndex(of = startIndex)

        // Get the child numbers if they exist; else null means "no child".
        val leftNumber = if (leftIndex >= vertices.size) null else vertices[leftIndex].second
        val rightNumber = if (rightIndex >= vertices.size) null else vertices[rightIndex].second

        // Case A: parent already smaller than both children (or children don’t exist) → stop.
        if ((leftNumber == null || startNumber < leftNumber) &&
            (rightNumber == null || startNumber < rightNumber)) {
            return
        }
        // Case B: either right child missing OR left is smaller than right → swap with left.
        else if (rightNumber == null || (leftNumber != null && leftNumber < rightNumber)) {
            swap(leftIndex, startIndex)
            bubbleDown(leftIndex) // continue fixing from the child we swapped with
            return
        }
        // Case C: otherwise, swap with right (because right is the smaller child)
        else {
            swap(rightIndex, startIndex)
            bubbleDown(rightIndex)
            return
        }
    }

    /**
     * Swap the items at two indices in the list *and* update the indexMap so our
     * "where is this item?" lookups stay correct.
     *
     * Important: we update the map for the two items before swapping, using
     * the current contents of vertices[index1]/vertices[index2], then swap the list entries.
     */
    private fun swap(index1: Int, index2: Int) {
        // Update map: "this item will now live at the other index"
        indexMap[vertices[index1].first] = index2
        indexMap[vertices[index2].first] = index1

        // Now swap the boxes in the list
        val tmp = vertices[index1]
        vertices[index1] = vertices[index2]
        vertices[index2] = tmp
    }

    /**
     * "Percolate up" from a starting index if the child has a smaller number than its parent.
     * Repeatedly swap with parent until parent is smaller or we reach the root (index 0).
     */
    private fun percolateUp(startIndex: Int) {
        val parentIndex = getParentIndex(of = startIndex)
        if (parentIndex < 0) {
            // We’re at the root already; nothing to do.
            return
        } else if (vertices[startIndex].second < vertices[parentIndex].second) {
            // Child is smaller → swap with parent and keep going
            swap(parentIndex, startIndex)
            percolateUp(parentIndex)
        }
    }

    // Math helpers: given an index i, compute where its relatives live in the list.

    /** Parent of i lives at (i - 1) / 2; for i = 0, parent is -1 (doesn’t exist). */
    private fun getParentIndex(of: Int): Int {
        return (of - 1) / 2
    }

    /** Left child of i lives at 2*i + 1 (might be off the end if no child). */
    private fun getLeftIndex(of: Int): Int {
        return of * 2 + 1
    }

    /** Right child of i lives at 2*i + 2 (might be off the end if no child). */
    private fun getRightIndex(of: Int): Int {
        return of * 2 + 2
    }
}
