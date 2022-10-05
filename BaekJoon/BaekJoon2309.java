import java.util.Arrays;
import java.util.Scanner;

public class BaekJoon2309 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int[] array = new int[9];
		int sum = 0;
		
		for (int i = 0; i < 9; i++) array[i] = scanner.nextInt();
		
		for (int i = 0; i < array.length; i++) sum += array[i];
		
		for(int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if ((sum - array[i] - array[j]) == 100) {
					array[i] = 0;
					array[j] = 0;
				}
			}
		}
		
		Arrays.sort(array);
		
		for(int i = 2; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
}
