package com.example.securinRecipe.Model;

import com.example.securinRecipe.Convertor.NutrientsConvertor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("cuisine")
    @Column(name = "cuisine")
    private String cuisine;

    @JsonProperty("rating")
    @Column(name = "rating")
    private Double rating;

    @JsonProperty("total_time")
    @Column(name = "total_time")
    private Integer totalTime;

    @JsonProperty("prep_time")
    @Column(name = "prep_time")
    private Integer prepTime;

    @JsonProperty("cook_time")
    @Column(name = "cook_time")
    private Integer cookTime;


    @JsonProperty("description")
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonProperty("nutrients")
    @Lob
    @Convert(converter = NutrientsConvertor.class)
    @Column(columnDefinition = "TEXT")
    private Nutrients nutrients;

    @JsonProperty("serves")
    private String serves;
}
