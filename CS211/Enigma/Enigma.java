import java.io.*;


/** A simulation of an Enigma encryption machine. */
public abstract class Enigma {

	public Enigma(){
		
	}
		
	
	
	/** Reset the rotor positions to zero ('A').
	 * 
	 */
	public abstract void reset();


	
	/** Reset this machine's rotors according to the given string.
	 *  Extra characters will be ignored. The string must contain
	 *  only letters. Lowercase characters are converted to uppercase.
	 * @param setting The rotor setting. e.g. "EIN"
	 */
	public abstract void reset(String setting);
	

	/** Encrypt characters read from a String. The string 
	 *  returned contains the encrypted characters. All
	 *  lowercase text is converted to uppercase. All non-alphabetical
	 *  characters are skipped.
	 * 
	 * @param pString the string to be encrypted (i.e. the plaintext)
	 * @return a string with the encrypted version of the plaintext string.
	 * @throws IOException
	 */
	public String crypt(String pString) throws IOException{
		
		return crypt(new StringReader(pString));
	}

	
	
	
	/** Encrypt characters read from a Reader. The string 
	 *  returned contains the encrypted characters. This method
	 *  continues reading until the Reader indicates EOF. All
	 *  lowercase text is converted to uppercase. All non-alphabetical
	 *  characters are skipped.
	 *  
	 * @param sr the Reader to read from
	 * @return the string of encrypted text
	 * @throws IOException, IllegalArgumentException, IllegalStateException
	 */ 
	public String crypt(Reader sr) throws IOException {
		
		StringBuffer retval = new StringBuffer();
		int i;
		char c,p;
		
		while( -1 != (i = sr.read()) ){ // -1 indicates end of file (EOF)
			
			p = (char) Character.toUpperCase(i); 
			
			if(Character.isUpperCase(p)) {

				c = (char) ('A' + crypt(p - 'A'));
				
				if(!Character.isUpperCase(c)) {
					
					throw new IllegalStateException("Crypt returned a non-uppercase character.");
				}
				retval.append(c);
			} 
		}
		
		return retval.toString();
	}
	


	/** Return the encryption of the value p.
	 * 
	 * @param p the value to encrypt
	 */
	protected abstract int crypt(int p);


}
