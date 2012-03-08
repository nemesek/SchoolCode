//Dan Nemesek
public class Reflector 
{
    Mapper mapper;
    
    //pull sig from enigma classes
    
	public Reflector(int mapArray[])
    {        
        mapper = new Mapper(mapArray);
        
        //check to see if map is valid - must be one to one, onto, and  f(x) != f(x) for x = x
        for(int i = 0; i < mapArray.length; i++)
        {
            if(mapArray[i] == i)
            	//System.out.println("You must map an input to something other than itself");
                throw new IllegalArgumentException("You must map an input to something other than itself");
            if(mapArray[mapArray[i]] != i)
            	//System.out.println("Your map must be one to one");
                throw new IllegalArgumentException("Your map must be one to one");
        }

    }

    public int map(int in)
    {
        return mapper.Map(in);
    }

}
