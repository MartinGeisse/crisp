package name.martingeisse.crisp.runtime;

import java.io.Reader;

/**
 *
 */
public class SourceFileParser {

	private final Interpreter interpreter;
	private final LowlevelParser lowlevelParser;

	public SourceFileParser(Interpreter interpreter, LowlevelParser lowlevelParser) {
		this.interpreter = interpreter;
		this.lowlevelParser = lowlevelParser;
	}

	public SourceFileParser(Interpreter interpreter, String s) {
		this(interpreter, new LowlevelParser(s));
	}

	public SourceFileParser(Interpreter interpreter, Reader reader) {
		this(interpreter, new LowlevelParser(reader));
	}

	public void parse() {
		// TODO
	}

}
