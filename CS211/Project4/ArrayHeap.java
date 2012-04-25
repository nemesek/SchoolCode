/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: ArrayHeap.java
     Current Date: 4/25/2012
     Course Information: CSci 211 - Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: This class implements the interface in Heap.java with an array based implementation
     takes a comparator object in its constructor and uses the comparator to determine priority
     Sources Consulted: None
    
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 

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
		while(i > 0 && comp.compare(heap[i], heap[(i-1)/2]) > 0)//TODO further test the i > 0 condition
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
		if (heapSize == DEFAULT_CAPACITY)
			return true;
		else
			return false;
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
		
		if(heapSize > 0)
		{
			int num = heapSize;
			int height = 1;
			while(num > 1)
			{
				num = num/2;
				height++;
			}
			int i=0;
			for(int level=0; level <= height; level++)
			{
				String gap = "";
				double numNodes = Math.pow(2, level);
				double printGap = Math.pow(2, height);
				
				for(int k=0; k < printGap/numNodes; k++)
				{
					gap += "   ";
				}
				for(int j=0; j<numNodes; j++)
				{
					if(i < heapSize)
					{
						System.out.print(gap + heap[i].toString() + gap);
						i++;
					}
					
				}
				System.out.println();
				System.out.println();
			}

				
		}
	}
	public void printSorted() //prints out sorted heap with highest priority item first in the list
	{
		T[] tempArray = (T[]) (new Object[heapSize]);
		for(int i=0; i< tempArray.length; i++)
		{
			tempArray[i] = heap[i];
		}
		java.util.Arrays.sort(tempArray);
		if(comp.compare(1,0) < 0)
		{
			for(int i=0; i< tempArray.length; i++)
			{
				System.out.print(tempArray[i].toString() + " ");
			}
		}
		else
		{
			for(int i= tempArray.length -1; i>=0; i--)
			{
				System.out.print(tempArray[i].toString() + " ");
			}
		}
		

	}
	//Added so I can demo heap without modifying elements for testing purposes
	public T[] GetHeap()
	{
		return heap;
	}
	public T[] heapSort() //heap is sorted by highest pri item first
	{
		T[] sortedHeap = (T[]) (new Object[heapSize]);
		//int i=heapSize - 1;
		int bound = heapSize;
		int i = 0;
		while(i < bound)
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
