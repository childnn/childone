package test.basic;

public class While
{
	public static void main(String[] args)
	{
		int i = 3;
		int sum = 0;
		while (i < 100)
		{
			sum += i;
			i++;
		}
		System.out.println(sum);
	}
}