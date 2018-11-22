package name.martingeisse.crisp.runtime.builtin;

import name.martingeisse.crisp.runtime.CrispException;

import java.lang.reflect.Method;

/**
 *
 */
public abstract class FixedParameterBuiltin implements Builtin {

	private Method method;

	public FixedParameterBuiltin() {
		this.method = findCallInternalMethod();
	}

	private Method findCallInternalMethod() {
		for (Method method : getClass().getMethods()) {
			if (method.getName().equals("callInternal")) {
				return method;
			}
		}
		throw new RuntimeException("FixedParameterBuiltin without callInternal() method: " + this);
	}

	@Override
	public Object call(Object... arguments) {
		Object result;
		try {
			result = method.invoke(this, arguments);
		} catch (Exception e) {
			throw new CrispException("error calling built-in function " + this, e);
		}
		if (result == null) {
			throw new CrispException("built-in function " + this + " returned null");
		}
		return result;
	}

	@Override
	public String toString() {
		return "builtin:" + getProperName();
	}

	// expects public method "callInternal"

}
