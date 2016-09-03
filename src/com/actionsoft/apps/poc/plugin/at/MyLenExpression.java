package com.actionsoft.apps.poc.plugin.at;

import com.actionsoft.bpms.commons.at.AbstExpression;
import com.actionsoft.bpms.commons.at.ExpressionContext;

/**
 * 返回字符串长度
 */
public class MyLenExpression extends AbstExpression {

	public MyLenExpression(final ExpressionContext atContext, String expressionValue) {
		super(atContext, expressionValue);
	}

	public String execute(String expression) {
		// 取第1个参数
		String str = getParameter(expression, 1);
		return String.valueOf(str.length());
	}
}
