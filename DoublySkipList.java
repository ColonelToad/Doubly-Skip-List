import java.util.*;

public class DoublySkipList
{
    // The head and tail pointers as well value of the skiplist's max height.
    Node head;
    Node tail;
    int maxHeight;

    class Node
    {
        // Node value, data, array of next pointers, previous, and down pointer.
        int value;
        int data;
        Node[] next;
        Node prev;
        Node down;

        Node(int value, int height)
        {
            this.value = value;
            this.next = new Node[height];
        }
    }

    public DoublySkipList()
    {
        this.head = null;
        this.tail = null;
        this.maxHeight = 0;
    }

    public int getRandomHeight()
    {
        Random rand = new Random();
        int height = 1;
        while (height < maxHeight && rand.nextDouble() < 0.5)
        {
            height++;
        }
        return height;
    }

    // Function to determine whether to start at the head or tail based on average value.
    public void determinePointer(int value)
    {
        if (head == null || tail == null)
        {
            System.out.println("Skip list is empty.");
            return;
        }
        double averageValue = (head.value + tail.value) / 2.0;
        if (value == averageValue)
        {
            // The value is exactly at the middle of the skip list, can start from either head or tail.
            System.out.println("Starting from either head or tail.");
            searchHead(value);
            searchTail(value);
        } 
        else if (head.next[0].value == value)
        {
            // The value is the second node from the head,can start from either head or second node.
            System.out.println("Starting from either head or second node.");
            searchHead(value);
            head.next[0] = head.next[0].next[0];
        }
        else if (tail.prev.value == value)
        {
            // The value is the second node from the tail,can start from either tail or second to last node.
            System.out.println("Starting from either tail or second to last node.");
            searchTail(value);
            tail.prev = tail.prev.prev;
        }
        else
        {
            // The value is not located at a special position, use the default approach starting from either head or tail.
            if (value < averageValue)
            {
                System.out.println("Starting from head.");
                searchHead(value);
            }
            else
            {
                System.out.println("Starting from tail.");
                searchTail(value);
            }
        }
    }

    // Function to insert a value at head in the skiplist.
    public void insertHead(int value)
    {
        if (head == null || tail == null)
        {
            head = new Node(value, 1);
            tail = head;
            maxHeight = 1;
            return;
        }
        Node newNode = new Node(value, getRandomHeight());
        Node[] update = new Node[maxHeight];
        Node current = head;
        for (int i = maxHeight - 1; i >= 0; i--)
        {
            while (current.next[i] != null && current.next[i].value < value)
            {
                current = current.next[i];
            }
            update[i] = current;
        }
        current = current.next[0];
        if (current == null || current.value != value)
        {
            current = newNode;
            for (int i = 0; i < newNode.next.length; i++)
            {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }
        }
    }

    // Function to insert a value at tail in the skiplist.
    public void insertTail(int value)
    {
        if (head == null || tail == null)
        {
            head = new Node(value, 1);
            tail = head;
            maxHeight = 1;
            return;
        }
        Node newNode = new Node(value, getRandomHeight());
        Node[] update = new Node[maxHeight];
        Node current = head;
        for (int i = maxHeight - 1; i >= 0; i--)
        {
            while (current.next[i] != null && current.next[i].value < value)
            {
                current = current.next[i];
            }
            update[i] = current;
        }
        current = current.next[0];
        if (current == null || current.value != value)
        {
            current = newNode;
            for (int i = 0; i < newNode.next.length; i++)
            {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }
        }
    }

    // Delete the leftmost node containing the given data, if present.
    public boolean deleteHead(int data)
    {
        if (head == null)
        {
            return false;
        }
        Node current = head;
        boolean found = false;
        while (current != null)
        {
            if (current.data == data)
            {
                found = true;
                current.prev.next = current.next;
                if (current.next != null)
                {
                    current.next[0].prev = current.prev;
                }
                current = current.down;
                head = current;
            }
            else if (current.data < data)
            {
                if (current.next != null)
                {
                    current = current.next[0];
                }
                else
                {
                    break;
                }
            }
            else
            {
                current = current.prev.down;
            }
        }
        return found;
    }

    // Delete the rightmost node containing the given data, if present.
    public boolean deleteTail(int data)
    {
        if (tail == null)
        {
            return false;
        }
        Node current = tail;
        boolean found = false;
        while (current != null)
        {
            if (current.data == data)
            {
                found = true;
                current.prev.next = current.next;
                if (current.next != null)
                {
                    current.next[0].prev = current.prev;
                }
                current = current.down;
                tail = current;
            }
            else if (current.data > data)
            {
                if (current.next != null)
                {
                    current = current.next[0];
                }
                else
                {
                    break;
                }
            }
            else
            {
                current = current.next[0].down;
            }
        }
        return found;
    }

    // Search for the given data starting from the head node. If the data is present multiple times, return the number of occurrences.
    public int searchHead(int data)
    {
        if (head == null)
        {
            return -1;
        }
        int count = 0;
        Node current = head;
        while(current != null)
        {
            if(current.data == data)
            {
                count++;
                current = current.down;
            }
            else if(current.next != null && current.next[0] != null && current.next[0].data <= data)
            {
                current = current.next[0];
            }
            else
            {
                current = current.down;
            }
        }
        return count;
    }

    // Search for the given data starting from the tail node. If the data is present multiple times, return the number of occurrences.
    public int searchTail(int data)
    {
        if (tail == null)
        {
            return -1;
        }
        int count = 0;
        Node current = tail;
        while(current != null)
        {
            if(current.data == data)
            {
                count++;
                current = current.down;
            }
            else if(current.next != null && current.next[0] != null && current.next[0].data >= data)
            {
                current = current.prev;
            }
            else
            {
                current = current.down;
            }
        }
        return count;
    }
}