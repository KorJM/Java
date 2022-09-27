import java.util.Scanner;

public class CodeUp1046 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		long a = 0, b = 0, c = 0;
		
		a = scanner.nextLong();
		b = scanner.nextLong();
		c = scanner.nextLong();
		
		System.out.println(a + b + c);
		System.out.printf("%.1f", (a + b + c) / (double)3);
	}

}
