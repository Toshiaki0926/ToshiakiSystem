package hint;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableReplacerEX {
    public static void main(String[] args) {
        // サンプルJavaコード
        String sampleCode = """
            public class Sample {
                public void test() {
                    int a = 42;
                    char b = 'c';
                    String name = "John";
                    System.out.println(a);
                    System.out.println(name);
                }
            }
        """;

        // 空欄に置き換える対象の変数名リスト
        Set<String> targetVariables = new HashSet<>();
        targetVariables.add("a");
        targetVariables.add("name");

        try {
            // JavaParserでソースコードを解析
            CompilationUnit cu = JavaParser.parse(sampleCode);

            // IntegerLiteralExpr, CharLiteralExpr, NameExprを置換する
            cu.accept(new LiteralBlanker(targetVariables), null);

            // 結果を出力
            System.out.println(cu.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // リテラルと名前を空欄に置き換えるVisitor
    private static class LiteralBlanker extends VoidVisitorAdapter<Void> {
        private final Set<String> targetVariables;

        public LiteralBlanker(Set<String> targetVariables) {
            this.targetVariables = targetVariables;
        }

        @Override
        public void visit(IntegerLiteralExpr n, Void arg) {
            super.visit(n, arg);
            // 整数リテラルを空欄に置き換え
            n.replace(new IntegerLiteralExpr("____"));
        }

        @Override
        public void visit(CharLiteralExpr n, Void arg) {
            super.visit(n, arg);
            // 文字リテラルを空欄に置き換え
            n.replace(new CharLiteralExpr("____"));
        }

        @Override
        public void visit(NameExpr n, Void arg) {
            super.visit(n, arg);
            // 指定された変数名を空欄に置き換え
            if (targetVariables.contains(n.getNameAsString())) {
                n.replace(new NameExpr("____"));
            }
        }
    }
}
