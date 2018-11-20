/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

/**
 *
 */
public final class Null {

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Null;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
