package divide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

//成功したプログラム！！！
public class PythonExecutor {

	public static void parseCodeToJson(String source_code, int source_id) {

		// ソースコードを文字列として定義
		String fileContent = source_code;
		
		try {
			String PythonPath = "C:\\pleiades\\2023-12\\workspace\\Hint1\\python\\script3.py";
			// Pythonスクリプトを実行
			ProcessBuilder pb = new ProcessBuilder("C:\\Users\\morit\\AppData\\Local\\Programs\\Python\\Python313\\python.exe", PythonPath);
			Process process = pb.start();

			// ソースコードをUTF-8でエンコードして渡す
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);
			PrintWriter writer = new PrintWriter(outputStreamWriter);
			writer.write(fileContent);  // そのままUTF-8で渡す
			writer.flush();
			writer.close();

			// Pythonの標準出力を読み込む
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
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
			
			JsonToDatabase.main(source_id);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
