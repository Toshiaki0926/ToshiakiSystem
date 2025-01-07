package hint;

import java.util.Set;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableReplacer extends VoidVisitorAdapter<Void> {
	//private Set<String> varNames = new HashSet<>();
	private Set<String> varNames;

	// 新しいコンストラクタを追加
	public VariableReplacer(Set<String> varNames) {
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
		binaryExpr.getLeft().ifStringLiteralExpr(expr -> expr.setValue("_"));
		binaryExpr.getRight().ifStringLiteralExpr(expr -> expr.setValue("_"));
	}

	@Override
	public void visit(AssignExpr assignExpr, Void arg) {
		super.visit(assignExpr, arg);
		// 代入式の左辺と右辺を置き換え
		assignExpr.getTarget().ifStringLiteralExpr(expr -> expr.setValue("_"));
		assignExpr.getValue().ifStringLiteralExpr(expr -> expr.setValue("_"));
	}

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
	
	@Override
	public void visit(CharLiteralExpr charLiteralExpr, Void arg) {
	    super.visit(charLiteralExpr, arg);
	    // charリテラルを'_'に置き換える
	    charLiteralExpr.setValue("_");
	}

	@Override
	public void visit(MethodCallExpr methodCallExpr, Void arg) {
		super.visit(methodCallExpr, arg);
		// MethodCallExprの引数を空欄に置き換え
		methodCallExpr.getArguments().forEach(argExpr -> {
			argExpr.replace(new NameExpr("_"));
		});
	}

	@Override
	public void visit(NameExpr nameExpr, Void arg) {
		super.visit(nameExpr, arg);
		// 変数名かどうかをチェックし、セットに含まれる場合のみ置き換える
		if (varNames.contains(nameExpr.getNameAsString())) {
			nameExpr.setName("_");
		}
	}

	@Override
    public void visit(CastExpr castExpr, Void arg) {
        super.visit(castExpr, arg);
        // キャスト型を「(_)」に置き換え
        String modifiedCast = String.format("(_) %s", castExpr.getExpression());
        
        // CastExprを新しいNameExprに置き換え
        castExpr.replace(new NameExpr(modifiedCast));
    }
}
