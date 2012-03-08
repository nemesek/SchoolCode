//Dan Nemesek
//Used Eclipse IDE in Adler to write and compile
//InputFile is assumed to be in /src directory
//Sources Consulted : None
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.

public class VertexNode implements Comparable<VertexNode>
{
	private int edgeID;
	private double weight;
	public VertexNode(int e, double w)
	{
		edgeID = e;
		weight = w;
		
	}
	public int getEdgeID()
	{
		return edgeID;
	}
	public double getWeight()
	{
		return weight;
	}
	public int compareTo(VertexNode v)
	{
		VertexNode temp = v;
		if(this.getEdgeID() < temp.getEdgeID())
			return -1;
		else if(this.getEdgeID() > temp.getEdgeID())
			return 1;
		else
			return 0;
	}
}
