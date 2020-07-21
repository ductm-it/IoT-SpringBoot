package com.iot.platform.Converter;

import java.util.HashMap;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Converter
public class ListConverter implements AttributeConverter<List<String>, String> {

	@Override
	public String convertToDatabaseColumn(List<String> list) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(list);
	}

	@Override
	public List<String> convertToEntityAttribute(String json) {
		if (json == null || json.trim().isEmpty()) {
			new HashMap<>();
		}

		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<List<String>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		return gson.fromJson(json, type);
	}

}