package com.example.securinRecipe.Service;

import com.example.securinRecipe.Model.Recipe;
import com.example.securinRecipe.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;


    public Recipe addRecipe(Recipe recipe) {

        Integer prep = recipe.getPrepTime() != null ? recipe.getPrepTime() : 0;
        Integer cook = recipe.getCookTime() != null ? recipe.getCookTime() : 0;

        recipe.setTotalTime(prep + cook);

        return recipeRepository.save(recipe);
    }
    public Page<Recipe> getTopRecipes(Integer limit) {
        int val = (limit == null ? 0 : limit);
        PageRequest pageable = PageRequest.of(0, val, Sort.by("rating").descending());
        return recipeRepository.findAll(pageable);

    }

}
