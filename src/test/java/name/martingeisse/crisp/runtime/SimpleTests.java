package name.martingeisse.crisp.runtime;

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
	public void testPlus() {
		assertEval(45, call("builtinPlus", 40, 5));
	}

}
