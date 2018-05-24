package framework;

import java.util.Scanner;

public final class User {
	
	private static Scanner scan;
	
	public static void openScanner() {
		scan = new Scanner(System.in);
	}
	
	public static String getUserInput() {
		return scan.next();
	}
	
	public static void closeScanner() {
		scan.close();
	}
}
