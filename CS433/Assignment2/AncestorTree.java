//Dan Nemesek
//Used Eclipse IDE
//InputFile is named input.txt and is read from /src directory
//Sources Consulted : Used a posting from Stackoverflow.com to get the idea for my AncestorTree.LevelOrder() method
//I can't find the link though.
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.

import java.util.*;

public class AncestorTree 
{
	private Node root;
	private Node father;
	private Node mother;
	private int count = 0;

	
	public AncestorTree(Node n)
	{
		root = n;
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
	//Called by Node.ListAncestors()
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
	//Called by Node.ListAncestors()
	public int GetLevel(Node n, int id)
	{
		return GetLevel(n, id, 0);
	}
	private int GetLevel(Node n, int id,  int level)
	{
		if(n == null)
			return 0;
		if(id == n.id)
			return level;
		int dad = GetLevel(n.GetFather(), id, level+1);
		int mom = GetLevel(n.GetMother(), id, level+1);
		if(dad>mom)
			level = dad;
		else
			level = mom;
		return level;
		
	}

}
