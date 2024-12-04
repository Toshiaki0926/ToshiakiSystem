package Samplecode;

public class Sample6 {
	public static void main(String[] args) {
		int[] score = new int[6];
		int total = 0;
		double average;
		
		score[0] = 86;
		score[1] = 74;
		score[2] = 91;
		score[3] = 59;
		score[4] = 100;
		score[5] = 41;
		
		for(int i = 0 ; i < score.length ; i++) {
			total += score[i];
		}
		average = (double)total / score.length;
		
		System.out.println(average);
	}

}
