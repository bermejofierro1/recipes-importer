package com.miguelbermejo.roomrecipes.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelbermejo.roomrecipes.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

	boolean existsByMealDbId(String mealDbId);

	Optional<Recipe> findByMealDbId(String mealDbId);
}
