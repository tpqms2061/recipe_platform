package com.ssh.recipe_platform.controller;


import com.ssh.recipe_platform.dto.AddIngredientDto;
import com.ssh.recipe_platform.dto.RecipeDetailDto;
import com.ssh.recipe_platform.dto.RecipeDto;
import com.ssh.recipe_platform.dto.RecipeResponseDto;
import com.ssh.recipe_platform.service.RecipeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public Page<RecipeResponseDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        return recipeService.list(pageable);
    }

    @GetMapping("/{id}")
    public RecipeDetailDto get(@PathVariable Long id) {
        return recipeService.get(id);
    }

    @PostMapping
    public RecipeResponseDto create(@Validated @RequestBody RecipeDto dto) {
        return recipeService.create(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/ingredients/add")
    public ResponseEntity<Void> addIngredient(
            @PathVariable Long id,
            @Validated @RequestBody AddIngredientDto dto
    ) {
        recipeService.addIngredient(id, dto);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public RecipeResponseDto update(@PathVariable Long id, @Validated @RequestBody RecipeDto dto) {

        return recipeService.update(id, dto);
    }



    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    public ResponseEntity<Void> removeIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ) {
        recipeService.removeIngredient(id, ingredientId);

        return ResponseEntity.noContent().build();
    }
}
