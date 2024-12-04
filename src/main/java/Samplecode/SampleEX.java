package Samplecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SampleEX {

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

		// ProcessBuilderでPythonスクリプトを呼び出す
		ProcessBuilder pb = new ProcessBuilder("C:\\Users\\morit\\AppData\\Local\\Programs\\Python\\Python313\\python.exe", "C:\\pleiades\\2023-12\\workspace\\Hint1\\python\\script2.py");

		try {
			Process process = pb.start();

			// Pythonスクリプトにソースコードを渡す
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			writer.write(fileContent);  // ソースコードを渡す
			writer.flush();
			writer.close();

			// Pythonスクリプトの標準出力を読み取る
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			StringBuilder output = new StringBuilder();
//			String line;
//			int lineNumber = 0;  // 行番号を管理する変数
//			List<String> lines = new ArrayList<>();
//
//			// 最初に全ての行を読み込んでリストに格納する
//			while ((line = reader.readLine()) != null) {
//			    lines.add(line);
//			}
//
//			// 最初の1行と最後の1〜2行をスキップして、残りの行を処理する
//			for (int i = 1; i < lines.size() - 2; i++) {  // 最初の1行と最後の1〜2行をスキップ
//			    String currentLine = lines.get(i);
//			    output.append(currentLine).append("\n");
//			    System.out.println(currentLine);  // 出力する
//			}
			
			//スクリプト読み取り
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			List<String> lines = new ArrayList<>();

			// Pythonスクリプトの出力をすべて取得
			while ((line = reader.readLine()) != null) {
			    lines.add(line);
			}

			// JSON部分のみを抽出
			StringBuilder jsonResponse = new StringBuilder();
			boolean jsonStarted = false;

			for (String currentLine : lines) {
			    if (currentLine.trim().startsWith("[") || jsonStarted) {
			        jsonStarted = true;  // JSONの開始を検出
			        jsonResponse.append(currentLine).append("\n");
			    }
			}

			// JSON解析のために整形
//			String finalOutput = jsonResponse.toString().trim();
//			System.out.println("Final JSON Response: \n" + finalOutput);
			
//			String finalOutput = output.toString();
//			System.out.println(finalOutput);

			
			// 標準エラー出力を読み取る
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String errorLine;
			while ((errorLine = errorReader.readLine()) != null) {
				System.err.println("Error: " + errorLine);  // エラーメッセージを表示
			}

//			String jsonInput = output.toString();
//			jsonInput.replaceAll("json", "").replaceAll("```", "");
//			System.out.println(jsonInput);

			// プロセス終了を待機
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Python script executed successfully.");
			} else {
				System.out.println("Python script failed with exit code: " + exitCode);
			}

			reader.close();
			errorReader.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
