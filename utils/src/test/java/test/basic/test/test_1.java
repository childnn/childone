package Test;

public class test_1
{
	public static void main(String[] args)
	{
		System.out.println(20 + 30);
		System.out.println("20" + 30);
		System.out.println("20" + "30");
		
		int x = 10;
		x %= 3;
		System.out.println(x); // 1
		System.out.println("===============");
		
		System.out.println(10 > 9); // ture
		
		// &&��||����
		System.out.println(10 > 9 && 8 > 9); // false
		System.out.println(true || false); // true
		System.out.println(!true); // false
		
	}
}