package com.myasinmanager.reactive.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myasinmanager.dto.ItemDTO;

public class DeserializationTest {
	static String json = "{\n"
			+ "  \"asin\": \"B09B93ZDG4\",\n"
			+ "  \"attributes\": {\n"
			+ "    \"list_prices\": [\n"
			+ "      {\n"
			+ "        \"price\": \"23\"\n"
			+ "      },\n"
			+ "      {\n"
			+ "        \"price\": \"12\"\n"
			+ "      }\n"
			+ "    ]\n"
			+ "  }\n"
			+ "}";

	public static void main(String[] args) {
		Gson gson = new GsonBuilder()
			    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			    .create();
		ItemDTO item = gson.fromJson(json, ItemDTO.class);
		System.out.println(item);
	}

}
