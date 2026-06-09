package com.miguelbermejo.roomrecipes.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.miguelbermejo.roomrecipes.dto.IngredientDTO;
import com.miguelbermejo.roomrecipes.dto.RecipeDTO;
import com.miguelbermejo.roomrecipes.dto.TheMealDbMealDTO;
import com.miguelbermejo.roomrecipes.model.Ingredient;
import com.miguelbermejo.roomrecipes.model.Recipe;

@Component
public class RecipeMapper {

	public RecipeDTO toRecipeDTO(TheMealDbMealDTO mealDTO) {
		return new RecipeDTO(clean(mealDTO.getIdMeal()), clean(mealDTO.getStrMeal()), clean(mealDTO.getStrCategory()),
				clean(mealDTO.getStrArea()), clean(mealDTO.getStrInstructions()), clean(mealDTO.getStrMealThumb()),
				clean(mealDTO.getStrYoutube()), mapIngredients(mealDTO), mapTags(mealDTO.getStrTags()),
				clean(mealDTO.getStrSource()));
	}

	public Recipe toEntity(RecipeDTO recipeDTO) {
		Recipe recipe = new Recipe(recipeDTO.getMealDbId(), recipeDTO.getName(), recipeDTO.getCategory(),
				recipeDTO.getArea(), recipeDTO.getInstructions(), recipeDTO.getImageUrl(), recipeDTO.getYoutubeUrl(),
				recipeDTO.getTags(), recipeDTO.getSourceUrl());

		recipeDTO.getIngredients().forEach(ingredientDTO -> recipe.addIngredient(new Ingredient(ingredientDTO.getName(),
				ingredientDTO.getMeasure(), normalizeIngredientName(ingredientDTO.getName()))));

		return recipe;
	}

	private List<IngredientDTO> mapIngredients(TheMealDbMealDTO mealDto) {
		List<IngredientDTO> ingredients = new ArrayList<>();

		addIngredient(ingredients, mealDto.getStrIngredient1(), mealDto.getStrMeasure1());
		addIngredient(ingredients, mealDto.getStrIngredient2(), mealDto.getStrMeasure2());
		addIngredient(ingredients, mealDto.getStrIngredient3(), mealDto.getStrMeasure3());
		addIngredient(ingredients, mealDto.getStrIngredient4(), mealDto.getStrMeasure4());
		addIngredient(ingredients, mealDto.getStrIngredient5(), mealDto.getStrMeasure5());
		addIngredient(ingredients, mealDto.getStrIngredient6(), mealDto.getStrMeasure6());
		addIngredient(ingredients, mealDto.getStrIngredient7(), mealDto.getStrMeasure7());
		addIngredient(ingredients, mealDto.getStrIngredient8(), mealDto.getStrMeasure8());
		addIngredient(ingredients, mealDto.getStrIngredient9(), mealDto.getStrMeasure9());
		addIngredient(ingredients, mealDto.getStrIngredient10(), mealDto.getStrMeasure10());
		addIngredient(ingredients, mealDto.getStrIngredient11(), mealDto.getStrMeasure11());
		addIngredient(ingredients, mealDto.getStrIngredient12(), mealDto.getStrMeasure12());
		addIngredient(ingredients, mealDto.getStrIngredient13(), mealDto.getStrMeasure13());
		addIngredient(ingredients, mealDto.getStrIngredient14(), mealDto.getStrMeasure14());
		addIngredient(ingredients, mealDto.getStrIngredient15(), mealDto.getStrMeasure15());
		addIngredient(ingredients, mealDto.getStrIngredient16(), mealDto.getStrMeasure16());
		addIngredient(ingredients, mealDto.getStrIngredient17(), mealDto.getStrMeasure17());
		addIngredient(ingredients, mealDto.getStrIngredient18(), mealDto.getStrMeasure18());
		addIngredient(ingredients, mealDto.getStrIngredient19(), mealDto.getStrMeasure19());
		addIngredient(ingredients, mealDto.getStrIngredient20(), mealDto.getStrMeasure20());

		return ingredients;
	}

	private void addIngredient(List<IngredientDTO> ingredients, String name, String measure) {
		String cleanName = clean(name);
		String cleanMeasure = clean(measure);

		if (cleanName != null && !cleanName.isBlank()) {
			ingredients.add(new IngredientDTO(cleanName, cleanMeasure));
		}
	}

	private String clean(String value) {
		if (value == null) {
			return null;
		}

		String cleaned = value.trim();

		if (cleaned.isEmpty()) {
			return null;
		}

		return cleaned;
	}

	private List<String> mapTags(String tags) {
		String cleanTags = clean(tags);

		if (cleanTags == null) {
			return Collections.emptyList();
		}

		return Arrays.stream(cleanTags.split(",")).map(String::trim).filter(tag -> !tag.isEmpty()).distinct().toList();
	}

	private String normalizeIngredientName(String value) {
		String cleaned = clean(value);

		if (cleaned == null) {
			return null;
		}

		return cleaned.toLowerCase().replaceAll("\\s+", " ");
	}
}
