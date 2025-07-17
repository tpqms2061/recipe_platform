package com.ssh.recipe_platform.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable //recipe - ingre 랑 합쳐지는 테이블를 뜻함
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode //
public class RecipeIngredientId implements Serializable {
    private Long recipeId;
    private Long ingredientId;
}
