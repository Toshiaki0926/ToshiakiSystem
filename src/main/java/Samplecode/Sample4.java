package Samplecode;

public class Sample4 {
	public static void main(String[] args) {
		System.out.println("キーボードから入力してください");
		int year = new java.util.Scanner(System.in).nextInt();

		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					System.out.println("うるう年です。");
				} else {
					System.out.println("うるう年ではありません。");
				}
			} else {
				System.out.println("うるう年です。");
			}
		} else {
			System.out.println("うるう年ではありません。");
		}
	}
}