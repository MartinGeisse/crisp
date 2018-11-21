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

	public static List<Object> list(Object... elements) {
		List<Object> list = new ArrayList<>();
		for (Object element : elements) {
			list.add(element);
		}
		return list;
	}

	public static Identifier identifier(String text) {
		return new Identifier(text);
	}

	public static List<Object> call(String functionName, Object... elements) {
		List<Object> result = list(elements);
		result.add(0, identifier(functionName));
		return result;
	}

}
