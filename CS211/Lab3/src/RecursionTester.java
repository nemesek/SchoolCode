import java.util.*;


public class RecursionTester
{
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter an integer ");
		int userNum = scan.nextInt();
		if(userNum <= 0)
		{
			System.out.println("Must be positive. Start over!!");
			
		}
		else
		{
			int countDigits = numDigits(userNum);
			int sum = sumDigits(userNum);
			System.out.println("The number of digits in " + userNum + " is " + countDigits);
			System.out.println("The sum of the digits in " + userNum + " is " + sum);
		}
		System.out.print("Enter a number to convert: ");
		int number = scan.nextInt();
		System.out.print("Enter a base: ");
		int base = scan.nextInt();
		String newNumber = convert(number,base);
		System.out.print(newNumber);
	}
	public static int numDigits(int num)
	{
		if(num < 10)
			return 1;
		else
			return 1 + numDigits(num/10);
	}
	public static int sumDigits(int num)
	{
		if(num < 10)
			return num;
		else
		{
			return (num % 10) + (sumDigits(num/10));
		}

	}
	public static String convert(int number, int base)
	{
		if(base == 0)
			return "0";
		else
		{
			int quotient = number/base;
			int remainder = number%base;
			
			if(quotient == 0)
				return Integer.toString(remainder);
			else
			{
				return  convert(quotient,base) + Integer.toString(remainder);
			}
		}
	}

}