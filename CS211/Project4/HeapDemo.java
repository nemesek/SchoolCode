
public class HeapDemo 
{

	public static void main(String[] args) 
	{
		MinComparator mc = new MinComparator();
		int num = mc.compare(7, 6);
		System.out.println(Integer.toString(num));
		MaxComparator max = new MaxComparator();
		int num2 = max.compare(5, 6);
		System.out.println(Integer.toString(num2));
	}

}
