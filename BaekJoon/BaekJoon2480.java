import java.util.Scanner;

public class BaekJoon2480 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int a = 0, b = 0, c = 0, reward = 0;
		
		a = scanner.nextInt();
		b = scanner.nextInt();
		c = scanner.nextInt();
		
		if (a == b & b == c) reward = 10000 + a * 1000;
		else if (a == b & b != c) reward = 1000+ a * 100;
		else if (a != b & b == c) reward = 1000+ b * 100;
		else if (a != b & a == c) reward = 1000+ a * 100;
		else if (a != b & b != c & a != c){
			if (a > b & a > c) reward = a * 100;
			else if (b > a & b > c) reward = b * 100;
			else if (c > b & c > a) reward = c * 100;
		}
		System.out.println(reward);


		
	}

}
