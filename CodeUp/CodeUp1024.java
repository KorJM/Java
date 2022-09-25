import java.util.Arrays;
import java.util.Scanner;

public class CodeUp1024 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		char[] data = new char[1];
		
		try {
			data = scanner.next().toCharArray();
		} catch (ArrayIndexOutOfBoundsException e) {
			data = Arrays.copyOf(data, data.length+1);
		}
		
		for (int i = 0; i < data.length; i++)
			System.out.println("'"+data[i]+"'");
		
	}

}
