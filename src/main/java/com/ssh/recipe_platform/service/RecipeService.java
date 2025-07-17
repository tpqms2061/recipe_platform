package com.ssh.recipe_platform.service;


import com.ssh.recipe_platform.Repository.IngredientRepository;
import com.ssh.recipe_platform.Repository.RecipeIngredientRepository;
import com.ssh.recipe_platform.Repository.RecipeRepository;
import com.ssh.recipe_platform.dto.RecipeDto;
import com.ssh.recipe_platform.dto.RecipeResponseDto;
import com.ssh.recipe_platform.model.Recipe;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeResponseDto create(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        Recipe saved = recipeRepository.save(recipe);
        return new RecipeResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }


}
