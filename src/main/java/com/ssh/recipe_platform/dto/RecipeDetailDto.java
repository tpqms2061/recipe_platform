package com.ssh.recipe_platform.dto;

import com.ssh.recipe_platform.model.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailDto {
    private Long id;
    private String title;
    private String description;
    private List<RecipeIngredientDto> ingredients;
}