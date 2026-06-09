package com.miguelbermejo.roomrecipes.client;

import java.util.List;

import com.miguelbermejo.roomrecipes.dto.TheMealDbMealDTO;

public interface TheMealDbClient {

	List<TheMealDbMealDTO> searchMealsByName(String recipeName);
}
