import java.util.Scanner;

public class CodeUp1018 {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		String[] str = scanner.next().split(":");
		
		System.out.printf("%s:%s", str[0], str[1]);
	}

}
