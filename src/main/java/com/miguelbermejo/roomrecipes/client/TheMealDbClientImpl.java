package com.miguelbermejo.roomrecipes.client;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.miguelbermejo.roomrecipes.dto.TheMealDbMealDTO;
import com.miguelbermejo.roomrecipes.dto.TheMealDbResponse;
import com.miguelbermejo.roomrecipes.exception.TheMealDbClientException;

@Component
public class TheMealDbClientImpl implements TheMealDbClient {

	private static final Logger logger = LoggerFactory.getLogger(TheMealDbClientImpl.class);

	private final WebClient theMealDbWebClient;
	private final long timeoutSeconds;

	public TheMealDbClientImpl(WebClient theMealDbWebClient,
			@Value("${app.themealdb.timeout-seconds}") long timeoutSeconds) {

		this.theMealDbWebClient = theMealDbWebClient;
		this.timeoutSeconds = timeoutSeconds;
	}

	@Override
	public List<TheMealDbMealDTO> searchMealsByName(String recipeName) {

		if (recipeName == null || recipeName.isBlank()) {
			logger.warn("El nombre de la receta está vacio. Saliendo de la llmada API...");
			return Collections.emptyList();
		}
		try {
			TheMealDbResponse response = theMealDbWebClient.get()
					.uri(uriBuilder -> uriBuilder.path("/search.php").queryParam("s", recipeName).build()).retrieve()
					.bodyToMono(TheMealDbResponse.class).timeout(Duration.ofSeconds(timeoutSeconds)).block();

			if (response == null || response.getMeals() == null) {
				return Collections.emptyList();
			}

			return response.getMeals();

		} catch (Exception e) {
			throw new TheMealDbClientException("Error consultando TheMealDB para: " + recipeName, e);
		}
	}

}
