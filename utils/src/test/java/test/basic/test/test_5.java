package test.basic.test;

public class test_5
{
	public static void main(String[] args)
	{
		int num = 8765;
		int ge = num % 10;
		int shi = num / 10 % 10;
		int bai = num / 100 % 10;
		int qian = num / 1000;
		System.out.println(ge);
		System.out.println(shi);
		System.out.println(bai);
		System.out.println(qian);
		System.out.println(ge + " " + shi + " " + bai + " " + qian);
		
		int x = 3 > 4 ? 3 : 4;
		System.out.println(x);
	}
}