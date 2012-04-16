import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;


public class Driver433P2 
{

	public static void main(String[] args) 
	{
		 LinkedList<Node> list = new LinkedList<Node>();
		 //Read the data file - assumes name is input.txt and stored in src dir		
		try
		{
			File file = new File("src\\input.txt");
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String strLine;			
			while((strLine = br.readLine()) != null)
			{
				String[] split = strLine.split("\\t");
				int id = Integer.parseInt(split[0]);
				String name = split[1];
				int fatherID = Integer.parseInt(split[2]);
				int motherID = Integer.parseInt(split[3]);
				int birthYear = Integer.parseInt(split[4]);
				int deathYear = Integer.parseInt(split[5]);
				Node node = new Node(id, name, fatherID, motherID, birthYear, deathYear);
				list.add(node);

			}

		}
		catch (Exception ex)
		{
			System.out.println("Oops");
		}
		
		Node temp = list.get(10);
		System.out.println(temp.GetUserInfo());
		System.out.println("Getting Father's data");
		Node father = temp.GetFather();
		System.out.println(father.GetUserInfo());

	}

}
