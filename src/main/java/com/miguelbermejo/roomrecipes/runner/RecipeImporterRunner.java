package com.miguelbermejo.roomrecipes.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.miguelbermejo.roomrecipes.service.RecipeImportService;

@Component
public class RecipeImporterRunner implements CommandLineRunner {

	private final RecipeImportService recipeImportService;
	private final String inputFile;

	public RecipeImporterRunner(RecipeImportService recipeImportService,
			@Value("${app.recipes.input-file}") String inputFile) {
		this.recipeImportService = recipeImportService;
		this.inputFile = inputFile;
	}

	@Override
	public void run(String... args) throws Exception {

		recipeImportService.importRecipesFromFile(inputFile);
	}

}
