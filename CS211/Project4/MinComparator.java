import java.util.*;
public class MinComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		Integer one = (Integer)o1;
		Integer two = (Integer)o2;
		if(one < two)
			return 1;
		else if( one > two)
			return -1;
		else
			return 0;
	}

}
