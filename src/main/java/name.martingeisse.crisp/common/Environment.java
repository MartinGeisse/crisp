package name.martingeisse.crisp.common;

import java.util.Map;

/**
 *
 */
public final class Environment {

	private final Map<String, Object> bindings;
	private final Environment enclosingEnvironment;

	public Environment(Map<String, Object> bindings, Environment enclosingEnvironment) {
		this.bindings = bindings;
		this.enclosingEnvironment = enclosingEnvironment;
	}

	public Object get(String name) {
		Object value = getOrNull(name);
		if (value == null) {
			throw new CrispException("cannot resolve name: " + name);
		}
		return value;
	}

	public Object getOrNull(String name) {
		Object value = bindings.get(name);
		if (value != null) {
			return value;
		}
		if (enclosingEnvironment == null) {
			return null;
		}
		return enclosingEnvironment.get(name);
	}

}
