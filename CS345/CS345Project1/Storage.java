import java.io.*;

public class Storage 
{
	private String term;
	private int numDocs = 0;
	private String[] files;
	
	
	public Storage(String x, int y, String[] z)
	{
		term = x;
		numDocs = y;
		files = z;
	}
	
	public String GetTerm()
	{
		return term;
	}
	
	public int GetNumDocs()
	{
		return numDocs;
	}
	
	public String[] GetFiles()
	{
		return files;
	}
	
	public int WriteData(int x)
	{
		try
		{
			//Create dictionary file
			x = 5;
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("src\\Data\\DanDictionary.txt", true));
			BufferedWriter out = new BufferedWriter(writer);
			String numberDocuments = Integer.toString(numDocs);
			out.write(term + " " + numberDocuments + " " + term + ".txt");
			out.newLine();
			out.close();
			
			//Create postings list
			OutputStreamWriter pWriter = new OutputStreamWriter(new FileOutputStream("src\\Data\\" + term + ".txt", true));
			BufferedWriter pOut = new BufferedWriter(pWriter);
			String frequency = "";
			String docID = "";
			for(int i=0; i < numDocs; ++i)
			{
				String[]tempStr = files[i].split("\\s");
				frequency = tempStr[1];
				docID = tempStr[2];
				pOut.write(frequency + " " + docID );
				pOut.newLine();				
				
			}
			pOut.close();
		}
		catch (Exception e)
		{
			x = -1;
		}
		
		return x;
	}
	
	

}