package com.miguelbermejo.roomrecipes.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.miguelbermejo.roomrecipes.client.TheMealDbClient;
import com.miguelbermejo.roomrecipes.dto.RecipeDTO;
import com.miguelbermejo.roomrecipes.dto.TheMealDbMealDTO;
import com.miguelbermejo.roomrecipes.exception.TheMealDbClientException;
import com.miguelbermejo.roomrecipes.mapper.RecipeMapper;
import com.miguelbermejo.roomrecipes.model.Recipe;
import com.miguelbermejo.roomrecipes.repositorio.RecipeRepository;

import jakarta.transaction.Transactional;

@Service
public class RecipeImportServiceImpl implements RecipeImportService {

	private static final Logger logger = LoggerFactory.getLogger(RecipeImportServiceImpl.class);

	private final RecipeFileReaderService recipeFileReaderService;
	private final TheMealDbClient theMealDbClient;
	private final RecipeMapper recipeMapper;
	private final RecipeRepository recipeRepository;

	public RecipeImportServiceImpl(RecipeFileReaderService recipeFileReaderService, TheMealDbClient theMealDbClient,
			RecipeMapper recipeMapper, RecipeRepository recipeRepository) {
		this.recipeFileReaderService = recipeFileReaderService;
		this.theMealDbClient = theMealDbClient;
		this.recipeMapper = recipeMapper;
		this.recipeRepository = recipeRepository;

	}

	@Override
	@Transactional
	public void importRecipesFromFile(String fileName) {
		List<String> recipeNames = recipeFileReaderService.readRecipeNames(fileName);

		int savedRecipes = 0;
		int skippedRecipes = 0;
		int notFoundSearches = 0;

		logger.info("Empezando la importación de recetas e ingredientes.....");

		for (String recipeName : recipeNames) {
			List<TheMealDbMealDTO> meals;

			try {
				meals = theMealDbClient.searchMealsByName(recipeName);
			} catch (TheMealDbClientException e) {
				logger.error("No se pudo consultar TheMealDB para {}. Se continúa con la siguiente búsqueda",
						recipeName, e);
				continue;
			}

			if (meals.isEmpty()) {
				notFoundSearches++;
				logger.warn("No se encontraron comidas para : {}", recipeName);
				continue;
			}

			for (TheMealDbMealDTO meal : meals) {
				RecipeDTO recipeDTO = recipeMapper.toRecipeDTO(meal);

				if (recipeRepository.existsByMealDbId(recipeDTO.getMealDbId())) {
					skippedRecipes++;
					continue;
				}

				Recipe recipe = recipeMapper.toEntity(recipeDTO);
				recipeRepository.save(recipe);
				savedRecipes++;

				logger.info("Receta guardada : {}", recipe.getName());
			}
		}

		logger.info("Importación finalizada");
		logger.info("Recetas guardadas: {}", savedRecipes);
		logger.info("Recetas duplicadas: {}", skippedRecipes);
		logger.info("Búsquedas sin resultado: {}", notFoundSearches);
	}

}
