import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class SearchDriver {
	static final int VALUE = 2916;  //Needs to be set to the number of docs in the corpus
	static final int NUMRESULTS = 50; //Set to how many results you want
	public static void main(String[] args) 
	{
		GetPostings("subject");
	}
	
	public static String[] GetPostings(String term)
	{
		String[] docs = new String[VALUE];
		int iIndex = 0;
		int jIndex = 0;
		int numDocs = 0;
		try
		{
			File file = new File("src\\Data\\DanDictionary.txt");
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;			
			while((strLine = br.readLine()) != null)
			{
				String[] split = strLine.split("\\s");
				if((term.compareTo(split[0])) == 0) 
				{
					//Found Matching file
					System.out.println("Found it");
					//TODO open file and build the array of documents to return to the user
					System.out.println(split[2]);
					numDocs = Integer.parseInt(split[1]);
					//docs = new String[numDocs];
					File postingsFile = new File("src\\Data\\" + split[2]);
					FileInputStream postingFis = new FileInputStream(postingsFile);
					DataInputStream postingDis = new DataInputStream(postingFis);
					BufferedReader postingBr = new BufferedReader(new InputStreamReader(postingDis));
					String pStrLine;
					int index =0;
					while((pStrLine = postingBr.readLine()) != null)
					{
						docs[index] = pStrLine;
						//System.out.println(docs[index]);
						index++;
						
					}
					postingBr.close();
				}
				
			}
			String[] results = new String[NUMRESULTS];
			System.out.println("Beginning Sort Results");
			for(int i=0; i< NUMRESULTS; ++i)
			{
				results[i] = docs[49-i];
				System.out.println(results[i]);
			}			
			//Sort Results descending
			String[] next = new String[2];
			//int position = 0;
			for(int i=NUMRESULTS; i < numDocs; ++i) 
			{
				next = docs[i].split("\\s");
				for(int j=0; j < NUMRESULTS; ++j)
				{
					String[] currentPos = new String[2];
					currentPos = results[j].split("\\s");
					int x = Integer.parseInt(next[0]);
					int y = Integer.parseInt(currentPos[0]);
					if ( x > y)
					{
						String tempStr = "";
						for(int h = j; h < NUMRESULTS; h++)
						{
							if(h == j)
							{
								tempStr = results[h];
								results[h] = docs[i];  //Replace
							}
							else
							{
								String tempStr2 = results[h];
								results[h] = tempStr;
								tempStr = tempStr2;		
							}
						}
						j = NUMRESULTS;
					}
					jIndex = j;
				}
				iIndex = i;
			}
			
			System.out.println("After Sort Results");
			for(int i=0; i< NUMRESULTS; ++i)
			{
				System.out.println(results[i]);
			}
			//System.out.println(strLine);
			br.close();

			return results;
		}
		catch (Exception e)
		{
			System.out.println(Integer.toString(iIndex) + " " + Integer.toString(jIndex));
			System.out.println("Error from GetPostings " + e.getMessage());
			return null;
		}

		
		
	}

}
