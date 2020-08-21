package com.bookstore.inventory.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class TestUtility {

	public static String mapObjectToJson(final Object object) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	public static <T> T mapJsonToObject(final String jsonString, Class<T> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, clazz);

	}

	public static String mapFilterObjectToJson(final Object object, final String filterName)
			throws JsonProcessingException {
		final SimpleBeanPropertyFilter simpleBeanFilter = SimpleBeanPropertyFilter.serializeAll();
		final SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName, simpleBeanFilter);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setFilterProvider(filterProvider);
		return mapper.writeValueAsString(object);
	}
}
