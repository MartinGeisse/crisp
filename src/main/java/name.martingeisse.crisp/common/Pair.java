/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.common;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

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
			Pair other = (Pair) obj;
			return head.equals(other.head) && tail.equals(other.tail);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(head).append(tail).toHashCode();
	}

	public List<Object> toList() {
		List<Object> list = new ArrayList<>();
		addToList(list);
		return list;
	}

	public void addToList(List<Object> list) {
		list.add(head);
		if (tail instanceof Pair) {
			((Pair) tail).addToList(list);
		} else if (!(tail instanceof Nil)) {
			throw new CrispException("invalid tail list: " + tail);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		toString(this, builder);
		builder.append(')');
		return builder.toString();
	}

	private static void toString(Pair pair, StringBuilder builder) {
		builder.append(pair.getHead());
		Object tail = pair.getTail();
		if (tail instanceof Pair) {
			builder.append(' ');
			toString((Pair) tail, builder);
		} else if (!(tail instanceof Nil)) {
			builder.append(" . ");
			builder.append(tail);
		}
	}

}
