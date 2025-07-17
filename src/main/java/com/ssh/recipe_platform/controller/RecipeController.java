package com.ssh.recipe_platform.controller;


import com.ssh.recipe_platform.dto.RecipeDto;
import com.ssh.recipe_platform.dto.RecipeResponseDto;
import com.ssh.recipe_platform.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public RecipeResponseDto create(@Validated @RequestBody RecipeDto dto) {
        return recipeService.create(dto);

    }
}
