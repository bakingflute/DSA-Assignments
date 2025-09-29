import org.example.DoublyLinkedList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DoublyLinkedListTest {
    @Test
    fun pushFront() {
        val x = DoublyLinkedList<String>()
        x.pushFront("test")
        assertEquals("test", x.peekFront())
    }

    @Test
    fun pushBack() {
    }

    @Test
    fun testEverything() {

    }

}