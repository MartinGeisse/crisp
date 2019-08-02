package name.martingeisse.crisp.attached.runtime;

import name.martingeisse.crisp.common.ExpressionBuilder;
import name.martingeisse.crisp.common.Identifier;
import name.martingeisse.crisp.common.Keyword;
import name.martingeisse.crisp.common.LowlevelParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class LowlevelParserTest {

	@Test
	public void testKeywords() throws IOException {
		Assert.assertEquals(new Keyword("foo"), new LowlevelParser("#foo").parse());
	}

	@Test
	public void testNumbers() throws IOException {
		Assert.assertEquals(0, new LowlevelParser("0").parse());
		Assert.assertEquals(42, new LowlevelParser("42").parse());
		Assert.assertEquals(42, new LowlevelParser("042").parse());
		Assert.assertEquals(-42, new LowlevelParser("-42").parse());
	}

	@Test
	public void testIdentifiers() throws IOException {
		Assert.assertEquals(new Identifier("foo"), new LowlevelParser("foo").parse());
	}

	@Test
	public void testStringLiterals() throws IOException {
		Assert.assertEquals("", new LowlevelParser("\"\"").parse());
		Assert.assertEquals("foo", new LowlevelParser("\"foo\"").parse());
		Assert.assertEquals("#foo", new LowlevelParser("\"#foo\"").parse());
		Assert.assertEquals(")", new LowlevelParser("\")\"").parse());
		Assert.assertEquals("\"", new LowlevelParser("\"\\\"\"").parse());
		Assert.assertEquals("\\", new LowlevelParser("\"\\\\\"").parse());
	}

	@Test
	public void testList() throws IOException {
		Assert.assertEquals(
			ExpressionBuilder.list(42, ExpressionBuilder.list(1, 2, 3), "foo"),
			new LowlevelParser("(42 (1 2 3) \"foo\")").parse()
		);
	}

	@Test
	public void testListWithWhitespace() throws IOException {
		Assert.assertEquals(
			ExpressionBuilder.list(42, ExpressionBuilder.list(1, 2, 3), "foo"),
			new LowlevelParser("   (42\t\t(1\n2\r3)\t  \"foo\" )  ").parse()
		);
	}

}
