/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.common;

/**
 *
 */
public final class Nil {

	public static final Nil INSTANCE = new Nil();

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Nil;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "nil";
	}
}
