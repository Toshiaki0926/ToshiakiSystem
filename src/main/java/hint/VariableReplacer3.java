package hint;

import java.util.Set;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableReplacer3 extends VoidVisitorAdapter<Void> {
	//private Set<String> varNames = new HashSet<>();
	private Set<String> varNames;

	// 新しいコンストラクタを追加
	public VariableReplacer3(Set<String> varNames) {
		this.varNames = varNames;
	}


	@Override
	public void visit(VariableDeclarator var, Void arg) {
		super.visit(var, arg);
		// 変数宣言の変数名をセットに追加
		varNames.add(var.getNameAsString());
		// 変数名を "_" に置き換え
		var.setName("_");
	}

	@Override
	public void visit(BinaryExpr binaryExpr, Void arg) {
		super.visit(binaryExpr, arg);
		// 条件式の左辺と右辺を置き換え
		binaryExpr.getLeft().ifNameExpr(expr -> {
			expr.setName("_");
		});
		binaryExpr.getRight().ifNameExpr(expr -> {
			expr.setName("_");
		});
	}
//
//	@Override
//	public void visit(AssignExpr assignExpr, Void arg) {
//		super.visit(assignExpr, arg);
//		// 代入式の左辺と右辺を置き換え
//		assignExpr.getTarget().ifNameExpr(expr -> {
//			expr.setName("_");
//		});
//		assignExpr.getValue().ifNameExpr(expr -> {
//			expr.setName("_");
//		});
//	}
//	
	@Override
	public void visit(ArrayAccessExpr arrayAccessExpr, Void arg) {
	    super.visit(arrayAccessExpr, arg);
	    // 配列名を "_" に置き換え
	    arrayAccessExpr.getName().ifNameExpr(expr -> {
	        expr.setName("_");
	    });
	    // インデックス部分も "_" に置き換え
	    arrayAccessExpr.getIndex().ifNameExpr(expr -> {
	        expr.setName("_");
	    });
	}
//
	@Override
	public void visit(StringLiteralExpr stringLiteralExpr, Void arg) {
		super.visit(stringLiteralExpr, arg);
		// 文字列リテラル（定数）を空欄に置き換え
		stringLiteralExpr.setValue("_");
	}

	@Override
	public void visit(IntegerLiteralExpr integerLiteralExpr, Void arg) {
		super.visit(integerLiteralExpr, arg);
		// 整数リテラル（定数）を空欄に置き換え
		integerLiteralExpr.setValue("_");
	}
//
//	@Override
//	public void visit(MethodCallExpr methodCallExpr, Void arg) {
//		super.visit(methodCallExpr, arg);
//		// MethodCallExprの引数を空欄に置き換え
//		methodCallExpr.getArguments().forEach(argExpr -> {
//			if (argExpr instanceof NameExpr) {
////				NameExpr nameExpr = (NameExpr) argExpr;
//				argExpr.replace(new NameExpr("_"));
//			}
//		});
//	}


	@Override
	public void visit(CastExpr castExpr, Void arg) {
	    super.visit(castExpr, arg);
	    // キャスト部分を "_"
	    String modifiedCast = String.format("(_) %s", castExpr.getExpression());
	    // キャスト式を新しいNameExprに置き換え
	    castExpr.replace(new NameExpr(modifiedCast));
	}
}
