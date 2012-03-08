
/** This class represents a 75% level Enigma machine with one rotor.
 * 
 * @author rhodes
 *
 */
public class Enigma75 extends Enigma {

	protected Rotor r0;
	protected Reflector ref;
	
	public Enigma75(){
		
		this.r0 = new Rotor(new int[]{10,9,1,25,4,11,0,14,12,19,23,20,22,16,5,13,15,17,7,24,18,8,3,21,2,6});
		this.ref = new Reflector(new int[]{25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0});
	}

	public Enigma75(int [] refArray, int [] r0Array){
		
		if(refArray.length != r0Array.length){
			
			throw new IllegalArgumentException("Arrays must have matching length.");
		}
		
		this.r0 = new Rotor(r0Array);
		this.ref = new Reflector(refArray);
	}
	

	
	/** Reset the rotor position to zero ('A').
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
	}
	
	/** Reset the rotor position to p0.
	 * 
	 */
	public void reset(int p0){
		
		// The setPosition() call can do
		// nothing for the 75 and 85% level
		// implementation. However, the 100%
		// level machine relies on this method
		// being properly implemented, EVEN IN
		// THE 75% AND 85% LEVEL MACHINES	
		this.r0.setPosition(p0);
	}

	/**
	 * Reset the rotor position to the given character.
	 * The character must be a letter, and is interpreted
	 * as a number derived from its position in the alphabet.
	 * e.g.  'A' means 0, 'B' means 1, etc.
	 * 
	 * @param r0
	 */
	public void reset(char r0){

		r0=Character.toUpperCase(r0);
		
		if(	!Character.isUpperCase(r0)) {
			
			throw new IllegalArgumentException("Characters must be uppercase letters.");
		}

		// The setPosition() call can do
		// nothing for the 75 and 85% level
		// implementation. However, the 100%
		// level machine relies on this method
		// being properly implemented, EVEN IN
		// THE 75% AND 85% LEVEL MACHINES
		this.r0.setPosition(r0-'A');
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

		char s = setting.charAt(0);
		s=Character.toUpperCase(s);
		this.r0.setPosition(s - 'A');
	}

	/** Return the encryption of the value p.
	 * 
	 * @param p the value to encrypt
	 */
	public int crypt(int p){
		
		int c;
		
		c = r0.mapForward(p);
		
		c = ref.map(c);
		
		c = r0.mapBackward(c);

		return c;		
	}

	
}
