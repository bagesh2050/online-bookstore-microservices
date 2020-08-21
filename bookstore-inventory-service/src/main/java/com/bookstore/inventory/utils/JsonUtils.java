package com.bookstore.inventory.utils;

import java.io.IOException;
import java.util.TimeZone;

import com.bookstore.inventory.exception.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private JsonUtils() {
		super();
	}

	public static ApiErrorResponse buildApiError(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, ApiErrorResponse.class);
	}

	public static String toJson(Object object) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setTimeZone(TimeZone.getDefault());
		return mapper.writeValueAsString(object);
	}

	public static <T> T toObject(String jsonString, Class<T> objectType) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, objectType);
	}
}
