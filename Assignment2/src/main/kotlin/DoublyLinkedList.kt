package org.example

interface LinkedList<T> {
    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T)

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T)

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists
     */
    fun popFront(): T?

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popBack(): T?

    /**
     * @return the value at the front of the list or nil if none exists
     */
    fun peekFront(): T?

    /**
     * @return the value at the back of the list or nil if none exists
     */
    fun peekBack(): T?

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

class Node<T>(var data: T) {
    var prev: Node<T>? = null
    var next: Node<T>? = null
}

class DoublyLinkedList<T>: LinkedList<T> {
    private var head: Node<T>? = null // first node; need var to be able to change later on
    private var tail: Node<T>? = null // lat node;

    override fun pushFront(data: T) {
        val newNode = Node(data)
        newNode.next = head

        if (head == null) {
            tail = newNode
        } else {
            head?.prev = newNode
        }
        head = newNode
    }

    override fun pushBack(data: T) {
        val newNode = Node(data)
        newNode.prev = tail
//
//        if (tail ==)
//
//        }
    }

    override fun popFront(): T? {
        TODO("Not yet implemented")
    }

    override fun popBack(): T? {
        TODO("Not yet implemented")
    }

    override fun peekFront(): T? {
        return head?.data

        TODO("Not yet implemented")
    }

    override fun peekBack(): T? {
            //checking what's at the back of the list but not removing
            return tail?.data
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }


}