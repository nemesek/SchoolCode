/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: Queens.java
     Current Date: 3/05/2012
     Course Information: CS211 Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: The program uses a backtracking recursive algorithm implemented in Queens.java 
     in order to display a KeyPanel in Grid Layout
     implemented in MainPanel.java with a solution to the 8 queens Problem.  
     The program also includes tracing output of the recursive backtracking/placement calls.
     Sources Consulted: Just my book and EightQueens.java from http://www.cs.olemiss.edu/~cbzickos/cs211/
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 
import java.io.*;


public class Queens 
{
	
	public Queens(){};
	public static boolean SetSquares(int[][] chessBoard, int column)
	{
		boolean solved = false;
		int row = 0;
		try
		{
			FileWriter fstream = new FileWriter("traceQueens.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			if(column < 8)
			{
				out.write("\nNow attempting solution from column " + Integer.toString(column));
				out.flush();
			}
			
			//base case column == 8
			if(column==8)
			{
				solved = true;
				return solved;
			}
			//Recursive case
			else
			{			
				while(row < 8 && !solved)
				{
					if(!IsValid(chessBoard, row, column))
					{
						out.write("\nQueen cannot be placed at [" + Integer.toString(row) + "," + Integer.toString(column) + "]");
						out.flush();
						if(row == 7)
						{
							out.write("\nProblem cannot be solved with queen in this position");
							out.flush();
						}
						row++;
					}
					else
					{
						chessBoard[row][column] = 1;
						out.write("\nPlacing Queen at [" + Integer.toString(row) + "," + Integer.toString(column) + "]");
						out.flush();
						solved = SetSquares(chessBoard, column+1); //Recursive Call to set next column
						if(!solved)
						{
							out.write("\nRemoving Queen from [" + Integer.toString(row) + "," + Integer.toString(column) + "]");
							out.flush();
							chessBoard[row][column] = 0;
							row++;
						}
					}				
				}
			}
			out.close();
		}

		catch (Exception e)
		{
			System.err.println("Error " + e.getMessage());
			
		}
		
		return solved;	
		

	}
	public static boolean IsValid(int[][] chessBoard, int row, int column)
	{
		boolean valid = true;
		//row check
		for(int i=0; i < column; i++)
		{
			if(chessBoard[row][i] == 1)
			{
				return false;
			}
		}
		//up diagonal check
		for(int i = 1; i<= column; i++)
		{
			if(row < i)
			{
				break;
			}
			if(chessBoard[row-i][column-i] == 1)
			{
				return false;
			}
		}
		//down diagonal check
		for(int i=1; i <= column; i++)
		{
			if(row + i >= 8)
			{
				break;
			}
			if(chessBoard[row+i][column-i]==1)
			{
				return false;
			}
		}
		return valid;
	}
}
	
	
