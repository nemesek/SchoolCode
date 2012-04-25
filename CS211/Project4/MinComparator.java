/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: MinComparator.java
     Current Date: 4/25/2012
     Course Information: CSci 211 - Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: This class implements the Comparator interface and returns the minimum of two objects
     Sources Consulted: http://docs.oracle.com/javase/6/docs/api/java/util/Comparator.html
    
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 
import java.util.*;
public class MinComparator<T extends Comparable<T>> implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		T t1 = (T)o1;
		T t2 = (T)o2;
		return -1 * compare( t1, t2);
	}
	private int compare(T o1, T o2)
	{
		return o1.compareTo(o2);
	}

}
