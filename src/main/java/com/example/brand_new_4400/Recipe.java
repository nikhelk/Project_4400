package com.example.brand_new_4400;

public class Recipe {
    public int recipeId;
    public String userEmail;
    public String recipeName;
    public String instructions;
    public Double averageRating;
    public Recipe(int recipeId, String userEmail, String recipeName, String instructions, Double averageRating) {
        this.recipeId = recipeId;
        this.userEmail = userEmail;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.averageRating = averageRating;
    }
}
