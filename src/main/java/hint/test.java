package hint;

public class test {
	public static void main(String[] args) {
		String code = "package Samplecode;\n"
				+ "\n"
				+ "public class SampleEX {\n"
				+ "	public static void main(String[] args) {\n"
				+ "		\n"
				+ "		int a = 0;\n"
				+ "		\n"
				+ "		while(a < 10) {\n"
				+ "			System.out.print(a);\n"
				+ "			a++;\n"
				+ "		}\n"
				+ "		\n"
				+ "		System.out.println(\"\");\n"
				+ "		\n"
				+ "		int[] b = {1, 6, 4, 2, 8};\n"
				+ "		\n"
				+ "		for(int i = 0 ; i < b.length ; i++) {\n"
				+ "			for(int j = 0 ; j < b.length - i - 1 ; j++) {\n"
				+ "				if(b[j] > b[j + 1]) {\n"
				+ "					int c = b[j];\n"
				+ "					b[j] = b[j + 1];\n"
				+ "					b[j + 1] = c;\n"
				+ "				}\n"
				+ "			}\n"
				+ "		}\n"
				+ "		\n"
				+ "		for(int n : b) {\n"
				+ "			System.out.print(n + \" \");\n"
				+ "		}\n"
				+ "		\n"
				+ "	}\n"
				+ "}\n"
				+ "";
		
		String ans = Main2.replaceVariables(code);
		
		System.out.println(ans);
	}

}
