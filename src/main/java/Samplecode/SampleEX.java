package Samplecode;

public class SampleEX {
	public static void main(String[] args) {
		
		int a = 0;
		
		while(a < 10) {
			System.out.print(a);
			a++;
		}
		
		System.out.println("");
		
		int[] b = {1, 6, 4, 2, 8};
		
		for(int i = 0 ; i < b.length ; i++) {
			for(int j = 0 ; j < b.length - i - 1 ; j++) {
				if(b[j] > b[j + 1]) {
					int c = b[j];
					b[j] = b[j + 1];
					b[j + 1] = c;
				}
			}
		}
		
		for(int n : b) {
			System.out.print(n + " ");
		}
		
	}
}
