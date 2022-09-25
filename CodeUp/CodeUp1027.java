import java.util.Scanner;

public class CodeUp1027 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		String[] str;
		
		str = scanner.next().split("\\.");
		
		System.out.printf("%02d-%02d-%04d", Integer.parseInt(str[2]), Integer.parseInt(str[1]), Integer.parseInt(str[0]));
	}

}
