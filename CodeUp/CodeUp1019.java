import java.util.Scanner;

public class CodeUp1019 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		String[] date = scanner.next().split("\\.");
		int year = 0, month = 0, day = 0;
		
		year = Integer.parseInt(date[0]);
		month = Integer.parseInt(date[1]);
		day = Integer.parseInt(date[2]);
	
		System.out.printf("%04d.%02d.%02d", year, month, day);
	}

}
