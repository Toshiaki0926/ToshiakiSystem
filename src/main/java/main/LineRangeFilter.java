package main;

import java.util.HashSet;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import hint.ForStmtReplacer;
import hint.IfStmtReplacer;
import hint.VariableReplacer;

public class LineRangeFilter extends VoidVisitorAdapter<Void> {
	private final int startLine;
	private final int endLine;

	public LineRangeFilter(int startLine, int endLine) {
		this.startLine = startLine;
		this.endLine = endLine;
	}

	@Override
	public void visit(ForStmt forStmt, Void arg) {
		// ForStmt の行範囲をチェック
		forStmt.getRange().ifPresent(range -> {
			if (range.begin.line >= startLine && range.end.line <= endLine) {
				forStmt.accept(new ForStmtReplacer(), arg); // ForStmtReplacerを呼び出す
			}
		});
		super.visit(forStmt, arg);
	}

	@Override
	public void visit(IfStmt ifStmt, Void arg) {
		// IfStmt の行範囲をチェック
		ifStmt.getRange().ifPresent(range -> {
			if (range.begin.line >= startLine && range.end.line <= endLine) {
				ifStmt.accept(new IfStmtReplacer(), arg); // IfStmtReplacerを呼び出す
			}
		});
		super.visit(ifStmt, arg);
	}

	@Override
	public void visit(MethodDeclaration methodDeclaration, Void arg) {
		// MethodDeclaration の行範囲をチェック
		methodDeclaration.getRange().ifPresent(range -> {
			if (range.begin.line >= startLine && range.end.line <= endLine) {
				methodDeclaration.accept(new VariableReplacer(new HashSet<>()), arg); // VariableReplacerを呼び出す
			}
		});
		super.visit(methodDeclaration, arg);
	}
}
