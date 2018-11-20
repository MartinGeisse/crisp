/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

/**
 *
 */
public final class Identifier {

	private final String text;

	public Identifier(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		...
	}

	@Override
	public int hashCode() {
		...
	}

}
