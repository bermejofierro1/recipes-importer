package com.miguelbermejo.roomrecipes.dto;

import java.util.List;

public class TheMealDbResponse {

	private List<TheMealDbMealDTO> meals;

	public List<TheMealDbMealDTO> getMeals() {
		return meals;
	}

	public void setMeals(List<TheMealDbMealDTO> meals) {
		this.meals = meals;
	}

}
