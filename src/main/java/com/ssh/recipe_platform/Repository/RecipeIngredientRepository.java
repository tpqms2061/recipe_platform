package com.ssh.recipe_platform.Repository;

import com.ssh.recipe_platform.model.RecipeIngredient;
import com.ssh.recipe_platform.model.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
}

//RecipeIngredientId - RecipeIngredient는 기본키가 없기때문에 따로만든 RecipeIngredientId 로 설정
