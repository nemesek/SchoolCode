/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: MainPanel.java
     Current Date: 2/08/2012
     Course Information: CS211 Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: Magic Square program.  Validates that the sum of all rows, columns, and diagonals are equal
     Validates, that all integers are greater than zero and distinct
     Sources Consulted: None
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel kp = new KeyPanel1();
	JPanel sp = new SouthPanel1();
	static JTextField[] txtArray = new JTextField[9];
	JLabel label = new JLabel("Press Submit to Calculate the Sum", JLabel.CENTER);

	public MainPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//label.setText("Press Submit to Calculate the Sum", JLabel.CENTER_ALIGNMENT);		
		add(label);
		add(kp);
		add(sp);
		
	}
	
	//Sub Panel Classes
	public class KeyPanel1 extends JPanel
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public KeyPanel1()
		{
			setLayout(new GridLayout(3,3));
			try
			{
				//Load initial data from a file where each number is on its own line within the file
				File file = new File("src\\puzzle.txt");
				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				String[] strArray = new String[9];
				int index = 0;
				while((strLine = br.readLine()) != null)
				{
					strArray[index] = strLine;
					index++;
				}
				for(int i= 0; i < 9; ++i)
				{
					txtArray[i] = new JTextField(strArray[i]);
					add(txtArray[i]);

				}
			}
			catch(Exception e)
			{
				//Failed reading file so Populate all fields with zeros
				for(int i= 0; i < 9; ++i)
				{
					txtArray[i] = new JTextField("0");
					add(txtArray[i]);
				}
			}

		}
		
	}	
	
	public class SouthPanel1 extends JPanel
	{

		private static final long serialVersionUID = 1L;
		JButton btnCalculate = new JButton("Calculate");
		JLabel lblResults = new JLabel("Results:______ ");

		SouthPanel1()
		{
			
			add(btnCalculate);
			add(lblResults);
			btnCalculate.addActionListener(new CalculateListener());
		}
		public boolean AreNoZeroes(JTextField[] textArray) //Check for non positive integers
		{
			boolean noZeroes = true;
			for(int i=0; i<textArray.length;++i)
			{
				if(Integer.parseInt(textArray[i].getText()) < 1)
				{
					noZeroes = false;
					i = 100;
				}				
				
			}
			return noZeroes;
		}
		public boolean IsValid(int x, int y) //Check to make sure sums match
		{
			if(x == y)
				return true;
			else
				return false;
		}
		public boolean IsDistinct(JTextField[] textArray) //Check to make sure all integers are distinct
		{
			int[] intArray = new int[textArray.length];
			
			for(int i=0; i< textArray.length; ++i)
			{
				intArray[i] = Integer.parseInt(textArray[i].getText());
			}
			boolean distinct = true;
			for(int i=0; i< intArray.length; ++i)
			{
				for(int j=i+1;j < intArray.length; ++j)
				{
					if(intArray[i] == intArray[j])
					{
						distinct = false;
						j = 100;
						i = 100;
					}
					
				}
				
			}
			return distinct;

		}
		//Listeners
		public class CalculateListener implements ActionListener
		{

			
			public void actionPerformed(ActionEvent event)
			{
				int row1 = 0;
				int row2 = 0;
				int row3 = 0;
				int diagnolDown = 0;
				int diagnolUp = 0;
				int column1 = 0;
				int column2 = 0;
				int column3 = 0;
			
				String errorInfo = "Sums do not match";

				for(int i=0; i<9; ++i) //Loop through txtArray and sum rows, columns, and diagonals
				{
					try //Try block to catch non numeric data when performing summations
					{
						int temp = Integer.parseInt(txtArray[i].getText());
						if(i<3)
						{
							row1 += temp;
							if(i == 0)
							{
								diagnolDown += temp;
								column1 += temp;
							}
							if(i == 1)
							{
								column2 += temp;
							}	
							if(i == 2)
							{
								diagnolUp += temp;
								column3 += temp;
							}						
						
						}
						else if(i > 2 && i < 6)
						{
							row2 += temp;
							if(i == 3)
							{
								column1 += temp;
							}
							if(i ==4)
							{
								diagnolDown += temp;
								diagnolUp += temp;
								column2 += temp;
							}
							if(i ==5)
							{
								column3 += temp;
							}
						}
						else
						{
							row3 += temp;
							if(i == 6)
							{
								diagnolUp += temp;
								column1 += temp;
							}
							if(i == 7)
							{
								column2 += temp;
							}
							if(i == 8)
							{
								diagnolDown += temp;
								column3 += temp;
							}
						}
					}
					catch (Exception e)
					{
						//Set invalid characters to zero
						txtArray[i].setText("0");
					}
					
				}
				//Validate all integers are positive, distinct, and that the sums match
				boolean noZeroes = AreNoZeroes(txtArray); //All integers are positive
				boolean distinctNums = IsDistinct(txtArray); //All integers are distinct
				boolean valid = false; //Valid == true when sums match							
				
				if(noZeroes) //Ensure that all integers are positive
				{
					if(distinctNums) //Ensure that all integers are distinct
					{
						if(IsValid(row1, row2))	//Ensure that all rows, columns, diagnols are equal
							if(IsValid(row1, row3))
								if(IsValid(row1, column1))
									if(IsValid(column1, column2))
										if(IsValid(column1, column3))
											if(IsValid(column1, diagnolUp))
												if(IsValid(diagnolUp, diagnolDown))	
												{
													valid = true;	//Data is valid
												}
					}
					else
					{
						errorInfo = "All integers must be distinct";
					}
				}
				else
				{
					errorInfo = "All Input must be positive integers";
				}
			
											
				if(valid)
				{
					String strTotal = Integer.toString(row1);
					lblResults.setText("Valid: " + strTotal);
					
				}
				else
				{
					lblResults.setText("Invalid: " + errorInfo);
				}

				
			}
		}

		}

}




