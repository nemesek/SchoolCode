//Dan Nemesek
//Used Eclipse IDE in Adler to write and compile
//InputFile is assumed to be in /src directory
//Sources Consulted : None
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.
import java.util.*;


public class GraphAL 
{
	private LinkedList<VertexNode>[] listArray;
	private int numVertices;
	private int numEdges;
	private boolean isDirected;
	private boolean isWeighted;
	
	@SuppressWarnings("unchecked")

	public GraphAL(int n, int m, boolean directed, boolean weighted)
	{
		
		listArray = (LinkedList<VertexNode>[])new LinkedList[n];
		isDirected = directed;
		numVertices = n;
		numEdges = m;
		isWeighted = weighted;
		for(int i=0; i< n; ++i)
		{
			listArray[i] = new LinkedList<VertexNode>();
		}
	}
	@SuppressWarnings("unchecked")
	public GraphAL(GraphAM g)
	{
		numVertices = g.GetNumNodes();
		numEdges = g.GetNumEdges();
		isDirected = g.IsDirected();
		isWeighted = g.IsWeighted();
		
		listArray = (LinkedList<VertexNode>[]) new LinkedList[numVertices];
		for(int i=0; i<numVertices; i++)
		{
			listArray[i] = new LinkedList<VertexNode>();
		}
		for(int i=1; i<= numVertices; i++)
		{
			for(int j=1; j<=numVertices; j++)
			{
				if(g.existsEdge(i, j))
				{
					if(isWeighted)
					{
						double weight = g.getWeight(i, j);
						VertexNode tempNode = new VertexNode(j, weight);
						listArray[i-1].add(tempNode);
					}
					else
					{
						VertexNode tempNode = new VertexNode(j, 1);
						listArray[i-1].add(tempNode);
					}

				}
			}
		}
	}
	public void addEdge(int u, int v)
	{
	
		if(isDirected)
		{
			VertexNode node = new VertexNode(v, 1);
			listArray[u-1].add(node);
			//Resort(listArray[u-1]);
		}
		else
		{

			VertexNode node1 = new VertexNode(v, 1);
			VertexNode node2 = new VertexNode(u, 1);
			listArray[u-1].add(node1);
			listArray[v-1].add(node2);
			//Resort(listArray[u-1]);
			//Resort(listArray[v-1]);
		}
		
	}
	

	
	public boolean IsWeigted()
	{
		return isWeighted;
	}
	public boolean IsDirected()
	{
		return isDirected;
	}
	public int GetNumVertices()
	{
		return numVertices;
	}
	public int GetNumEdges()
	{
		return numEdges;
	}
	public void addEdge(int u, int v, double weight)
	{
		if(isDirected)
		{
			VertexNode node = new VertexNode(v, weight);
			listArray[u-1].add(node);

		}
		else
		{
			VertexNode node1 = new VertexNode(v, weight);
			VertexNode node2 = new VertexNode(u, weight);
			listArray[u-1].add(node1);
			listArray[v-1].add(node2);

		}
		
	}
	public boolean existsEdge(int u, int v)
	{
		boolean exists = false;
		
		@SuppressWarnings("rawtypes")
		Iterator iterator = listArray[u-1].iterator();
		while(iterator.hasNext())
		{
			VertexNode tempNode = (VertexNode)iterator.next();
			if(tempNode.getEdgeID() == v)
			{
				exists = true;
				return exists;
			}			
		}
		return exists;
	}
	public double getWeight(int u, int v)
	{
		double weight = 0;
		@SuppressWarnings("rawtypes")
		Iterator iterator = listArray[u-1].iterator();
		while(iterator.hasNext())
		{
			VertexNode tempNode = (VertexNode)iterator.next();
			if(tempNode.getEdgeID() == v)
			{
				weight = tempNode.getWeight();
			}
		}
		return weight;
	}
	public VertexNode[] getNeighbors(int u)
	{
		int index = 0;
		VertexNode[] tempArray = new VertexNode[numVertices];
		@SuppressWarnings("rawtypes")
		Iterator iterator = listArray[u-1].iterator();
		while(iterator.hasNext())
		{
			VertexNode tempNode = (VertexNode)iterator.next();
			tempArray[index] = tempNode;
			index++;
		}
		VertexNode [] neighbors = new VertexNode[index];
		for(int i=0; i<index; i++)
		{
			neighbors[i] = tempArray[i];
		}
		Arrays.sort(neighbors);
		return neighbors;
		
	}
	public String toString()
	{
		VertexNode[] tempArray;
		String str = "";
		for(int i=0;i <numVertices; i++)
		{
			tempArray = this.getNeighbors(i+1);
			str += Integer.toString(i+1);
			for(int j=0; j < tempArray.length; j++)
			{
				str += "--> edgeID " + Integer.toString(tempArray[j].getEdgeID());
				if(isWeighted)
				{
						str += ", Weight " + Double.toString(tempArray[j].getWeight());					
					
				}					
					
			}
			str += "\n";
		}
			
				
		return str;
	}

}
