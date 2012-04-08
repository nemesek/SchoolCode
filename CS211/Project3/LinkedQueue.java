//********************************************************************
//  LinkedQueue.java       Java Foundations
//
//  
//
//  Represents a linked implementation of a queue.
//********************************************************************

//package javafoundations;

//import javafoundations.LinearNode;
//import javafoundations.Queue;
//import javafoundations.exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements Queue<T>
{
	private int count;
	private LinearNode<T> front, rear;

	// -----------------------------------------------------------------
	// Creates an empty queue.
	// -----------------------------------------------------------------
	public LinkedQueue()
	{
		count = 0;
		front = rear = null;
	}

	// -----------------------------------------------------------------
	// Adds the specified element to the rear of this queue.
	// -----------------------------------------------------------------
	public void enqueue(T element)
	{
		LinearNode<T> node = new LinearNode<T>(element);

		if (count == 0)
			front = node;
		else
			rear.setNext(node);

		rear = node;
		count++;
	}

	// -----------------------------------------------------------------
	// Removes the element at the front of this queue.
	// -----------------------------------------------------------------
	public T dequeue()
	{
		if (isEmpty())
			throw new EmptyCollectionException("Queue is empty.");

		T element = front.getElement();
		count--;

		if (isEmpty())
		{
			rear = null;
			front = null;
		} else
		{
			front = front.getNext();
		}

		return element;
	}

	// -----------------------------------------------------------------
	// Returns the element at the front of this queue without removing it.
	// -----------------------------------------------------------------
	public T first()
	{
		if (isEmpty())
			throw new EmptyCollectionException("Queue is empty.");

		return front.getElement();
	}

	// -----------------------------------------------------------------
	// Returns true if the queue is empty.
	// -----------------------------------------------------------------
	public boolean isEmpty()
	{
		return count == 0;
	}

	// -----------------------------------------------------------------
	// Returns the number of elements in the queue.
	// -----------------------------------------------------------------
	public int size()
	{
		return count;
	}
	
	// -----------------------------------------------------------------
	// Returns a string representation of this queue.
	// -----------------------------------------------------------------
	public String toString()
	{
		String result = "<top of queue>\n";
		if (front != null) {
			result += front.getElement() + "\n";
			LinearNode current = front.getNext();
	
			while (current != null)
			{
				result += current.getElement() + "\n";
				current = current.getNext();
			}
		}
		return result + "<rear of queue>";
	}

}
