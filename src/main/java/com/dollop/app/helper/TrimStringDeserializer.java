package com.dollop.app.helper;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class TrimStringDeserializer  extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
		System.err.println("deserialize called ====");
		
		String value = jsonParser.getValueAsString();
	        if (value != null) {
	            return value.trim(); // Trims the incoming string
	        }
	        return null; // Return null if the value is null
	}

}
