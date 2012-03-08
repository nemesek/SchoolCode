//Dan Nemesek

public class Mapper
{
    int mapping[];

    Mapper(int mapArray[])
    {
    	this.mapping = mapArray;
    }

    public int GetLength()
    {
        return mapping.length;
    }

    public int Map(int i)
    {
        return mapping[i];
    }

    public int InverseMap(int i)
    {
    	int value = -1;
    	for(int j = 0; j < mapping.length; j++)
    	{
            if(mapping[j] == i)
            {
                value = j;
                j = mapping.length + 1;
            	
            }
    		
    	} 
    	return value;

    }

}