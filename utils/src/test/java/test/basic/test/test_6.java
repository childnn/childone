package test.basic.test;

public class test_6{
	public static void main(String[] args){
		int i = 10;
		int j = 11;
		
		int temp = i > j ? i : j;
		
		System.out.println("�ϴ�ֵ�� " + temp);
		
		int x = 12;
		int max = temp > x ? temp : x;
		System.out.println("���ֵ�� " + max);
	}
}