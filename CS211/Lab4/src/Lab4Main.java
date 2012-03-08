
public class Lab4Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = Factorial(0);
		System.out.println(Integer.toString(n));
		int gcd = GCD(630,3);
		System.out.println(Integer.toString(gcd));
		int a = 3429072;
		int b = 88231256;
		int c = 1800237;
		int d = 3180012;
		int[] intArray = {a, b, c, d};
		for(int i=0; i< 4; ++i)
		{
			int x = sumDigits(intArray[i]);
			if( x % 7 == 0)
				System.out.println("OK");
			else
				System.out.println("Error");
		}
		

	}
	
	public static int Factorial(int n)
	{
		if (n <=0)
			return 1;
		else
			return n * Factorial(n-1);
	}
	
	public static int GCD(int x, int y)
	{
		if(y == 0)
			return x;
		else 
			return GCD(y, x % y);


	}
	
	public static int sumDigits(int num)
	{
		if(num < 10)
			return num;
		else
			return num % 10 + sumDigits(num/10);
	}

}
