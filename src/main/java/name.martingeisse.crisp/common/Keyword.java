/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.common;

/**
 *
 */
public final class Keyword {

	private final String text;

	public Keyword(String text) {
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
		return (obj instanceof Keyword) && (((Keyword) obj).getText().equals(text));
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public String toString() {
		return "#" + text;
	}

}
