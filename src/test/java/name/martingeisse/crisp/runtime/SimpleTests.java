package name.martingeisse.crisp.runtime;

import name.martingeisse.crisp.runtime.builtin.BuiltinEnvironment;
import org.junit.Assert;
import org.junit.Test;

import static name.martingeisse.crisp.runtime.ExpressionBuilder.*;

/**
 *
 */
public class SimpleTests {

	private Interpreter interpreter = new Interpreter();

	private void assertEval(Object expectedValue, Object expression) {
		Object actualValue = interpreter.evaluate(expression);
		Assert.assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testLiterals() {
		assertEval(5, 5);
		assertEval(Null.INSTANCE, Null.INSTANCE);
		assertEval(false, false);
		assertEval(true, true);
		assertEval("foo", "foo");
	}

	@Test(expected = CrispException.class)
	public void testUndefinedIdentifier() {
		interpreter.evaluate(new Identifier("foo"));
	}

	@Test
	public void testDefinedIdentifier() {
		interpreter.defineGlobal("foo", 97);
		assertEval(97, new Identifier("foo"));
	}

	@Test
	public void testBuiltin() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(37, call("builtinSubtract", 40, 3));
	}

	@Test
	public void testNestedCalls() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(36, call("builtinSubtract", 40, call("builtinAdd", 3, 1)));
	}

	@Test
	public void testKeywordExpressions() {
		assertEval(Null.INSTANCE, keyword("null"));
		assertEval(false, keyword("false"));
		assertEval(true, keyword("true"));

	}

	@Test
	public void testLambda() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(6, list(lambda(call("builtinAdd", identifier("x"), identifier("x")), "x"), 3));
	}

}
