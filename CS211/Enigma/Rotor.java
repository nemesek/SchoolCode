//Dan Nemesek

public class Rotor
{
    int pos;
    Mapper mapper;

    //pull sig from Enigma classes
    public Rotor(int mapArray[])
    {
        pos = 0;
        mapper = new Mapper(mapArray);
    }
    
    public int getPosition()
    {
        return pos;
    }

    public int mapForward(int in)
    {
    	//add position, set it to its congruence class mod n, map it
    	//sub position, and set it to its congruence class
    	int input = in;
    	input += pos;
    	input %= mapper.GetLength();
    	input = mapper.Map(input);
    	input += mapper.GetLength();  //to ensure crypt returns uppercase
    	input -= pos;
    	input %= mapper.GetLength();
    	return input;
    }

    public int mapBackward(int in)
    {
    	//same logic as mapForward except use InverseMap
    	//duplicate logic consider adding private method(s) to reduce duplication
    	int output = in;
    	output += pos;
    	output %= mapper.GetLength();
    	output = mapper.InverseMap(output);
    	output += mapper.GetLength();
    	output -= pos;
    	output %= mapper.GetLength();
    	return output;
    }
    public void rotate()
    {
    	pos += 1;
    	pos %= mapper.GetLength(); //in case pos = n
    }

    public void setPosition(int position)
    {
    	pos = position;
        return;

    }

    public boolean atMaxPosition()
    {
    	boolean isMax = false;
    	if(pos == mapper.GetLength() - 1)
    		isMax = true;    	
    	return isMax;

    }

}