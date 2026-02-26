package com.example.securinRecipe.Config;

import com.example.securinRecipe.Model.Recipe;
import com.example.securinRecipe.Repository.RecipeRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.infrastructure.item.json.JacksonJsonObjectReader;
import org.springframework.batch.infrastructure.item.json.JsonItemReader;
import org.springframework.batch.infrastructure.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfig {

    private final RecipeRepository recipeRepository;

    public BatchConfig(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


//    @Bean
//    public JsonItemReader<Recipe> jsonItemReader() {
//        return new JsonItemReaderBuilder<Recipe>()
//                .jsonObjectReader(new JacksonJsonObjectReader<>(Recipe.class))
//                .resource(new ClassPathResource("US_recipe-null.json"))
//                .name("recipeJsonItemReader")
//                .build();
//    }

    @Bean
    public ListItemReader<Recipe> reader() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = new ClassPathResource("US_recipes_null.json").getInputStream();
        Map<String, Recipe> recipeMap = mapper.readValue(inputStream, new TypeReference<Map<String, Recipe>>() {});

//        return new ListItemReader<>(new ArrayList<>(new HashSet<>(recipeMap.values())));
        return new ListItemReader<>(new ArrayList<>(recipeMap.values()));
    }

    @Bean
    public RepositoryItemWriter<Recipe> writer() {
        return new RepositoryItemWriterBuilder<Recipe>()
                .repository(recipeRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step recipeStep(JobRepository jobRepository,PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("recipeStep", jobRepository)
                .<Recipe, Recipe>chunk(1000)
                .reader(reader())
                .writer(writer())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job recipeJob(JobRepository jobRepository,
                         Step recipeStep) {

        return new JobBuilder("importRecipe", jobRepository)
                .start(recipeStep)
                .build();
    }
}
