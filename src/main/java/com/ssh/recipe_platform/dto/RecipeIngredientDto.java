package com.ssh.recipe_platform.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {
    private Long ingredientId;
    private String ingredientName;
    private String quantity;
}