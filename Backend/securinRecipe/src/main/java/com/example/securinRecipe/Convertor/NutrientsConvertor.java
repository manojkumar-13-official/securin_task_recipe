package com.example.securinRecipe.Convertor;

import com.example.securinRecipe.Model.Nutrients;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.databind.ObjectMapper;

@Converter
public class NutrientsConvertor implements AttributeConverter<Nutrients,String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Nutrients nutrients) {
        try{
            if(nutrients==null){
                return null;
            }
            return objectMapper.writeValueAsString(nutrients);
        }catch (Exception e){
            return "Error converting Nutrients to JSON: "+e.getMessage();
        }
    }

    @Override
    public Nutrients convertToEntityAttribute(String s) {
        try{
            if(s==null){
                return null;
            }
            return objectMapper.readValue(s, Nutrients.class);
        }catch (Exception e){
            throw new RuntimeException("Error converting JSON to Nutrients: "+e.getMessage());
        }
    }
}
