

/** This class represents a 100% level Enigma machine.
 * 
 * @author rhodes 
 *
 */
public class Enigma100 extends Enigma85 {
	
	protected Rotor r2;

	public Enigma100(){
		
		this.r2 = new Rotor(new int[]{14,18,8,24,13,20,25,12,10,21,5,17,4,16,9,2,15,11,3,7,23,6,0,22,1,19});
	}

	public Enigma100(int[] refArray, int [] plugBoardArray, int [] r2Array, int[] r1Array, int [] r0Array){

		super(refArray, plugBoardArray, r1Array, r0Array);
		
		if(r2Array.length != r1Array.length){
			
			throw new IllegalArgumentException("Arrays must have matching length.");
		}

		this.r2 = new Rotor(r2Array);
	}


	
	/** Reset the rotor position to zero ('A').
	 * 
	 */
	public void reset(){
		
		super.reset();
		this.r2.setPosition(0);
	}
	
	/** Reset the rotor positions to the given values.
	 *
	 * @param p2 position for rotor 2
	 * @param p1 position for rotor 1
	 * @param p0 position for rotor 0
	 */
	public void reset(int p2, int p1, int p0){
		
		this.r2.setPosition(p2);
		super.reset(p1,p0);		
	}

	/**
	 * Reset the rotor positions to the given character.
	 * The character must be a letter, and is interpreted
	 * as a number derived from its position in the alphabet.
	 * e.g.  'A' means 0, 'B' means 1, etc.
	 * 
	 * @param r0 position for rotor 0
	 * @param r1 position for rotor 1
	 * @param r2 position for rotor 1
	 */
	public void reset(char r2, char r1, char r0){

		r2=Character.toUpperCase(r2);
		
		if(	!Character.isUpperCase(r2)) {
			
			throw new IllegalArgumentException("Characters must be uppercase letters.");
		}
		
		this.r2.setPosition(r2-'A');
		super.reset(r1, r0);
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

		char s2 = setting.charAt(0);	
		s2=Character.toUpperCase(s2);
		this.r2.setPosition(s2 - 'A');
		super.reset(setting.substring(1));
	}

	
	/** Move the rotors one position. 
	 */
	protected void updatePositions(){
				
		this.r0.rotate();
		if(this.r0.atMaxPosition()){
			
			this.r1.rotate();
			if(this.r1.atMaxPosition()){
				
				this.r2.rotate();
			}
		}
	}

	
	/** Return the encryption of the value p.
	 * 
	 * @param p the value to encrypt
	 */	
	public int crypt(int p){
		
		int c;
		
		this.updatePositions();
	
		c = plugBoard.mapForward(p);
		
		c = r0.mapForward(c);
		c = r1.mapForward(c);
		c = r2.mapForward(c);
		
		c = ref.map(c);
		
		c = r2.mapBackward(c);
		c = r1.mapBackward(c);
		c = r0.mapBackward(c);
	
		c = plugBoard.mapBackward(c);

		return c;
	}

	
}
