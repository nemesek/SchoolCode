import java.util.*;
public class ArrayHeap<T> implements Heap<T>
{
	public final static int DEFAULT_CAPACITY = 10;
	private int heapSize = 0;
	private T[] heap;
	
	public ArrayHeap(T[] values)
	{
		heap = (T[])(new Object[values.length*2]);
	}
	
	public void add(T obj)
	{
		heap[heapSize] = obj;
		heapSize++;
	}
	public T remove()
	{
		T highest = heap[0];
		heap[0] = heap[heapSize];
		heapSize--;
		return highest;
	}
	public boolean isEmpty()
	{
		boolean empty = false;
		if(heapSize == 0)
			empty = true;
		return empty;
	}
	public boolean isFull()
	{
		boolean full = false;
		if(heapSize == DEFAULT_CAPACITY)
			full = true;
		return full;
	}
	public T getTop()
	{
		T top = heap[0];
		return top;
		
	}
	public int sizeOf()
	{
		return heapSize;
	}
	public void drawHeap()
	{
		
	}
	public void printSorted()
	{
		
	}
	public T[] heapSort()
	{
		return heap;
	}


}
