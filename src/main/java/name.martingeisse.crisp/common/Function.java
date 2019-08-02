/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.common;

import com.google.common.collect.ImmutableList;
import name.martingeisse.crisp.common.CrispException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public final class Function {

	private final String properName;
	private final Environment environment;
	private final ImmutableList<Parameter> parameters;
	private final Object body;

	public Function(String properName, Environment environment, ImmutableList<Parameter> parameters, Object body) {
		this.properName = properName;
		this.environment = environment;
		this.parameters = parameters;
		this.body = body;
	}

	public String getProperName() {
		return properName;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public ImmutableList<Parameter> getParameters() {
		return parameters;
	}

	public Object getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "function " + properName;
	}

	public Environment buildBodyEnvironment(Object[] arguments) {
		if (arguments.length != parameters.size()) {
			throw new CrispException("function " + properName + " expected " + parameters.size() + " arguments, got " + arguments.length);
		}
		Map<String, Object> bodyBindings = new HashMap<>();
		for (int i = 0; i < parameters.size(); i++) {
			bodyBindings.put(parameters.get(i).getIdentifier(), arguments[i]);
		}
		return new Environment(bodyBindings, environment);
	}

	public static final class Parameter {

		private final String identifier;

		public Parameter(String identifier) {
			this.identifier = identifier;
		}

		public String getIdentifier() {
			return identifier;
		}

	}

}
