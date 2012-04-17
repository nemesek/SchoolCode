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
	public String GetUserInfo()
	{
		String result = "ID = " + Integer.toString(id) + "\nName: " + name  +  
		"\nFatherID: " + Integer.toString(this.GetFatherID()) +
		"\nMotherID: " + Integer.toString(this.GetMotherID()) +
		"\nBirth Year: " + Integer.toString(birthYear) +
		"\nDeath Year: " + Integer.toString(deathYear);
		return result;
	}
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
	public int GetAncestorCount()
	{
		AncestorTree at = new AncestorTree(this);
		BuildAncestorTree(at);		
		int count = at.GetCount();
		return count;
	}
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
