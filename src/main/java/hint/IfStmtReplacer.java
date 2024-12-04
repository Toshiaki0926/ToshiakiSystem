package hint;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class IfStmtReplacer extends VoidVisitorAdapter<Void> {

	@Override
	public void visit(IfStmt ifStmt, Void arg) {
		super.visit(ifStmt, arg);

		// if文の条件式を精査
		ifStmt.getCondition().ifBinaryExpr(binaryExpr -> {
			replaceBinaryExpr(binaryExpr);
		});
	}

	// BinaryExprを再帰的に置き換える
	private void replaceBinaryExpr(BinaryExpr binaryExpr) {
		// 左右のオペランドを処理する
		if (binaryExpr.getLeft() instanceof BinaryExpr) {
			replaceBinaryExpr((BinaryExpr) binaryExpr.getLeft());
		} else {
			binaryExpr.setLeft(new NameExpr("_"));
		}

		if (binaryExpr.getRight() instanceof BinaryExpr) {
			replaceBinaryExpr((BinaryExpr) binaryExpr.getRight());
		} else {
			binaryExpr.setRight(new NameExpr("_"));
		}
	}
}
