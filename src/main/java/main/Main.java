package main;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class Main {
    public static String replaceVariables(String code, int startLine, int endLine) {
        // JavaParserでコードを解析
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(code).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

        // 変数名を格納するセットを作成
        Set<String> varNames = new HashSet<>();

        // 行範囲フィルタを作成
        LineRangeFilter lineRangeFilter = new LineRangeFilter(startLine, endLine);

        // 行範囲フィルタを使って指定された範囲で空欄に置き換える処理を実行
        cu.accept(lineRangeFilter, null);

        // 変更後のソースコードを返す
        return cu.toString();
    }
}
