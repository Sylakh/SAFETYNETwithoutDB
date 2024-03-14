package com.openclassrooms.safetynet.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderFromFile {

	private static final String FILE_PATH = ".//src//main//resources//data.json";

	public static RawData readData() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(new File(FILE_PATH), RawData.class);
	}
}