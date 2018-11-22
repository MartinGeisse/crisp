package name.martingeisse.crisp.runtime.builtin;

/**
 *
 */
public final class BuiltinMultiply extends FixedParameterBuiltin {

	@Override
	public String getProperName() {
		return "builtinMultiply";
	}

	public int callInternal(int x, int y) {
		return x * y;
	}

}
