package name.martingeisse.crisp.common.builtin;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class BuiltinEnvironment {

	public static final ImmutableMap<String, Object> TEMPLATE;

	static {
		Map<String, Object> map = new HashMap<>();
		add(map, new BuiltinAdd(), new BuiltinSubtract(), new BuiltinMultiply(), new BuiltinDivide(), new BuiltinRemainder());
		TEMPLATE = ImmutableMap.copyOf(map);
	}

	private static void add(Map<String, Object> map, Builtin... builtins) {
		for (Builtin builtin : builtins) {
			map.put(builtin.getProperName(), builtin);
		}
	}

}
