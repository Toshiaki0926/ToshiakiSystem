package hint;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class Main2 {
	public static String replaceVariables(String code) {
		// JavaParserでコードを解析
		JavaParser parser = new JavaParser();
		CompilationUnit cu = parser.parse(code).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

		// 変数名を格納するセットを作成
		Set<String> varNames = new HashSet<>();

		// 変数名、条件式、定数を指定された範囲で空欄に置き換える処理
		cu.accept(new ForStmtReplacer(), null);
//		cu.accept(new ForStmtReplacer2(), null);
		cu.accept(new IfStmtReplacer(), null);
//		cu.accept(new VariableReplacer(varNames), null);
//		cu.accept(new VariableReplacer2(varNames), null);
		cu.accept(new VariableReplacer3(varNames), null);

		// 変更後のソースコードを返す
		return cu.toString();
	}
}