package com.example.securinRecipe.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nutrients {
    private String calories;
    private String carbohydrateContent;
    private String cholesterolContent;
    private String fiberContent;
    private String proteinContent;
    private String saturatedFatContent;
    private String sodiumContent;
    private String sugarContent;
    private String fatContent;
    private String unsaturatedFatContent;
}
