package name.martingeisse.crisp.common.builtin;

/**
 *
 */
public final class BuiltinDivide extends FixedParameterBuiltin {

	@Override
	public String getProperName() {
		return "builtinDivide";
	}

	public int callInternal(int x, int y) {
		return x / y;
	}

}
