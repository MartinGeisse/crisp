package name.martingeisse.crisp.runtime.builtin;

/**
 *
 */
public final class BuiltinSubtract extends FixedParameterBuiltin {

	@Override
	public String getProperName() {
		return "builtinSubtract";
	}

	public int callInternal(int x, int y) {
		return x - y;
	}

}
