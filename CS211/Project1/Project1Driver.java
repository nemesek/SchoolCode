/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: Project1Driver.java
     Current Date: 2/08/2012
     Course Information: CS211 Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: Magic Square program.  Validates that the sum of all rows, columns, and diagonals are equal
     Validates, that all integers are greater than zero and distinct
     Sources Consulted: None
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/
import javax.swing.JFrame;
import java.awt.Dimension;



public class Project1Driver
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Project 1");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = new Dimension(400,300);
		frame.pack();
		frame.setSize(dimension);
		frame.getContentPane().add(new MainPanel());

		frame.setVisible(true);

		
	}
	

}