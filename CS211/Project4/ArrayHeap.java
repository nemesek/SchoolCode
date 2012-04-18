// ArrayHeap.java 
// array based implementation of the Heap Interface

import java.util.*;
public class ArrayHeap<T> implements Heap<T>
{
	public final static int DEFAULT_CAPACITY = 10;
	private int heapSize = 0;
	private T[] heap;
	private Comparator<Object> comp;
	
	
	@SuppressWarnings("unchecked")
	public ArrayHeap(Comparator<Object> c)
	{
		comp = c;
		heap = (T[])(new Object[DEFAULT_CAPACITY]); 
	}
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
		int i = heapSize;
		heapSize++;
		while(comp.compare(heap[i], heap[(i-1)/2]) > 0)
		{
			T temp = heap[i];
			heap[i] = heap[(i-1)/2];
			heap[(i-1)/2] = temp;
			i = (i-1)/2;			
		}		
	}
	public T remove()
	{
		T highest = heap[0];
		heapSize--;
		heap[0] = heap[heapSize];		
		Heapify(heap, 0);
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
		int num = heapSize + 1;
		if(num == 0)
			return true;
		while(num % 2 == 0)
		{
			num = num /2;
		}
		if(num > 1)
		{
			return false;
		}
		else
		{
			return true;
		}
		

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
		System.out.println("\nhello from printSorted()");
	}
	//TODO for testing purposes
	public T[] GetHeap()
	{
		return heap;
	}
	public T[] heapSort()
	{
		T[] sortedHeap = (T[]) (new Object[heapSize]);
		int i=0;
		while(heapSize > 0)
		{
			sortedHeap[i] = this.remove();
			i++;
		}
		return sortedHeap;
	}
	private void BuildHeap(T[] values)
	{
		for(int i= (heapSize-1)/2; i >=0; i--)
		{
			Heapify(values, i);
		}
	}
	private void Heapify(T[] values, int i)
	{
		int left = 2*i + 1;
		int right = 2*i + 2;
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
