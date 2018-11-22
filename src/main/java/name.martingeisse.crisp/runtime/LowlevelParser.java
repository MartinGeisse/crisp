package name.martingeisse.crisp.runtime;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Parses a string to an s-expression.
 */
public final class LowlevelParser {

	private final Reader reader;
	private char c = ' ';
	private boolean eof = false;

	public LowlevelParser(String s) {
		this(new StringReader(s));
	}

	public LowlevelParser(Reader reader) {
		this.reader = reader;
	}

	/**
	 * Returns null on EOF.
	 */
	public Object parse() throws IOException {
		Object result = parseNested();
		if (result == null) {
			if (eof) {
				return null;
			} else {
				throw new IOException("end of list outside a list");
			}
		}
		return result;
	}

	private Object parseNested() throws IOException {
		skipWhitespace();
		if (eof) {
			return null;
		}
		switch (c) {

			case '(':
				return readList();

			case ')':
				return null;

			case '#':
				return readKeyword();

			case '"':
				return readString();

		}
		if ((c >= '0' && c <= '9') || c == '+' || c == '-') {
			return readNumber();
		}
		if (Character.isJavaIdentifierStart(c)) {
			return readIdentifier();
		}
		throw new IOException("invalid first character for expression: " + c);
	}

	private void read() throws IOException {
		int x = reader.read();
		eof = (x < 0);
		// Those few places that could mistake this for a real closing paranthesis explicitly check for EOF. And we
		// can't mistake a closing parenthesis to be legally part of any expression except string literals, which
		// also handles EOF.
		c = eof ? ')' : (char)x;
	}

	private void skipWhitespace() throws IOException {
		// no unicode whitespace for now
		while (c <= 32) {
			read();
		}
	}

	private Object readNumber() throws IOException {
		// no float literals for now
		StringBuilder builder = new StringBuilder();
		if (c != '+') {
			builder.append(c);
		}
		read();
		while (c >= '0' && c <= '9') {
			builder.append(c);
			read();
		}
		return Integer.parseInt(builder.toString());
	}

	private String readString() throws IOException {
		// no escape sequences for now, other than literal escapes
		StringBuilder builder = new StringBuilder();
		read();
		while (true) {
			if (eof) {
				throw new IOException("EOF in string literal");
			} else if (c == '"') {
				break;
			} else if (c == '\\') {
				read();
				builder.append(c);
				read();
			} else {
				builder.append(c);
				read();
			}
		}
		read();
		return builder.toString();
	}

	private Identifier readIdentifier() throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(c);
		read();
		while (Character.isJavaIdentifierPart(c)) {
			builder.append(c);
			read();
		}
		return new Identifier(builder.toString());
	}

	private Keyword readKeyword() throws IOException {
		StringBuilder builder = new StringBuilder();
		read();
		while (Character.isJavaIdentifierPart(c)) {
			builder.append(c);
			read();
		}
		return new Keyword(builder.toString());
	}

	private Object readList() throws IOException {
		// no improper lists (tail other than list or null) for now
		read();
		Object list = parseTail();
		read();
		return list;
	}

	private Object parseTail() throws IOException {
		Object head = parseNested();
		if (head == null) {
			return Null.INSTANCE;
		}
		Object tail = parseTail();
		return new Pair(head, tail);
	}

}
