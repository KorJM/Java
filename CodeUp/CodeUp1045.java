import java.util.Scanner;

public class CodeUp1045 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		long a = 0, b = 0;
		
		a = scanner.nextLong();
		b = scanner.nextLong();
		
		System.out.println(a + b);
		System.out.println(a - b);
		System.out.println(a * b);
		System.out.println(a / b);
		System.out.println(a % b);
		System.out.printf("%.2f", a / (double)b);
	}

}
