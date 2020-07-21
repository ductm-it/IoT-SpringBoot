package com.iot.platform.Converter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Converter
public class MapConverter implements AttributeConverter<Map<String, Long>, String> {

	@Override
	public String convertToDatabaseColumn(Map<String, Long> dict) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(dict);
	}

	@Override
	public Map<String, Long> convertToEntityAttribute(String json) {
		if (json == null || json.trim().isEmpty()) {
			new HashMap<>();
		}

		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<Map<String, Long>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		return gson.fromJson(json, type);
	}

}