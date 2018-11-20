/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime.interpreter;

import com.google.common.collect.ImmutableSet;
import name.martingeisse.crisp.runtime.Null;
import name.martingeisse.crisp.runtime.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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

	private final LinkedList<TodoItem> todoQueue = new LinkedList<>();
	private final LinkedList<Object> stack = new LinkedList<>();

	public Object evaluate(Object expression) {
		if (!todoQueue.isEmpty() || !stack.isEmpty()) {
			throw new IllegalStateException("interpreter is in an inconsistent state");
		}
		todoQueue.add(new EvaluateItem(expression));
		while (!todoQueue.isEmpty()) {
			todoQueue.removeFirst().run();
		}
		if (stack.size() != 1) {
			throw new IllegalStateException("after evaluation, stack size is " + stack.size());
		}
		return stack.removeFirst();

	}

	private interface TodoItem {
		void run();
	}

	private final class EvaluateItem implements TodoItem {

		private final Object expression;

		public EvaluateItem(Object expression) {
			this.expression = expression;
		}

		@Override
		public void run() {
			if (literalClasses.contains(expression.getClass())) {
				stack.push(expression);
			} else if (expression instanceof Pair) {

			}
		}

	}
}
