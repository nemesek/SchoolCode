import java.util.Iterator;
import java.util.*;
import java.util.concurrent.*;


public class AncestorTree 
{
	private Node root;
	private Node father;
	private Node mother;
	private int count = 0;

	
	public AncestorTree(Node n)
	{
		root = n;
		//count++;
	}

	public void InsertFather(Node n)
	{
		father = n;
		count++;
	}
	public void InsertMother(Node n)
	{
		mother = n;
		count++;
	}
	public int GetCount()
	{
		return count;
	}

	public Node[] LevelOrder(Node n)
	{
		Node[] nodes = new Node[count + 1];
		Queue<Node> queue = new LinkedList<Node>();
		if(n != null)
		{
			queue.add(n);
		}
		int i = 0;
		while(!queue.isEmpty())
		{
			Node next = queue.remove();
			nodes[i] = next;
			i++;
			if(next.GetFather() != null)
			{
				queue.add(next.GetFather());
			}
			if(next.GetMother() != null)
			{
				queue.add(next.GetMother());
			}
			
		}
		return nodes;
		
	}



}
