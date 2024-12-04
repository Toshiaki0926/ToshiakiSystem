package Samplecode;

public class Sample1 {
	public static void main(String[] args){
		int number = 10;
		String text = "Hello";

		System.out.println(number);
		System.out.println(text);

		int total = 0;
		int len = 10;
		for(int i = 0 ; i <= len ; i++){
			total += i;
		}
		double average = (double)total / len;

		System.out.println(average);
	}
}
