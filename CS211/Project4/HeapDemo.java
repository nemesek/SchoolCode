
public class HeapDemo 
{

	public static void main(String[] args) 
	{
		MinComparator min = new MinComparator();
		MaxComparator max = new MaxComparator();
		Integer[] intArray = {2,7,8,1,3,5,6};
		
		ArrayHeap<Integer> heap = new ArrayHeap<Integer>(intArray, max);
		Object[] tempArray = heap.heapSort();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		heap.add(4);
		tempArray = heap.heapSort();
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		heap.remove();
		tempArray = heap.heapSort();
		System.out.println();
		for(int i=0; i<heap.sizeOf(); i++)
		{
			Integer temp = (Integer)tempArray[i];
			System.out.print(Integer.toString(temp));
			System.out.print(" ");
		}
		
		
	}

}
