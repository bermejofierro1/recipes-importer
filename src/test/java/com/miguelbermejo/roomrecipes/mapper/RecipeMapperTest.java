package com.miguelbermejo.roomrecipes.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.miguelbermejo.roomrecipes.dto.RecipeDTO;
import com.miguelbermejo.roomrecipes.dto.TheMealDbMealDTO;
import com.miguelbermejo.roomrecipes.model.Ingredient;
import com.miguelbermejo.roomrecipes.model.Recipe;

public class RecipeMapperTest {
	private final RecipeMapper recipeMapper = new RecipeMapper();

	@Test
	void toRecipeDTO_shouldCleanMainFieldsAndIgnoreBlankIngredients() {
		TheMealDbMealDTO meal = new TheMealDbMealDTO();
		meal.setIdMeal(" 123 ");
		meal.setStrMeal(" Arrabiata ");
		meal.setStrCategory(" Pasta ");
		meal.setStrIngredient1(" Tomato ");
		meal.setStrMeasure1(" 2 cups ");
		meal.setStrIngredient2("   ");
		meal.setStrMeasure2(" ignored ");

		RecipeDTO dto = recipeMapper.toRecipeDTO(meal);

		assertEquals("123", dto.getMealDbId());
		assertEquals("Arrabiata", dto.getName());
		assertEquals("Pasta", dto.getCategory());
		assertEquals(1, dto.getIngredients().size());
		assertEquals("Tomato", dto.getIngredients().get(0).getName());
		assertEquals("2 cups", dto.getIngredients().get(0).getMeasure());
	}

	@Test
	void toEntity_shouldKeepMeasureAndNormalizeIngredientName() {
		TheMealDbMealDTO meal = new TheMealDbMealDTO();
		meal.setIdMeal("123");
		meal.setStrMeal("Recipe");
		meal.setStrIngredient1("  Chicken   Breast ");
		meal.setStrMeasure1(" 200g ");

		RecipeDTO dto = recipeMapper.toRecipeDTO(meal);
		Recipe recipe = recipeMapper.toEntity(dto);
		Ingredient ingredient = recipe.getIngredients().get(0);

		assertEquals("Chicken   Breast", ingredient.getName());
		assertEquals("200g", ingredient.getMeasure());
		assertEquals("chicken breast", ingredient.getNormalizedName());
	}

	@Test
	void toRecipeDTO_shouldMapTagsAndSourceUrl() {
		TheMealDbMealDTO meal = new TheMealDbMealDTO();
		meal.setIdMeal("123");
		meal.setStrMeal("Recipe");
		meal.setStrTags(" Vegan, Pasta, Vegan ");
		meal.setStrSource(" https://example.com/recipe ");

		RecipeDTO dto = recipeMapper.toRecipeDTO(meal);

		assertEquals(2, dto.getTags().size());
		assertEquals("Vegan", dto.getTags().get(0));
		assertEquals("Pasta", dto.getTags().get(1));
		assertEquals("https://example.com/recipe", dto.getSourceUrl());
	}

	@Test
	void toRecipeDTO_shouldReturnEmptyTagsWhenTagsAreBlank() {
		TheMealDbMealDTO meal = new TheMealDbMealDTO();
		meal.setIdMeal("123");
		meal.setStrMeal("Recipe");
		meal.setStrTags("   ");

		RecipeDTO dto = recipeMapper.toRecipeDTO(meal);

		assertTrue(dto.getTags().isEmpty());
	}
}
