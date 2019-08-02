package name.martingeisse.crisp.common.builtin;

/**
 *
 */
public final class BuiltinRemainder extends FixedParameterBuiltin {

	@Override
	public String getProperName() {
		return "builtinRemainder";
	}

	public int callInternal(int x, int y) {
		return x % y;
	}

}
