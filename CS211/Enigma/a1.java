import java.io.*;
import java.util.StringTokenizer;


public class a1 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		
		
//		Enigma e = new Enigma();
//		
//		e.reset(3,1);
//
//		int [] pt = new int[]{ 0, 1, 2, 3};
//		int [] ct = new int[4];
//
//		
//		for(int i=0; i<4; i++){
//			
//			ct[i] = e.crypt(pt[i]);
//			System.out.println("p=" + pt[i] + " c=" + ct[i] );		
//		}
//		
//		System.out.println();
//
//		e.reset(3,1);
//		
//		for(int i=0; i<4; i++){
//			
//			pt[i] = e.crypt(ct[i]);
//			System.out.println("c=" + ct[i] + " p=" + pt[i] );		
//		}
		
		Enigma e=null;
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		
		System.out.println("Enter assignment level (75, 85, 100):");
		String levelString = br.readLine();
		int level = Integer.decode(levelString);
		System.out.println("level is: " + level);

		System.out.print("Use default components?(y/n): ");
		boolean useDefaults = !(br.readLine().trim().toUpperCase().startsWith("N"));
		
		if(useDefaults){
			
			switch(level){
				case 75:
					e =new Enigma75();
					break;
				case 85:
					e = new Enigma85();
					break;
				case 100:
					e = new Enigma100();
					break;
				default:
					System.out.println("Sorry, no such assignment level.");
			}
		} else {
			int [] r0=null, r1=null, r2=null;
			int [] ref=null, plug=null;

			System.out.println("Reflector:");
			ref = readArray(br);
			
			System.out.println("Rotor 0:");
			r0 = readArray(br);

			if(level > 75){
				System.out.println("Plugboard:");
				plug = readArray(br);

				System.out.println("Rotor 1:");
				r1 = readArray(br);
				
				if(level == 100) {
					
					System.out.println("Rotor 2:");
					r2 = readArray(br);
				}
			}

			switch(level){
				case 75:
					e =new Enigma75(ref,r0);
					break;
				case 85:
					e = new Enigma85(ref,plug, r1, r0);
					break;
				case 100:
					e = new Enigma100(ref,plug, r2, r1, r0);
					break;
				default:
					System.out.println("Sorry, no such assignment level.");
			}
		}
				
		if(level == 100){
			System.out.println("Enter initial rotor setting as a string (e.g. AGN) Extra characters will be ignored: ");
			String rotorString = br.readLine();
			System.out.println("RotorString:"+ rotorString.toUpperCase());
			e.reset(rotorString);
		}
				
		System.out.println("Enter plaintext message, followed by <ret> and <EOF> (Cntrl-Z for Windows, Cntrl-D for Linux/Mac):");
		String ciphertext = e.crypt(br);
		
//		String ciphertext = e.crypt("Now is the time for all good men to come to the aid of their country.");
		System.out.println("\n\nEncrypted Result is:");
		System.out.println(ciphertext);
		
//		String plaintext = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"; //"NOWISTHETIMEFORALLGOODMENTOCOMETOTHEAIDOFTHEIRCOUNTRY";
//		System.out.println(plaintext);
//		
//		String ciphertext = e.crypt(plaintext);   //"NOWISTHETIMEFORALLGOODMENTOCOMETOTHEAIDOFTHEIRCOUNTRY"  "ABCDEFGHIJKLMNOPQRSTUVWXYZ" "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
//		
//		System.out.println("\n" + ciphertext);
//		
//		
//		e.reset(14,12,5);
//		String recoveredText = e.crypt(ciphertext);
//		System.out.println("\n" + recoveredText);
		

	}
	
	
	private static int [] readArray(BufferedReader r) throws NumberFormatException, IOException {
		
		
		System.out.print("Enter length:");
		int length = Integer.decode(r.readLine().trim());
		
		System.out.print("Enter elements:");
		String arrayString = r.readLine();
		StringTokenizer st = new StringTokenizer(arrayString);
		int [] retval = new int[length];
		
		for(int i=0; i< length; i++){
			
			retval[i] = Integer.decode(st.nextToken());
		}
		
		
		return retval;
	}

}
