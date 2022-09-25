import java.util.Arrays;
import java.util.Scanner;

public class CodeUp1025 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int n = 10000, num = 0;
		char[] data = new char[5];
		
		data = scanner.next().toCharArray();
		
		for (int i = 0; i < data.length; i++) {
			num = Integer.parseInt(String.format("%c", data[i]));
			System.out.println("["+num*n+"]");
			n = n / 10;
		}
	}

}
