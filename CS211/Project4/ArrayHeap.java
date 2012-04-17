import java.util.*;
public class ArrayHeap<T> implements Heap<T>
{
	public final static int DEFAULT_CAPACITY = 10;
	private int heapSize = 0;
	private T[] heap;
	private Comparator<Object> comp;
	
	public ArrayHeap(T[] values, Comparator<Object> c)
	{
		heap = (T[]) (new Object[values.length*2]);
		comp = c;
		heapSize = values.length;
		for(int i=0; i< heapSize; i++)
		{
			heap[i] = (T)values[i];
		}
		BuildHeap(heap);
	}
	
	public void add(T obj)
	{
		heap[heapSize] = obj;
		heapSize++;
		BuildHeap(heap);
	}
	public T remove()
	{
		T highest = heap[0];
		heapSize--;
		heap[0] = heap[heapSize];		
		BuildHeap(heap);
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
	private void BuildHeap(T[] values)
	{
		for(int i= values.length/2; i >=0; i--)
		{
			Heapify(values, i);
		}
	}
	private void Heapify(T[] values, int i)
	{
		int left = i+1;
		int right = i+2;
		int highest = i;
		if((left < heapSize) && (comp.compare(values[left], values[highest]) > 0))
		{
			highest = left;
		}
		if((right < heapSize) && (comp.compare(values[right], values[highest]) > 0))
		{
			highest = right;
				
		}
		if(highest != i)
		{
			T temp = values[highest];
			values[highest] = values[i];
			values[i] = temp;
			Heapify(values, highest);
			
		}
	}


}
