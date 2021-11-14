package test.basic.test;

public class Operator {
	public static void main(String[] args) {
		System.out.println(1 + 2);
		
		int i = 3;
		int j = 4;
		System.out.println(i - j); // -1
		
		System.out.println(i * 4 / 5); // 2
		
		int a = i % 4;
		System.out.println(a); // 3
		
		double b = j / 2;
		System.out.println(b); // 2.0
		
		String str1 = "Hello";
		System.out.println(str1); // Hello
		
		System.out.println("Hello" + "World");
		
		String str2 = "Java";
		// Stirng + int --> String
		System.out.println(str2 + 20); // Java20
		
		System.out.println(str2 + (12 + 13)); // Java25
		
		int q = 123;
		System.out.println(q++); // 123
		System.out.println(++q); // 125
		
		int p = 234;
		p++;
		System.out.println(p); // 235
		++p;
		System.out.println(p); // 236
		
		int k = ++p;
		System.out.println(k); // 237
		k = p++;
		System.out.println(k); // 237
		
		int m = 9;
		int n = 6;
		int result5 = ++m + n--;
		System.out.println(result5); // 16
		System.out.println(m); // 10
		System.out.println(n); // 5
		
		// 11++; //error,�������������Լ�
		
		int t = 123;
		t += 10; // t = t + 10;
		System.out.println(t); //133
		
		t -= 3;
		System.out.println(t); // 130
		
		t /= 10;
		System.out.println(t); // 13
		
		t %= 2;
		System.out.println(t); // 1
		
		t *= 123;
		System.out.println(t); // 123
		
		byte r = 12;
		r += 5; // += �Դ�ǿת��r = (byte) (r + 5);
		System.out.println(r); // 17
		
	}
}