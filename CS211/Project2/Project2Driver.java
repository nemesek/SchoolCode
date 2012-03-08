/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: Project2Driver.java
     Current Date: 3/5/2012
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
import javax.swing.JFrame;
import java.awt.Dimension;



public class Project2Driver
{
	public static void main(String[] args)
	{
		
		JFrame frame = new JFrame("Project 2");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = new Dimension(400,300);
		frame.pack();
		frame.setSize(dimension);
		frame.getContentPane().add(new MainPanel());

		frame.setVisible(true);

		
	}
	

}