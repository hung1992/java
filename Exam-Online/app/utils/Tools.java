/**
 * Copyright by Technologic Arts
 * Project: EXAMONLINE
 * Package: controllers
 * Author: khacvu
 */
package utils;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tools {
	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> JsonNode listToJson(List<T> models) {
		return mapper.convertValue(models, JsonNode.class);
	}
	public static <T> List<T> listFromJson(String content, Class<T> valueType)
			throws IOException {
		return mapper.readValue(content, mapper.getTypeFactory()
				.constructCollectionType(List.class, valueType));
	}

	public static JsonNode toJson(Object content) {
		return mapper.convertValue(content, JsonNode.class);
	}

	public static <T> T fromJson(JsonNode json, Class<T> valueType)
			throws IOException {
		return mapper.treeToValue(json, valueType);
	}

}
