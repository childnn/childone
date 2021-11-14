package test.basic;

public class For
{
	public static void main(String[] args)
	{
	    int i = 0;
		for (; i > 10; i++)
		{
			System.out.println("Excellent!!!");

		}
		System.out.println(i);
		
		System.out.println("==========");
		
		int x = 0;
		while (x > 10)
		{
			System.out.println("Excellent!!!");
			x++;
		}
		
		System.out.println(x);
		
		int j = 0;
		do
		{
			System.out.println("Excsllent!!!");
			j++;
		} while (j > 10);

		System.out.println(j);
	}
}