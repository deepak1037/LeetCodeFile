package com.leetcode.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonService {

	
	/** The Constant MAPPER. */
	private static final ObjectMapper MAPPER;

	/** The Constant for error log string. */
	private static final String ERROR_LOG_STR = "Got IOException while converting from Json to Object..{}";
	
	/**
	 * Instantiates a new json service.
	 */
	private JsonService() {
		// Added to remove sonar issues.
	}
	
	static {		
		MAPPER = new ObjectMapper();
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		MAPPER.setSerializationInclusion(Include.NON_NULL);
	}
	
	/**
	 * Gets the object from json.
	 *
	 * @param <T> the generic type
	 * @param jsonString the json string
	 * @param valueType the value type
	 * @return the object from json
	 */
	public static <T> T getObjectFromJson(final Object jsonString, final Class<T> valueType) throws Exception{
		T object = null;
		if (jsonString != null) {
			try {
				object = MAPPER.readValue(jsonString.toString(), valueType);
			} catch (IOException io) {
				
				throw io;
			}
		}
		return object;
	}
	
	/**
	 * Gets the object from json.
	 *
	 * @param <T> the generic type
	 * @param jsonString the json string
	 * @param type the type
	 * @return the object from json
	 */
	public static <T> T getObjectFromJson(final Object jsonString, final TypeReference<T> type) throws Exception{
		T object = null;
		if (jsonString != null) {
			try {
				object = MAPPER.readValue(jsonString.toString(), type);
			} catch (IOException io) {
				throw io;
			}
		}
		return object;
	}
	
	/**
	 * Gets the json from object.
	 *
	 * @param object the object
	 * @return the json from object
	 */
	public static String getJsonFromObject(final Object object)  throws Exception{
		String jsonString = null;
		if(object != null) {
			try {
				jsonString = MAPPER.writeValueAsString(object);
			} catch(JsonProcessingException ex) {
				throw ex;
			}
		}
		return jsonString;
	}
	
	public static <T> T getObjectFromJsonTree(final Object jsonTree,String element, final Class<T> valueType)  throws Exception{
		T object = null;
		if (jsonTree != null) {
			try {
				JsonNode jsonTreeNode = MAPPER.readTree(jsonTree.toString());
				JsonNode parent=jsonTreeNode.findValue(element);
				if(parent==null)
					return null;
				object = MAPPER.readValue(parent.toString(), valueType);
			} catch (IOException io) {
				throw io;
			}
		}
		return object;
	}
	
	/**
	 * Gets the list object from json.
	 *
	 * @param <T> the generic type
	 * @param jsonString the json string
	 * @param valueType the value type
	 * @return the list object from json
	 */
	public static <T> List<T> getListObjectFromJsonTree(final Object jsonTree, String element, final Class<T> valueType)  throws Exception{		
		List<T> object = null;
		if (jsonTree != null) {
			try {
				JsonNode jsonTreeNode = MAPPER.readTree(jsonTree.toString());
				JsonNode parent=jsonTreeNode.findValue(element);
				if(parent==null)
					return null;
				CollectionLikeType typeref = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, valueType);
				object = MAPPER.readValue(parent.toString(), typeref);
			} catch (IOException io) {
				throw io;
			}
		}
		return object;
	}
	
}
