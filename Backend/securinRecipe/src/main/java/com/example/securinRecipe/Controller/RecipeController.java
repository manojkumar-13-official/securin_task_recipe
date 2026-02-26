package com.example.securinRecipe.Controller;

import com.example.securinRecipe.Model.Recipe;
import com.example.securinRecipe.Service.RecipeService;
import org.apache.coyote.Response;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    private final JobOperator jobOperator;
    private final Job job;

    public RecipeController(JobOperator jobOperator, Job job) {
        this.jobOperator = jobOperator;
        this.job = job;
    }

    @PostMapping("/upload")
    public String uploadFile(){
        JobParameters parameters = new JobParametersBuilder().addLong(
                "startAt",System.currentTimeMillis())
                .toJobParameters();

        try {
            JobExecution execution = jobOperator.start(job, parameters);
            return execution.getStatus().toString();
        } catch (Exception e) {
            return "Error Occurred while uploading file: "+e.getMessage();
        }
    }

    @PostMapping("")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe){
        Recipe re = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(re);
    }

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> getTopRecipes(@RequestParam(defaultValue = "5") Integer limit) {

        Page<Recipe> recipes = recipeService.getTopRecipes(limit);

        Map<String, Object> response = new HashMap<>();
        response.put("data", recipes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
}
