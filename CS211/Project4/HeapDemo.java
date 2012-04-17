
public class HeapDemo 
{

	public static void main(String[] args) 
	{
		MinComparator min = new MinComparator();
		MaxComparator max = new MaxComparator();
		Integer[] intArray = {3, 7, 2, 1, 8, 4};
		
		ArrayHeap<Integer> heap = new ArrayHeap<Integer>(intArray, max);
		Object[] tempArray = heap.heapSort();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.println(Integer.toString(temp));
		}
		heap.add(9);
		tempArray = heap.heapSort();
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.println(Integer.toString(temp));
		}
		heap.remove();
		tempArray = heap.heapSort();
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.println(Integer.toString(temp));
		}
		
		
	}

}
