//Dan Nemesek
//Used Eclipse IDE in Adler to write and compile
//InputFile is assumed to be in /src directory
//Sources Consulted : None
//     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
//                 ... My Signature is on File.

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Driver 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a file name ");
		String strFile = scan.nextLine();
		try
		{
			int numGraph = 0;
			File file = new File(strFile);
			FileInputStream fis = new FileInputStream("src\\" + file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;			
			while((strLine = br.readLine()) != null)
			{
				String[] split = strLine.split("\\s");
				if(split.length > 0)
				{
					numGraph++;
					int n = Integer.parseInt(split[0]);
					int m = Integer.parseInt(split[1]);
					int d = Integer.parseInt(split[2]);
					int w = Integer.parseInt(split[3]);
					int r = Integer.parseInt(split[4]);
					int u = 0;
					int v = 0;		


					if(numGraph % 2 == 1)
					{
						System.out.println("+++++++++++++++++++++++++++++++++++++++++");
						System.out.println("Odd numbered graph construct AM");
						System.out.println("Graph " + Integer.toString(numGraph));
	
						System.out.println("Number of nodes: " + split[0]);
						System.out.println("Number of edges: " + split[1]);
						if(r>0)
						{
							System.out.println("Random Graph");
							
						}
						else
						{
							System.out.println("Graph is not Random");
							GraphAM graphAM;
							if (d > 0 && w > 0)
							{
								System.out.println("Graph is Directed and Weighted");
								graphAM = new GraphAM(n, m, true, true);

							}
							else if(d > 0)
							{
								System.out.println("Graph is Directed and Unweighted");
								graphAM = new GraphAM(n, m, true, false);
							
							}
							else if(w > 0)
							{
								System.out.println("Graph is Weighted and Undirected");
								graphAM = new GraphAM(n, m, false, true);							
								
							}
							else
							{
								System.out.println("Graph is Undirected and Unweighted");
								graphAM = new GraphAM(n, m, false, false);								
							}
							
							for(int i=0; i< m; ++i)
							{

								strLine = br.readLine(); 
								String[] split2 = strLine.split("\\s");
								u = Integer.parseInt(split2[0]);
								v = Integer.parseInt(split2[1]);
								if(w > 0)
								{
									double weight = Double.parseDouble(split2[2]);
									graphAM.addEdge(u, v, weight);
								}

								else
									graphAM.addEdge(u,v);

							}
							String matrix = graphAM.toString();
							System.out.println("\nAdjacency Matrix");
							System.out.println(matrix);	
							System.out.println("\nAdjacency List");
							GraphAL graphAL = new GraphAL(graphAM);
							System.out.println(graphAL.toString());
							System.out.println("");
							
						}

					}
					else
					{
						System.out.println("+++++++++++++++++++++++++++++++++++++++++");						
						System.out.println("Even numbered graph construct AL");
						System.out.println("Graph " + Integer.toString(numGraph));
						System.out.println("Number of nodes: " + split[0]);
						System.out.println("Number of edges: " + split[1]);
						
						if(r > 0)
						{
							System.out.println("Random graph");
							
						}
						else
						{
							System.out.println("Graph is not Random");
							GraphAL graphAL;
							if(d > 0 && w > 0)
							{
								System.out.println("Graph is Directed and Weighted");
								graphAL = new GraphAL(n,m, true, true);
							}
							else if(d>0)
							{
								System.out.println("Graph is Directed and Unweighted");
								graphAL = new GraphAL(n, m, true, false);
								
							}
							else if(w > 0)
							{
								System.out.println("Graph is Weighted and Undirected");
								graphAL = new GraphAL(n, m, false, true);	
							}
							else
							{
								System.out.println("Graph is Unweighted and Undirected");
								graphAL = new GraphAL(n, m, false, false);
							}
							
							for(int i=0; i< m; ++i)
							{

								strLine = br.readLine(); 
								String[] split2 = strLine.split("\\s");
								u = Integer.parseInt(split2[0]);
								v = Integer.parseInt(split2[1]);
								if(w > 0)
								{
									double weight = Double.parseDouble(split2[2]);
									graphAL.addEdge(u, v, weight);
								}

								else
								{
									graphAL.addEdge(u,v);
								}

							}
							System.out.println("\nAdjacency List");
							System.out.println(graphAL.toString());
							GraphAM graphAM = new GraphAM(graphAL);
							System.out.println("\nAdjacency Matrix");
							System.out.println(graphAM.toString());
							
						}
						
						
					}
					
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		

	}

}
