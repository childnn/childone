package test.basic.test;

public class Logic
{
	public static void main(String[] args)
	{
		System.out.println(true && false);
		System.out.println(true && true);
		System.out.println(false && true);
		System.out.println(false && false);
		System.out.println("============");
		
		System.out.println(true || false);
		System.out.println(true || true);
		System.out.println(false || true);
		System.out.println(false || false);
		System.out.println("==============");
		
		System.out.println(!true);
		System.out.println(!false);
		System.out.println("==============");
		
		int a = 123;
		System.out.println((3>4) && (++a<100));
		System.out.println((123==a) || (3>5)); // true
		
		System.out.println((97=='a') && (48=='0'));
		
		System.out.println("Hello" + 'A');
		
		System.out.println((3<4) ^ (4<5));
	}
}