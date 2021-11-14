package test.basic.test;

public class test_2
{
	public static void main(String[] args)
	{
		int i = 1;
		
		for (i=1; i<=10; i++)
		{
			if (i == 3)
			{
				break; // ��ֹforѭ��
			}
			System.out.println("HelloWorld" + i);
		}
		
		System.out.println("===============");
		
		for (i=1; i<=10; i++)
		{
			if (i == 3)
			{
				continue; // ����forѭ���������������HelloWorld
			}
			System.out.println("HelloWorld" + i);
		}
	}
}
