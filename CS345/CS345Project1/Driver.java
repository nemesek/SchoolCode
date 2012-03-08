import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Driver 
{
	static final int VALUE = 2916;	

	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String badEntry = "";
		int jindex = 0;
		int totalDocs =0;

		LinkedList<Storage> storageList = new LinkedList<Storage>();
		
		try
		{
			//Load all filenames into a list
			File dir = new File("C:\\Users\\Socrates\\Desktop\\Data");
			File[] files = dir.listFiles();
			//Create FileInputStream foreach File
			FileInputStream[] fisArray = new FileInputStream[VALUE];
			DataInputStream[] disArray = new DataInputStream[VALUE];
			BufferedReader[] brArray = new BufferedReader[VALUE];
			System.out.println("List of files: ");
			/*for(int i=0; i < VALUE; ++i)
			{
				System.out.println(files[i].getName());
			}*/
			for(int i=0; i< VALUE; ++i)
			{
				fisArray[i] = new FileInputStream(files[i]);
				disArray[i] = new DataInputStream(fisArray[i]);
				brArray[i] = new BufferedReader(new InputStreamReader(disArray[i]));
			}
			
			String strLine;
			LinkedList<String> list = new LinkedList<String>();
			String previousLine = "";
			for(int i=0; i< VALUE; ++i)
			{
				while((strLine = brArray[i].readLine()) != null)
				{
					if(strLine.trim().contains(" "))
					{
						strLine.toLowerCase();
						strLine = strLine + " " + files[i].getName();

					}
					else
					{
						strLine.toLowerCase();
						strLine = strLine + brArray[i].readLine() + " " + files[i].getName();
					}
					//remove dupes in the same file
					if(!strLine.equalsIgnoreCase(previousLine) )
					{
						
						if(strLine.contains("#") || strLine.contains("«") || strLine.contains(">") || strLine.contains("{") || strLine.contains("<") || strLine.contains("|") || strLine.contains("~") || strLine.contains("-") || strLine.contains("+") || strLine.contains("*") || strLine.contains("'") || strLine.contains("$") || strLine.contains("%") || strLine.contains("&")) //TODO temp fix
						{
							//System.out.println("BAD DATA");
						}
						else 
						{							
							list.add(strLine);
						}

					}
					else
					{
						//System.out.println("Found Dupe");
						//Would need to parse both 
					}

					previousLine = strLine;
	
				}
			}

		
			Collections.sort(list);
		
			System.out.println("Storage time");			
			//Now  store into StorageList TODO Everything above seems good
			int numDocs = 1;
			System.out.println("Last element: " + list.getLast().toString());
			String tempStr = "";
			int size = list.size() -1;
			for( int i=0; i < list.size()-1; ++i)
			{

				String[] split = list.get(i).split("\\s"); //Get the first term
				if(i == 0)
				{
					System.out.println("Previous " + tempStr);
					System.out.println("Current " + split[0].toString());
				}
				//This will give us the count of the docs
				String[] nextSplit = list.get(i+numDocs).split("\\s"); //Get the next term and compare
				String temp = nextSplit[0];			
				if(split[0].compareTo(temp) == 0)
				{
					do
					{
						numDocs++;
						if(i+numDocs < size)
						{
							jindex = i;
							totalDocs = numDocs;
							badEntry = split[0];
							nextSplit = list.get(i+numDocs).split("\\s");
							temp = nextSplit[0];
						}
					}while(split[0].compareTo(temp)==0 && numDocs + i < size);	

				}
				if(numDocs > 1)
				{
					String[] fileArray = new String[numDocs];
					for(int j=0; j < numDocs; j++)
					{
						if(i - j >= 0)
						{
							fileArray[j] = list.get(i + j); 
						}
						else
						{
							System.out.println("Bad entry");
							System.out.println("Previous2" + tempStr);
							System.out.println("Current2" + split[0].toString());
							
						}
					}

					Storage tempStorage = new Storage(split[0], numDocs, fileArray);
					tempStorage.WriteData(1);
					storageList.add(tempStorage);
					i += numDocs-1; 
					numDocs = 1;
					
					
				}
				else
				{
					String[] fileArray = {list.get(i).toString()};
					Storage tempStorage = new Storage(split[0], numDocs, fileArray);
					tempStorage.WriteData(1);
					storageList.add(tempStorage);
					tempStr = split[0];
					numDocs = 1;
				}


			}
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			/*for(int i=0; i<storageList.size(); ++i)
			{
				System.out.println(storageList.get(i).GetTerm() + " " +  Integer.toString(storageList.get(i).GetNumDocs()));

			}*/

			System.out.println("Done");

		}		
		catch (IndexOutOfBoundsException ex)
		{
			System.out.println("Term " + badEntry);
			System.out.println("Index " + Integer.toString(jindex));
			System.out.println("NumDocs " + Integer.toString(totalDocs));
			System.out.println(ex.getMessage());

			ex.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}


	}

}

