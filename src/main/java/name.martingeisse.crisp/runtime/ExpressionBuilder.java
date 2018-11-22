package name.martingeisse.crisp.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public final class ExpressionBuilder {

	// prevent instantiation
	private ExpressionBuilder() {
	}

	public static Pair pair(Object head, Object tail) {
		return new Pair(head, tail);
	}

	public static Object list(Object... elements) {
		Object list = Null.INSTANCE;
		for (int i = elements.length - 1; i >= 0; i--) {
			list = pair(elements[i], list);
		}
		return list;
	}

	public static Identifier identifier(String text) {
		return new Identifier(text);
	}

	public static Keyword keyword(String text) {
		return new Keyword(text);
	}

	public static Pair call(String functionName, Object... elements) {
		return pair(identifier(functionName), list(elements));
	}

	public static Object lambda(Object body, String... parameters) {
		Object parameterList = Null.INSTANCE;
		for (int i = parameters.length - 1; i >= 0; i--) {
			parameterList = pair(identifier(parameters[i]), parameterList);
		}
		return list(keyword("lambda"), parameterList, body);
	}

}
