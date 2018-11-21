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
		if (text == null) {
			throw new IllegalArgumentException("text cannot be null");
		}
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Identifier) && (((Identifier) obj).getText().equals(text));
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public String toString() {
		return "[" + text + "]";
	}

}
