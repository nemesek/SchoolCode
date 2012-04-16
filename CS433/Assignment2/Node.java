
public class Node 
{
	public int id;
	private String name;
	private int fatherID, motherID, birthYear, deathYear;
	private Node mother;
	private Node father;
	private int[] children = new int[20];
	
	public Node(int ID, String Name, int dadID, int momID, int birth, int death)
	{
		id = ID;
		name = Name;
		fatherID = dadID;
		motherID = momID;
		birthYear = birth;
		deathYear = death;
		//TODO - Set parent nodes from here --Maybe not because nodes may not have been initialized yet
		
	}
	public String GetUserInfo()
	{
		String result = "ID = " + Integer.toString(id) + "\nName: " + name  +  
		"\nFatherID: " + Integer.toString(fatherID) +
		"\nMotherID: " + Integer.toString(motherID) +
		"\nBirth Year: " + Integer.toString(birthYear) +
		"\nDeath Year: " + Integer.toString(deathYear);
		return result;
	}
	public int GetAncestorCount()
	{
		int count = 0;
		return count;
	}
	public int GetDescendantCount()
	{
		int count = 0;
		return count;
	}
	public String ListChildren()
	{
		String result = "";
		return result;
	}
	public String ListAncesotrs()
	{
		String result = "";
		return result;
	}
	public String ListDescendandts()
	{
		String result = "";
		return result;
	}
	public void SetMother(Node mom)
	{
		mother = mom;
		//TODO - Update moms children array
	}
	public void SetFather(Node dad)
	{
		father = dad;
		//TODO - Update dads children array
	}
	public Node GetMother()
	{
		return mother;
	}
	public Node GetFather()
	{
		return father;
	}
	public void AddChild(int childID)
	{
		int i = 0;
		while(children[i] < 1)
		{
			i++;
		}
		children[i] = childID;
	}
}
