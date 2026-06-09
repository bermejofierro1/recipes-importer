package com.miguelbermejo.roomrecipes.dto;

public class IngredientDTO {

	private String name;
	private String measure;

	public IngredientDTO() {
	}

	public IngredientDTO(String name, String measure) {
		this.name = name;
		this.measure = measure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

}
