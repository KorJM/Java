import java.util.Arrays;
import java.util.Scanner;

public class CodeUp1021 {

	public static void main(String[] args) {
		
		char[] data = new char[1];
		
		Scanner scanner = new Scanner(System.in);
		try {
			data = scanner.next().toCharArray();
		} catch (ArrayIndexOutOfBoundsException e) {
			data = Arrays.copyOf(data, data.length+1);
		}
		
		for (int i = 0; i < data.length; i++)
			System.out.printf("%s",data[i]);
	}

}
