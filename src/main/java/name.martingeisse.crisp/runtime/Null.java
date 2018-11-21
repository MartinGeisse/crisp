/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

/**
 *
 */
public final class Null {

	public static final Null INSTANCE = new Null();

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Null;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "null";
	}
}
