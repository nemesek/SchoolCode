
/** This class represents a 85% level Enigma machine.
 * 
 * @author rhodes
 *
 */
public class Enigma85 extends Enigma75 {
	
	protected Rotor r1;
	protected PlugBoard plugBoard;

	public Enigma85(){
		
		this.r1 = new Rotor(new int[]{8,0,3,20,22,10,12,23,9,2,25,4,6,21,24,7,18,5,11,19,17,15,13,16,1,14});
		this.plugBoard = new PlugBoard(new int[]{0,1,14,3,10,5,6,20,8,9,4,11,12,13,2,15,16,17,18,19,7,21,22,23,24,25});
	}

	
	public Enigma85(int[] refArray, int [] plugBoardArray, int[] r1Array, int [] r0Array){

		super(refArray, r0Array);
		
		if(plugBoardArray.length != r1Array.length || r1Array.length != r0Array.length){
			
			throw new IllegalArgumentException("Arrays must have matching length.");
		}

		this.r1 = new Rotor(r1Array);
		this.plugBoard = new PlugBoard(plugBoardArray);
	}

	
	/** Reset the rotor positions to zero ('A').
	 * 
	 */
	public void reset(){
		// The setPosition() call can do
		// nothing for the 75 and 85% level
		// implementation. However, the 100%
		// level machine relies on this method
		// being properly implemented, EVEN IN
		// THE 75% AND 85% LEVEL MACHINES
		
		this.r0.setPosition(0);
		this.r1.setPosition(0);
	}
	
	/** Reset the rotor positions to the given values.
	 *
	 * @param p1 position for rotor 1
	 * @param p0 position for rotor 0
	 */
	public void reset(int p1, int p0){
		// The setPosition() call can do
		// nothing for the 75 and 85% level
		// implementation. However, the 100%
		// level machine relies on this method
		// being properly implemented, EVEN IN
		// THE 75% AND 85% LEVEL MACHINES
		
		this.r1.setPosition(p1);
		this.r0.setPosition(p0);
		
	}

	/**
	 * Reset the rotor positions to the given character.
	 * The character must be a letter, and is interpreted
	 * as a number derived from its position in the alphabet.
	 * e.g.  'A' means 0, 'B' means 1, etc.
	 * 
	 * @param r0 position for rotor 0
	 * @param r1 position for rotor 1
	 */
	public void reset(char r1, char r0){

		r1=Character.toUpperCase(r1);
		
		if(	!Character.isUpperCase(r1) ) {
			
			throw new IllegalArgumentException("Characters must be uppercase letters.");
		}
		// The setPosition() call can do
		// nothing for the 75 and 85% level
		// implementation. However, the 100%
		// level machine relies on this method
		// being properly implemented, EVEN IN
		// THE 75% AND 85% LEVEL MACHINES

		this.r1.setPosition(r1-'A');
		super.reset(r0);
	}
	
	/** Reset this machine's rotors according to the given string.
	 *  Extra characters will be ignored. The string must contain
	 *  only letters, which are each interpreted
	 *  as a number derived from its position in the alphabet.
	 *  e.g.  'A' means 0, 'B' means 1, etc.
	 *  
	 * @param setting The rotor setting. e.g. "E"
	 */
	public void reset(String setting){

		char s1 = setting.charAt(0);	
		s1=Character.toUpperCase(s1);
		this.r1.setPosition(s1 - 'A');
		super.reset(setting.substring(1));
	}

	/** Return the encryption of the value p.
	 * 
	 * @param p the value to encrypt
	 */	
	public int crypt(int p){
		
		int c;
	
		c = plugBoard.mapForward(p);
		
		c = r0.mapForward(c);
		c = r1.mapForward(c);
		
		c = ref.map(c);
		
		c = r1.mapBackward(c);
		c = r0.mapBackward(c);
	
		c = plugBoard.mapBackward(c);

		return c;
	}

	
}
