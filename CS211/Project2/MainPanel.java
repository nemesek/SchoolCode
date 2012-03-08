/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: MainPanel.java
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel kp = new KeyPanel1();
	JButton btnSolve = new JButton("Solve");
	JLabel label = new JLabel("Queens", JLabel.CENTER);
	static int[][] chessBoard = new int[8][8];
	static JTextField[][] txtArray = new JTextField[8][8];

	public MainPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//label.setText("Press Submit to Calculate the Sum", JLabel.CENTER_ALIGNMENT);		
		add(label);
		add(kp);
		add(btnSolve);

		btnSolve.addActionListener(new SolveListener());
	}
	//Sub Panel Classes
	class KeyPanel1 extends JPanel
	{		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		KeyPanel1()
		{
			setLayout(new GridLayout(8,8));
				
			for(int i=0; i< 8; ++i)
			{			
				for(int j=0; j <8; ++j)
				{
					txtArray[i][j] = new JTextField();
					chessBoard[i][j] = 0;
					txtArray[i][j].setText("*");
					add(txtArray[i][j]);
					
				}				
			}
		
			
		}
	}
	class SolveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			boolean solved =  Queens.SetSquares(chessBoard, 0);
			if(solved)
			{
				for(int i=0; i<8; ++i)
				{
						for(int j=0;j<8; ++j)
						{
							//txtArray[i][j].setText(Integer.toString(chessBoard[i][j]));
							if(chessBoard[i][j] > 0)
							{
								txtArray[i][j].setText("Q");							
							}
							else
							{
								txtArray[i][j].setText("*");
							}						
						
						}
						/*try 
						{
							Thread.sleep(5);
						} 
						catch (InterruptedException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
	
					}	
			
			
				}
			
			}
		}
	
}
		

	










