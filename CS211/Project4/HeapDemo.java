/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: HeapDemo.java
     Current Date: 4/25/2012
     Course Information: CSci 211 - Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: This is my driver class that demo's my Heap implementation by calling the public methods
     Demonstrates two different heaps one a min heap and one a max heap
     Sources Consulted: None
    
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 
public class HeapDemo 
{

	public static void main(String[] args) 
	{
		MinComparator min = new MinComparator();
		MaxComparator max = new MaxComparator();
		
		//minHeap
		Integer[] intArray = {2,7,8,1,3,5,6};
		System.out.println("Demoing minHeap first, passing array and comparator to constructor");
		ArrayHeap<Integer> minHeap = new ArrayHeap<Integer>(intArray, min);
		Object[] tempArray = minHeap.GetHeap();
		System.out.print("Heap values for minHeap: ");
		for(int i=0; i<minHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		System.out.print("\nTop of Heap: ");
		int topMin = minHeap.getTop();
		System.out.print(Integer.toString(topMin));
		
		minHeap.add(4);		
		System.out.print("\nHeap values for minHeap after adding new element: ");
		for(int i=0; i<minHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		minHeap.remove();
		System.out.print("\nHeap values for minHeap after removing element: ");
		for(int i=0; i<minHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		System.out.print("\nCalling printSorted() on minHeap sorted by highest priority: ");
		minHeap.printSorted();

		System.out.println("\nCalling drawHeap(): ");
		minHeap.drawHeap();		
		System.out.print("Calling heapSort(): ");
		tempArray = minHeap.heapSort();
		for(int i=0; i<tempArray.length; i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		if(minHeap.isEmpty())
		{
			System.out.println("\nHeap has been destroyed and is now empty");
		}
		else
		{
			System.out.println("\nHeap is somehow not empty");
		}
		//maxHeap 
		System.out.println("\n============================================");
		System.out.println("Now demoing MaxHeap passing only comparator to constructor");
		ArrayHeap<Integer> maxHeap = new ArrayHeap<Integer>(max); 
		maxHeap.add(1);
		maxHeap.add(8);
		maxHeap.add(6);
		maxHeap.add(5);
		maxHeap.add(3);
		maxHeap.add(7);
		maxHeap.add(4);
		
		Object[] tempArray2 = maxHeap.GetHeap();
		System.out.print("Heap values for maxHeap: ");
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}

		maxHeap.remove();
		System.out.print("\nHeap values for maxHeap after removing element: ");
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		System.out.print("\nTop of Heap: ");
		int topMax = maxHeap.getTop();
		System.out.print(Integer.toString(topMax));
		maxHeap.add(9);
		System.out.print("\nHeap values for maxHeap after adding new element: ");
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		System.out.print("\nCalling printSorted() on maxHeap sorted by highest priority: ");
		maxHeap.printSorted();
		System.out.print("\nShowing that Heap was not destroyed after maxHeap.printSorted(): ");
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		System.out.println("\nAdding max number of elements (10) by calling isFull() in a loop before adding an element:");
		int num = 10;
		while(!maxHeap.isFull())
		{
			maxHeap.add(num);
			num++;
		}
		System.out.println("\nCalling drawHeap(): ");
		maxHeap.drawHeap();
		System.out.print("Calling heapSort(): ");
		tempArray = maxHeap.heapSort();
		for(int i=0; i<tempArray.length; i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		if(maxHeap.isEmpty())
		{
			System.out.println("\nHeap has been destroyed and is now empty");
		}
		else
		{
			System.out.println("\nHeap is somehow not empty");
		}
		
		
	}

}
