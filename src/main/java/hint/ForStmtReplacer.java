package hint;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ForStmtReplacer extends VoidVisitorAdapter<Void> {
	private Set<String> varNames = new HashSet<>();
	@Override
	public void visit(ForStmt forStmt, Void arg) {
		super.visit(forStmt, arg);

		// 初期化部分の処理は不要
		// 変数名を varNames に追加する
		forStmt.getInitialization().forEach(init -> {
			if (init instanceof VariableDeclarationExpr) {
				VariableDeclarationExpr varDecl = (VariableDeclarationExpr) init;
				varDecl.getVariables().forEach(var -> {
					varNames.add(var.getNameAsString());
				});
			}
		});

		// 条件式を空欄に置き換え
		forStmt.getCompare().ifPresent(comp -> {
			comp.replace(new BinaryExpr(new NameExpr("_"), new NameExpr("_"), BinaryExpr.Operator.LESS_EQUALS));
		});

		// 更新部分を空欄に置き換え
		forStmt.getUpdate().forEach(update -> {
			if (update instanceof UnaryExpr) {
				UnaryExpr unaryExpr = (UnaryExpr) update;
				unaryExpr.getExpression().replace(new NameExpr("_"));
			} else {
				update.replace(new AssignExpr(new NameExpr("_"), new NameExpr("_"), AssignExpr.Operator.ASSIGN));
			}
		});

		// ボディの式も空欄に置き換え
		//		forStmt.getBody().ifBlockStmt(block -> {
		//			// VariableReplacerを使用してボディ内の変数やリテラルを置き換え
		////			block.accept(new VariableReplacer(), arg);
		//			block.accept(new VariableReplacer(varNames), arg);
		//
		//			// ForStmtReplacerを使用してボディ内のfor文を精査(2重3重ループ用)
		//			block.accept(new ForStmtReplacer(), arg);
		//		});
	}
}
