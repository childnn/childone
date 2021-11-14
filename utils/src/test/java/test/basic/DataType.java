package test.basic;
/*
当数据类型不一样时,将会发生数据类型转换.

自动类型转换(隐式)
	1. 特点: 代码不需要进行特殊处理,自动完成.
	2. 规则: 数据范围从小到大.
强制类型转换(显式)
	1. 特点: 代码需要进行特殊的格式处理,不能自动完成.
	2. 格式:范围小的类型 范围小的变量名 = (范围小的类型) 原本范围大的数据;
	注意事项:
		1. 强制类型转换一般不推荐使用,因为有可能发生精度损失、数据溢出.
		2. byte/short/char这三种类型都可以发生数学运算，在运算时，都会首先提升成为int类型，然后再计算。
		3. boolean类型不可发生数据转换。
*/
public class DataType{

	public static void main(String[] args){
		System.out.println(1024); // 整数,默认int类型
		System.out.println(3.14); // 浮点数,默认double类型
		
		// 一个等号代表赋值,若不加L,表示将右侧的int类型赋值给左边的long类型变量进行存储
		long num1 = 100L; // 直接写100不加L则默认为int类型,没有L输出也是100.自动转换
		System.out.println(num1); // 100
		
		// 左边double类型数据范围大于右边的float,自动转换
		double num2 = 2.5F; // 不加 F 默认double
		System.out.println(num2); // 2.5
		
		// float范围大,自动转换
		float num3 = 123;
		System.out.println(num3); // 无论23行的123后加不加L,输出都为123.0
		
		
		// 强制类型转换
		// 格式:范围小的类型 范围小的变量名 = (范围小的类型) 原本范围大的数据;
		// long数据范围大于int
		int num4 = (int) 100L; // 不加(int)会导致编译error
		System.out.println(num4);
		
		// int num5 = 2.14; // error,右边double不可赋值给int
		// System.out.println(num5);
		
		int num6 = (int) 3.14; // double强制转换int,精度损失
		System.out.println(num6); // 3
		
		int num7 = (int) 6000000000L; // long强制转换为int，数据溢出
		System.out.println(num7); // 1705032704
		
		//计算机底层会用一个数字（二进制）来代表字符A，即65
		//一旦char类型进行了数学运算，那么字符就会按照一定的规则翻译成一个数字
		char zifu1 = 'A';
		System.out.println(zifu1 + 1); // 66，字母A被当作65处理
		
		byte num8 = 40; // 注意：右侧的数值大小不能超过左侧类型数据范围
		byte num9 = 50; 
		// byte + byte --> int + int -->int
		int result1 = num8 + num9; // 前面的数据类型不能写byte,除非参照下方在右侧加byte
		System.out.println(result1); //90
		
		short num10 = 12;
		short result2 = (short) (num8 + num10); // 注意
		System.out.println(result2);	
	}
	
}