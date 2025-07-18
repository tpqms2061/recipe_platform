package com.ssh.recipe_platform.service;


import com.ssh.recipe_platform.Repository.IngredientRepository;
import com.ssh.recipe_platform.Repository.RecipeIngredientRepository;
import com.ssh.recipe_platform.Repository.RecipeRepository;
import com.ssh.recipe_platform.dto.*;
import com.ssh.recipe_platform.model.Ingredient;
import com.ssh.recipe_platform.model.Recipe;
import com.ssh.recipe_platform.model.RecipeIngredient;
import com.ssh.recipe_platform.model.RecipeIngredientId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    // 전체조회 + 페이징
    public Page<RecipeResponseDto> list (Pageable pageable) {
        // findAll 응답은 Page<recipe> 객체 형태
        // return 타입과 맞춰주기 위해 .map 사용
        // 각 항목을 RecipeResponseDto로 변환하여 Page<RecipeResponseDto> 형태로 반환하는 메서드.
        return recipeRepository.findAll(pageable)
                .map(recipe -> new RecipeResponseDto(recipe.getId(), recipe.getTitle(), recipe.getDescription()));
    }
    // 단일조회
    public RecipeDetailDto get(Long id) {
        // 해당 레시피 존재하는지 확인
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 레시피가 없습니다."));
        // recipe.getRecipeIngredients() → 중간 엔티티 RecipeIngredient 리스트 반환
        // .stream()으로 RecipeIngredient 리스트의 데이터 하나씩 처리
        // .map()으로 RecipeIngredient → RecipeIngredientDto로 변환
        // .toList()로 새로운 리스트로 수집하여 List<RecipeIngredientDto> 형태로 ingredientDtos에 저장
        List<RecipeIngredientDto> ingredientDtos = recipe.getRecipeIngredients().stream()
                .map(
                        recipeIngredient -> new RecipeIngredientDto(
                                recipeIngredient.getIngredient().getId(),
                                recipeIngredient.getIngredient().getName(),
                                recipeIngredient.getQuantity()
                        )
                ).toList();
        // RecipeDetailDto 생성해 전체 레시피 정보를 담아 반환
        return new RecipeDetailDto(recipe.getId(), recipe.getTitle(), recipe.getDescription(), ingredientDtos );
    }

    public RecipeResponseDto create(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        // DB에 저장된 객체를 saved에 다시 받아옴
        // DB에 확실히 저장된 엔티티를 기준으로 후속 처리를 안전하게 하기 위함
        Recipe saved = recipeRepository.save(recipe);
        // RecipeResponseDto 생성해 응답 시 필요한 레시피 정보를 담아 반환
        return new RecipeResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    public RecipeResponseDto update(Long id, RecipeDto dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("레시피가 존재하지 않습니다."));
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        // DB에 저장된 객체를 saved에 다시 받아옴
        // DB에 확실히 저장된 엔티티를 기준으로 후속 처리를 안전하게 하기 위함
        Recipe saved = recipeRepository.save(recipe);
        // RecipeResponseDto 생성해 응답 시 필요한 레시피 정보를 담아 반환
        return new RecipeResponseDto(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    //delete로직
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public void addIngredient(Long recipeId, AddIngredientDto dto) {
        // 해당 레시피가 있어야 추가가능, 존재하는지 확인
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("레시피를 찾을 수 없습니다."));
        // 추가할 재료가 있어야 추가가능, 존재하는지 확인
        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new NoSuchElementException("재료를 찾을 수 없습니다."));
        // RecipeIngredientId 복합키 객체 생성
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredient.getId());
        if(recipeIngredientRepository.existsById(id)) {
            throw new IllegalStateException("이미 추가된 재료입니다.");
        }

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(dto.getQuantity());

        // recipeIngredients는 List<RecipeIngredient> 이므로 setRecipeIngredient()가 아닌 add로 추가
        recipe.getRecipeIngredients().add(recipeIngredient);
        // cascade 옵션 덕분에 recipe에서 save하면 recipeingredient에서도 save
        recipeRepository.save(recipe);
    }
    // 레시피에 재료 삭제
    public void removeIngredient(Long recipeId, Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        recipeIngredientRepository.deleteById(id);
    }
}