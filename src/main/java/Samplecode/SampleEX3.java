package Samplecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

//成功したプログラム！！！
public class SampleEX3 {

	public static void main(String[] args) {

		// ソースコードを文字列として定義
		String fileContent = """
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

				        for(int i = 0; i < score.length; i++) {
				            total += score[i];
				        }
				        average = (double)total / score.length;

				        System.out.println(average);
				    }
				}
				""";

		try {
			String PythonPath = "C:\\pleiades\\2023-12\\workspace\\Hint1\\python\\script3.py";
			// Pythonスクリプトを実行
			ProcessBuilder pb = new ProcessBuilder("C:\\Users\\morit\\AppData\\Local\\Programs\\Python\\Python313\\python.exe", PythonPath);
			Process process = pb.start();

			// Pythonスクリプトにソースコードを渡す
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			writer.write(fileContent);  // ソースコードを渡す
			writer.flush();
			writer.close();

			// Pythonの標準出力を読み込む
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			StringBuilder output = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			// 出力されたJSONを表示
			System.out.println("Python Output: " + output.toString());

			// Pythonスクリプトの終了コードを確認
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Python script executed successfully.");
			} else {
				System.out.println("Python script failed with exit code: " + exitCode);
			}
			
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			StringBuilder errorOutput = new StringBuilder();
			while ((line = errorReader.readLine()) != null) {
			    errorOutput.append(line).append("\n");
			}
			if (exitCode != 0) {
			    System.out.println("Python script failed with exit code: " + exitCode);
			    System.out.println("Error output: " + errorOutput.toString());
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
