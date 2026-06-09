package com.miguelbermejo.roomrecipes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String mealDbId;

	@Column(nullable = false)
	private String name;

	private String category;

	private String area;

	@Column(columnDefinition = "TEXT")
	private String instructions;

	private String imageUrl;

	private String youtubeUrl;

	@ElementCollection
	@CollectionTable(name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id")

	)
	@Column(name = "tag")
	private List<String> tags = new ArrayList<>();

	private String sourceUrl;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Ingredient> ingredients = new ArrayList<>();

	public Recipe() {
	}

	public Recipe(String mealDbId, String name, String category, String area, String instructions, String imageUrl,
			String youtubeUrl, List<String> tags, String sourceUrl) {
		this.mealDbId = mealDbId;
		this.name = name;
		this.category = category;
		this.area = area;
		this.instructions = instructions;
		this.imageUrl = imageUrl;
		this.youtubeUrl = youtubeUrl;
		this.tags = tags != null ? tags : new ArrayList<>();
		this.sourceUrl = sourceUrl;
	}

	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
		ingredient.setRecipe(this);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
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
