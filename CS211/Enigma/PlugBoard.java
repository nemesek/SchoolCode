//Dan Nemesek


public class PlugBoard
{
    Mapper mapper;
    
    //get sig from Engima classes
    public PlugBoard(int mapArray[])
    {
        mapper = new Mapper(mapArray);
        for(int i = 0; i < mapArray.length; i++)
        	//check to see if mapping is one to one and onto
            if(mapArray[mapArray[i]] != i)
            	//System.out.println("Your map must be one to one");
                throw new IllegalArgumentException("Your map must be one to one");

    }

    public int mapForward(int in)
    {
    	int value = mapper.Map(in);
        return value;
    }

    public int mapBackward(int in)
    {
    	int value = mapper.InverseMap(in);
    	return value;
        
    }


}

