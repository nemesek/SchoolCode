
public class HeapDemo 
{

	public static void main(String[] args) 
	{
		MinComparator min = new MinComparator();
		MaxComparator max = new MaxComparator();
		//Integer[] intArray = {2,7,8,1,3,5,6};
		Integer[] intArray = {2,7,8,1};
				
		ArrayHeap<Integer> heap = new ArrayHeap<Integer>(intArray, min);
		Object[] tempArray = heap.GetHeap();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		
		heap.add(4);		
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		heap.remove();
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		//heap.printSorted();
		System.out.println();
		tempArray = heap.heapSort();
		for(int i=0; i<tempArray.length; i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		
		ArrayHeap<Integer> maxHeap = new ArrayHeap<Integer>(max); 
		maxHeap.add(5);
		maxHeap.add(7);
		maxHeap.add(1);
		maxHeap.add(9);
		maxHeap.add(8);
		maxHeap.add(3);
		
		Object[] tempArray2 = maxHeap.GetHeap();
		System.out.println();
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		maxHeap.remove();
		System.out.println();
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		maxHeap.add(4);
		System.out.println();
		for(int i=0; i<maxHeap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray2[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		
		
	}

}
