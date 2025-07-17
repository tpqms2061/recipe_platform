package com.ssh.recipe_platform.Repository;

import com.ssh.recipe_platform.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);
    //이름을 기준으로 검색을 할수있는 메소드 / optional은 name이 없을경우를 대비해서
}
