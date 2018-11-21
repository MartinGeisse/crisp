/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

import com.google.common.collect.ImmutableSet;
import name.martingeisse.crisp.runtime.builtin.Builtin;

import java.util.*;

/**
 *
 */
public final class Interpreter {

	private static final Set<Class<?>> literalClasses = ImmutableSet.of(
		Null.class,
		Boolean.class,
		Integer.class,
		String.class
	);

	private final Map<String, Object> globalEnvironment = new HashMap<>();
	private final LinkedList<TodoItem> todoStack = new LinkedList<>();
	private final LinkedList<Object> valueStack = new LinkedList<>();

	public void defineGlobal(String identifier, Object value) {
		globalEnvironment.put(identifier, value);
	}

	public Object evaluate(Object expression) {
		if (!todoStack.isEmpty() || !valueStack.isEmpty()) {
			throw new IllegalStateException("interpreter is in an inconsistent state");
		}
		todoStack.push(new EvaluateItem(expression, new HashMap<>(globalEnvironment)));
		while (!todoStack.isEmpty()) {
			todoStack.pop().run();
		}
		if (valueStack.size() != 1) {
			throw new IllegalStateException("after evaluation, value stack size is " + valueStack.size());
		}
		return valueStack.pop();
	}

	private interface TodoItem {
		void run();
	}

	private final class EvaluateItem implements TodoItem {

		private final Object expression;
		private final Map<String, Object> environment;

		public EvaluateItem(Object expression, Map<String, Object> environment) {
			this.expression = expression;
			this.environment = environment;
		}

		@Override
		public void run() {
			if (literalClasses.contains(expression.getClass())) {
				valueStack.push(expression);
			} else if (expression instanceof Identifier) {
				String text = ((Identifier) expression).getText();
				Object value = environment.get(text);
				if (value == null) {
					throw new CrispException("undefined identifier: " + text);
				}
				valueStack.push(value);
			} else if (expression instanceof Pair) {
				List<Object> list = ((Pair) expression).toList();
				todoStack.push(new CallItem(list.size()));
				for (int i = list.size() - 1; i >= 0; i--) {
					todoStack.push(new EvaluateItem(list.get(i), environment));
				}
			}
		}

	}

	private final class CallItem implements TodoItem {

		private final int argumentCount;

		public CallItem(int argumentCount) {
			this.argumentCount = argumentCount;
		}

		@Override
		public void run() {
			List<Object> arguments = new ArrayList<>();
			for (int i = argumentCount - 1; i >= 0; i--) {
				arguments.set(i, valueStack.pop());
			}
			Object callTarget = valueStack.pop();
			if (callTarget instanceof Function) {
				Function function = (Function) callTarget;
				Map<String, Object> bodyEnvironment = function.buildBodyEnvironment(arguments);
				todoStack.push(new EvaluateItem(function.getBody(), bodyEnvironment));
			} else if (callTarget instanceof Builtin) {
				Builtin builtin = (Builtin)callTarget;
				valueStack.push(builtin.call(arguments));
			} else {
				throw new CrispException("not a function or builtin: " + callTarget);
			}
		}
	}

}
