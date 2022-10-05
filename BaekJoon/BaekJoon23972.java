import java.util.Scanner;

public class BaekJoon23972 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		long payment = scanner.nextLong(), mul = scanner.nextLong(), have = -1L;
		
		if (mul != 1) {
			have = (payment*mul)/(mul-1L);	
			if ((payment * mul)%(mul - 1L) != 0L)
				have +=1L;
		}
		System.out.println(have);
		
	}

}
