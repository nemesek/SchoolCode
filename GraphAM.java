//Dan Nemesek
//Used Eclipse IDE in Adler to write and compile
//InputFile is assumed to be in /src directory
//Sources Consulted : None
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.



public class GraphAM 
{
	private double[][] matrix;
	private int numNodes;
	private boolean isDirected;
	private boolean isWeighted;
	private int numEdges;
	
	public GraphAM(int n, int m, boolean directed, boolean weighted)
	{
		numNodes = n;
		numEdges = m;
		matrix = new double[n][n];
		isDirected = directed;
		isWeighted = weighted;
		for(int i=0; i<n; ++i)
		{
			matrix[i][i] = 0;
		}
		
	}
	public GraphAM(GraphAL g)
	{
		numNodes = g.GetNumVertices();
		numEdges = g.GetNumEdges();
		isDirected = g.IsDirected();
		isWeighted = g.IsWeigted();
		matrix = new double[numNodes][numNodes];
		for(int i=0; i<numNodes; ++i)
		{
			matrix[i][i] = 0;
		}
		for(int i=0; i< numNodes; ++i)
		{
			for(int j=0; j <numNodes; ++j)
			{
				if(g.existsEdge(i+1, j+1))
				{
					if(isWeighted)
					{
						double weight = g.getWeight(i+1, j+1);
						this.addEdge(i+1, j+1, weight);
					}
					else
					{
						this.addEdge(i+1, j+1);
					}
				}
			}
			
		}
	}
	public void addEdge(int u, int v)
	{
		if(isDirected)
			matrix[u-1][v-1] = 1;
		else
		{
			matrix[u -1][v - 1] = 1;
			matrix[v -1][u -1 ] = 1;
		}
		
	}
	public void addEdge(int u, int v, double w)
	{
		if(isDirected)
			matrix[u-1][v-1] = w;
		else
		{
			matrix[u-1][v-1] = w;
			matrix[v-1][u-1] = w;
		}
	}
	public boolean existsEdge(int u, int v)
	{
		boolean exists = false;
		if(matrix[u - 1][v - 1] > 0)
			exists = true;
		return exists;
	}
	public double getWeight(int u, int v)
	{
		double weight = matrix[u- 1][v -1];
		return weight;
	}
	public int[] getNeighbors(int u)
	{
		int[] tempArray = new int[numNodes];
		int index = 0;
		for(int j=1; j<= numNodes; j++)
		{

			if(this.getWeight(u, j) > 0)
			{
				tempArray[index] = j;
				index++;
			}
		}
		int[] neighbors = new int[index];
		for(int i= 0; i<index;++i)
		{
			neighbors[i] = tempArray[i];
		}
		return neighbors;
		
	}
	public String toString()
	{
		String str = "";
		for(int i=0; i<numNodes; ++i)
		{
			str += "      " + Integer.toString(i+1);
				
		}
		for(int i=0; i<numNodes; ++i)
		{
			str += "\n" + "" + Integer.toString(i+1);
			for(int j=0; j<numNodes; ++j)
			{
				str += "    "  + Double.toString(matrix[i][j]);
			}
		}
		return str;
	}
	public int GetNumNodes()
	{
		return numNodes;
	}
	public int GetNumEdges()
	{
		return numEdges;
	}
	public boolean IsDirected()
	{
		return isDirected;
	}
	public boolean IsWeighted()
	{
		return isWeighted;
	}
}
