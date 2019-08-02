package name.martingeisse.crisp.attached.runtime;

import name.martingeisse.crisp.common.builtin.BuiltinEnvironment;
import org.junit.Assert;
import org.junit.Test;

import static name.martingeisse.crisp.common.ExpressionBuilder.call;

/**
 *
 */
public class BuiltinTests {

	private Interpreter interpreter = new Interpreter();

	private void assertEval(Object expectedValue, Object expression) {
		Object actualValue = interpreter.evaluate(expression);
		Assert.assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testAdd() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(45, call("builtinAdd", 40, 5));
	}

	@Test
	public void testSubtract() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(37, call("builtinSubtract", 40, 3));
	}
	@Test
	public void testMultiply() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(120, call("builtinMultiply", 40, 3));
	}
	@Test
	public void testDivide() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(13, call("builtinDivide", 40, 3));
	}
	@Test
	public void testRemainder() {
		interpreter.defineGlobals(BuiltinEnvironment.TEMPLATE);
		assertEval(1, call("builtinRemainder", 40, 3));
	}

}
