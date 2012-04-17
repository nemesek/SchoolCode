//Dan Nemesek
//Used Eclipse IDE
//InputFile is named input.txt and is read from /src directory
//Sources Consulted : Used a posting from Stackoverflow.com to get the idea for my AncestorTree.LevelOrder() method
//I can't find the link though.
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.
import java.util.*;

public class Node 
{
	public int id;
	private String name;
	private int fatherID, motherID, birthYear, deathYear;
	private Node mother;
	private Node father;
	private LinkedList<Node> childrenList = new LinkedList<Node>();
	
	public Node(int ID, String Name, int dadID, int momID, int birth, int death)
	{
		id = ID;
		name = Name;
		fatherID = dadID;
		motherID = momID;
		birthYear = birth;
		deathYear = death;		
	}
	public String GetName()
	{
		return name;
	}
	//Requirement 1
	public String GetUserInfo()
	{
		String result = "ID = " + Integer.toString(id) + "\nName: " + name  +  
		"\nFatherID: " + Integer.toString(this.GetFatherID()) +
		"\nMotherID: " + Integer.toString(this.GetMotherID()) +
		"\nBirth Year: " + Integer.toString(birthYear) +
		"\nDeath Year: " + Integer.toString(deathYear);
		return result;
	}
	//Requirement 2
	public String GetParentInfo()
	{
		String result = "\n";
		if(father != null)
		{
			result +="Fathers info: \n";
			result += father.GetUserInfo();
		}
		else
		{
			result += "\nNo father info";
		}
		result += "\n";
		if(mother != null)
		{
			result +="\nMother's info: \n";
			result += mother.GetUserInfo();
		}
		else
		{
			result +="\nNo mother info";
		}
		return result;
	}
	//Used to build a binary tree for ancestors with this as root
	//Called by the GetAncestorCount() and ListAncestors() methods
	private void BuildAncestorTree(AncestorTree at)
	{
		Node current = this;
		if(this.father != null)
		{
			at.InsertFather(current.father);
			current = this.father;
			current.BuildAncestorTree(at);
		}
		if(this.mother != null)
		{
			at.InsertMother(current.mother);
			current = this.mother;
			current.BuildAncestorTree(at);
		}
		
	}
	//Requirement 3
	public int GetAncestorCount()
	{
		AncestorTree at = new AncestorTree(this);
		BuildAncestorTree(at);		
		int count = at.GetCount();
		return count;
	}
	//Requirement 6
	public int GetDescendantCount()
	{
		int count = 0;
		Iterator<Node> iterator = childrenList.iterator();
		while(iterator.hasNext())
		{
			count++;
			Node next = iterator.next();
			count += next.GetDescendantCount();
		}
		return count;
	}
	//Requirement 7
	public String ListDescendants()
	{
		return ListDescendants(1);
	}
	private String ListDescendants(int n)
	{
		String result = "";
		Iterator<Node> iterator = childrenList.iterator();
		while(iterator.hasNext())
		{
			Node next = iterator.next();
			result += "\n" + next.name + " +" + Integer.toString(n);
			result += next.ListDescendants(n+1);
			
		}
		return result;
	}
	//Requirement 5
	public String ListChildren()
	{
		if(childrenList.size() < 1)
			return "No immediate children";
		String result = "";
		Iterator<Node> iterator = childrenList.iterator();
		while(iterator.hasNext())
		{
			result +=  iterator.next().name + "\n";
		}		
		return result;
	}
	//Requirement 4
	public String ListAncestors()
	{
		String result = "";
		AncestorTree at = new AncestorTree(this);
		BuildAncestorTree(at);
		Node[] ancestors = at.LevelOrder(this);
		for(int i=1; i < ancestors.length; ++i)
		{
			result += "\n" + ancestors[i].name;
			int level = at.GetLevel(this, ancestors[i].id);
			result += " +" + Integer.toString(level);
		}
		return result;
	}

	public void SetMother(Node mom)
	{
		mother = mom;
		mother.AddChild(this);
	}
	public void SetFather(Node dad)
	{		
		father = dad;
		father.AddChild(this);
	}
	public Node GetMother()
	{
		return mother;
	}
	public int GetMotherID()
	{
		return motherID;
	}
	public Node GetFather()
	{		
		return father;
	}
	public int GetFatherID()
	{		
		return fatherID;
	}

	public void AddChild(Node child)
	{
		childrenList.add(child);
	}
}
