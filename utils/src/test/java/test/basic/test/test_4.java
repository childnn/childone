package test.basic.test;

/*
	�ַ��Ĵ洢��ʽ������������int�Ĵ洢��ʽ��ͬ
	'0' --> 48,
	'A' --> 65,
	'a' --> 97,
*/
public class test_4
{
	public static void main(String[] args)
	{
		char zifu = 'a';
		zifu = (char)(zifu - 32);
		System.out.println(zifu); // A
		
		System.out.println("=============");
		
		//�������ң�String + �κ��������� --> String
		String str = "java";
		System.out.println(str + 20 + 30); // java2030
		System.out.println(str + (20 + 30)); // java50
		System.out.println(20 + 30 + str); // 50java
		System.out.println(20 + 'A' + str); //85java	
	}
}