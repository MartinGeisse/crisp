package name.martingeisse.crisp.runtime.builtin;

/**
 *
 */
public interface Builtin {

	Object call(Object... arguments);

	String getProperName();

}
