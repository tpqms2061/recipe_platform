package com.ssh.recipe_platform.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDto {
    @NotBlank
    private String title;
    private String description;

}
