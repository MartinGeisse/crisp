/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

/**
 *
 */
public final class Pair {

	private final Object head;
	private final Object tail;

	public Pair(Object head, Object tail) {
		this.head = head;
		this.tail = tail;
	}

	public Object getHead() {
		return head;
	}

	public Object getTail() {
		return tail;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			...
		}
	}

	@Override
	public int hashCode() {
		...
	}
}
