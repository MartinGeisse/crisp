package name.martingeisse.crisp.common.builtin;

/**
 *
 */
public interface Builtin {

	Object call(Object... arguments);

	String getProperName();

}
