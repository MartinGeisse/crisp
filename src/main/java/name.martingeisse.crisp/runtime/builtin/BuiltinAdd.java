package name.martingeisse.crisp.runtime.builtin;

/**
 *
 */
public final class BuiltinAdd extends FixedParameterBuiltin {

	@Override
	public String getProperName() {
		return "builtinAdd";
	}

	public int callInternal(int x, int y) {
		return x + y;
	}

}
