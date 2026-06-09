package com.miguelbermejo.roomrecipes.dto;

import java.util.List;

public class RecipeDTO {

	private String mealDbId;
	private String name;
	private String category;
	private String area;
	private String instructions;
	private String imageUrl;
	private String youtubeUrl;
	private List<IngredientDTO> ingredients;
	private List<String> tags;
	private String sourceUrl;

	public RecipeDTO() {
	}

	public RecipeDTO(String mealDbId, String name, String category, String area, String instructions, String imageUrl,
			String youtubeUrl, List<IngredientDTO> ingredients, List<String> tags, String sourceUrl) {
		this.mealDbId = mealDbId;
		this.name = name;
		this.category = category;
		this.area = area;
		this.instructions = instructions;
		this.imageUrl = imageUrl;
		this.youtubeUrl = youtubeUrl;
		this.ingredients = ingredients;
		this.tags = tags;
		this.sourceUrl = sourceUrl;
	}

	public String getMealDbId() {
		return mealDbId;
	}

	public void setMealDbId(String mealDbId) {
		this.mealDbId = mealDbId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

}
