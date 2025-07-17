package com.ssh.recipe_platform.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name= "recipe_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY) //다대일관계  - 내용이 필요할때 사용한다
    @MapsId("recipeId") // 만든 ID 를 embbeded로 만든 Id를 가리키는것
    @JoinColumn(name = "recipe_id") //  다쪽이니까 다쪽에 id를 기록
    @JsonBackReference("recipe-ri") // 순환참조 방지
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    @JsonBackReference("ingredient-ri")
    private Ingredient ingredient;

    //매니 투매니가 아니니 중간값

    @Column(length = 50)
    private String quantity;
}
