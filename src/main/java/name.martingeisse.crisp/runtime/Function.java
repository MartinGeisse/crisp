/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.crisp.runtime;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public final class Function {

	private final String properName;
	private final Map<String, Object> environment;
	private final ImmutableList<Parameter> parameters;
	private final Object body;

	public Function(String properName, Map<String, Object> environment, ImmutableList<Parameter> parameters, Object body) {
		this.properName = properName;
		this.environment = environment;
		this.parameters = parameters;
		this.body = body;
	}

	public String getProperName() {
		return properName;
	}

	public Map<String, Object> getEnvironment() {
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

	public Map<String, Object> buildBodyEnvironment(List<Object> arguments) {
		if (arguments.size() != parameters.size()) {
			throw new CrispException("function " + properName + " expected " + parameters.size() + " arguments, got " + arguments.size());
		}
		Map<String, Object> bodyEnvironment = new HashMap<>(environment);
		for (int i = 0; i < parameters.size(); i++) {
			bodyEnvironment.put(parameters.get(i).getIdentifier(), arguments.get(i));
		}
		return bodyEnvironment;
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
