import java.util.Scanner;

public class BaekJoon25191 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int chicken = scanner.nextInt(), coke = scanner.nextInt(),
			bear = scanner.nextInt(), can = 0, idx = 0;
		idx = coke / 2 + bear;
		for (int i = 0; i < idx; i++) {
			if (coke > 1) {
				coke = coke - 2;
				can++;
				continue;
			}
			else if (bear > 0) {
				bear = bear - 1;
				can++;
				continue;
			}
			else break;
		}
		if (can > chicken) System.out.println(chicken);
		else System.out.println(can);
		
		
	}

}
