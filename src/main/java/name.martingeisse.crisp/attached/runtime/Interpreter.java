package name.martingeisse.crisp.attached.runtime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import name.martingeisse.crisp.common.*;
import name.martingeisse.crisp.common.builtin.Builtin;

import java.util.*;

/**
 *
 */
public final class Interpreter {

	private static final Set<Class<?>> selfReferringLiteralClasses = ImmutableSet.of(
		Nil.class,
		Boolean.class,
		Integer.class,
		String.class
	);

	private final Map<String, Object> globalEnvironmentBindings = new HashMap<>();
	private final Environment globalEnvironment = new Environment(globalEnvironmentBindings, null);

//region definitions

	public void defineGlobal(String identifier, Object value) {
		globalEnvironmentBindings.put(identifier, value);
	}

	public void defineGlobals(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			defineGlobal(entry.getKey(), entry.getValue());
		}
	}

//endregion

//region evaluation

	public Object evaluate(Object expression) {
		return evaluate(expression, globalEnvironment);
	}

	public Object evaluate(Object expression, Environment environment) {
		if (selfReferringLiteralClasses.contains(expression.getClass())) {
			return expression;
		} else if (expression instanceof Keyword) {
			String text = ((Keyword) expression).getText();
			return evaluateStandaloneKeyword(text);
		} else if (expression instanceof Identifier) {
			String text = ((Identifier) expression).getText();
			return environment.get(text);
		} else if (expression instanceof Pair) {
			List<Object> list = ((Pair) expression).toList();
			if (list.get(0) instanceof Keyword) {
				return evaluateKeywordList(list, environment);
			} else {
				Object callable = evaluate(list.get(0), environment);
				Object[] arguments = new Object[list.size() - 1];
				for (int i = 0; i < arguments.length; i++) {
					arguments[i] = evaluate(list.get(i + 1), environment);
				}
				if (callable instanceof Function) {
					Function function = (Function) callable;
					Environment bodyEnvironment = function.buildBodyEnvironment(arguments);
					return evaluate(function.getBody(), bodyEnvironment);
				} else if (callable instanceof Builtin) {
					Builtin builtin = (Builtin) callable;
					return builtin.call(arguments);
				} else {
					throw new CrispException("not a function or builtin: " + callable);
				}

			}
		} else {
			throw new CrispException("invalid expression: " + expression);
		}
	}

	private Object evaluateStandaloneKeyword(String text) {
		switch (text) {

			case "nil":
				return Nil.INSTANCE;

			case "false":
				return false;

			case "true":
				return true;

			default:
				throw new CrispException("invalid usage of keyword #" + text + " as expression");
		}
	}

	private Object evaluateKeywordList(List<Object> list, Environment environment) {
		String keyword = ((Keyword) list.get(0)).getText();
		switch (keyword) {

			case "lambda": {

				// deconstruct lambda expression
				if (list.size() != 3 || !(list.get(1) instanceof Pair)) {
					throw new CrispException("lambda usage: (lambda (parameters) body)");
				}
				List<Object> parameterSpecifiers = ((Pair) list.get(1)).toList();
				Object body = list.get(2);

				// parse parameter specifiers
				List<Function.Parameter> parameters = new ArrayList<>();
				for (Object specifier : parameterSpecifiers) {
					if (specifier instanceof Identifier) {
						parameters.add(new Function.Parameter(((Identifier) specifier).getText()));
					} else {
						throw new CrispException("invalid parameter specifier: " + specifier);
					}
				}

				// build function
				return new Function("anonymous", environment, ImmutableList.copyOf(parameters), body);
			}

			default:
				throw new CrispException("invalid usage of keyword #" + keyword + " as list keyword");

		}
	}

//endregion

}
