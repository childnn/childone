package test.basic.test;

public class BiJiao
{
	public static void main(String[] args)
	{
		int a = 11;
		int b = 13;
		System.out.println(a < b);
		
		System.out.println((a>12) || (b<13));
		
		System.out.println(a != b);
		System.out.println(a == b);
		System.out.println(a >= b);
		System.out.println(a <= b);
		// System.out.println(a < 12 < b); // error
	}
}