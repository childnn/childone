package test.basic.test;

public class test_7{

	public static void main(String[] args){
		{
			int x = 3;
			int y = 4;
			int z = x + y++;
			System.out.println(x + "," + y + "," + z); // 3,5,7
		}
		{
			int x = 10;
			x++;
			++x;
			System.out.println(x++); // 12
		}
		{
			int x = 10;
			int y = 20;
			System.out.println(x + "+" + y + "="+ (x+y)); // 10 + 20 = 30
		}
	}
}