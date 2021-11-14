package test.basic;

public class Variable{
	public static void main(String[] args){
		//创建一个变量
		//格式: 数据类型 变量名称;
		int num1;
		//向变量当中存入一个数据
		//格式: 变量名称 = 数据值;
		num1 = 123;
		System.out.println(num1);
		
		//改变变量中的数字,得到新的数字
		num1 = 234;
		System.out.println(num1);
		
		//使用直接赋值的方式定义变量
		//格式: 数据类型 变量名称 = 数据值
		int num2 = 456;
		System.out.println(num2); // 输出25
		
		//byte num3 = 400; // 右侧400超出左侧byte的数据类型(byte-128到127),error.
		
		long num4 = 36878979812771289L;
		System.out.println(num4);
		
		float num5 = 3.14159F;
		System.out.println(num5);
		
		double num6 = 3.141212132113;
		System.out.println(num6);
		
		char zifu1 = 'b';
		System.out.println(zifu1);
		
		boolean var1 = true;
		System.out.println(var1);
	}
	
}