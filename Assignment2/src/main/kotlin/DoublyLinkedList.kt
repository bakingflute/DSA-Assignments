package org.example

//Part 1: Doubly Linked List
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

        if (tail == null) { //null means nothing
            // if the list is empty tho, the new node is both the head and tail,
            // bc there isn't anything
            head = newNode
            tail = newNode
        }
        else { // not empty
            // update the tail's new pointer to the new node
            // and then set the new node as the tail
            tail?.next =newNode
            tail = newNode
        }
    }

    override fun popFront(): T? { //removes the first element
        // if there's nothing there, return nothing
        if (head == null) {
            return null
        }

        val removedData = head?.data
        // the list has one element
        if (head == tail) { // the head has to also be the tail
            head = null
            tail = null
        } else {
            // with more than one element
            head = head?.next
            head?.prev = null
        }
        return removedData
    }
}

    override fun popBack(): T? {
        if (tail == null) {
            return null
        }

        val removedData = tail?.data
        if (head == tail) {
            head = null
            tail = null
        } else {
            tail = tail?.prev
            tail?.next = null
        }
        return removedData
    }

    override fun peekFront(): T? {
        return head?.data
    }

    override fun peekBack(): T? {
            //checking what's at the back of the list but not removing
            return tail?.data
    }

    override fun isEmpty(): Boolean {
        return head == null
    }

}
// Stack and Queue Abstract Data Types

// Exercise 1
// making a stack; last in first out
// push adds an item on top, then if you want to take it off the top,
// you use pop and removes it as it's last in, first out.
interface Stack<T> {
    fun push(data: T)
    fun pop(): T?
    fun peek(): T?
    fun isEmpty(): Boolean
}
class LinkedListStack<T> : Stack<T> {
    private val list = DoublyLinkedList<T>()

    override fun push(data: T) {
        // Pushing to a stack means adding to the top, which corresponds to the front of a linked list.
        list.pushFront(data)
    }

    override fun pop(): T? {
        // Popping from a stack means removing from the top, which is the front of the list.
        return list.popFront()
    }

    override fun peek(): T? {
        // Peeking looks at the top element, which is the front of the list.
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        // The stack is empty if the underlying list is empty.
        return list.isEmpty()
    }
}

// making a queue
// Exercise 2
// First in first out
// since this is a ling, enqueue adds item to the back of the line, and
// dequeue takes it out from the front of the line

interface Queue<T> {
    fun enqueue(data: T)
    fun dequeue(): T?
    fun peek(): T?
    fun isEmpty(): Boolean
}

class LinkedListQueue<T> : Queue<T> {
    private val list = DoublyLinkedList<T>()

    override fun enqueue(data: T) {
        // Enqueuing adds an element to the back of the queue,
        // which is the back of the list.
        list.pushBack(data)
    }

    override fun dequeue(): T? {
        // Dequeuing removes an element from the front of the queue,
        // which is the front of the list.
        return list.popFront()
    }

    override fun peek(): T? {
        // Looking at the front of the list
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        // The queue is empty if the underlying list is empty.
        return list.isEmpty()
    }
}

// Exercise 3
// Reversing the stack im