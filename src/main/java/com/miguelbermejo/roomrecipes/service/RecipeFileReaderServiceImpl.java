package com.miguelbermejo.roomrecipes.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.miguelbermejo.roomrecipes.exception.RecipeFileReadingException;

@Service
public class RecipeFileReaderServiceImpl implements RecipeFileReaderService {

	@Override
	public List<String> readRecipeNames(String fileName) {
		try {
			ClassPathResource resource = new ClassPathResource(fileName);

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
				return reader.lines().map(String::trim).filter(line -> !line.isEmpty()).distinct().toList();
			}

		} catch (IOException e) {
			throw new RecipeFileReadingException("No se ha podido leer el archivo: " + fileName, e);
		}
	}

}
