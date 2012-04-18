import java.util.*;
public class MaxComparator<T extends Comparable<T>> implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		T t1 = (T)o1;
		T t2 = (T)o2;
		return compare( t1, t2);
	}
	private int compare(T o1, T o2)
	{
		return o1.compareTo(o2);
	}

}
