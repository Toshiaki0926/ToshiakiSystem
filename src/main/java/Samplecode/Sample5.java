package Samplecode;

public class Sample5 {
	public static void main(String[] args) {
		System.out.println("キーボードから入力してください");
		int num = new java.util.Scanner(System.in).nextInt();
		
		if(num < 10) {
			num = num + 10 + 20 + 30;
		} else {
			num = num - 1 - 2 - 3;
		}
		System.out.println(num);
	}

}
