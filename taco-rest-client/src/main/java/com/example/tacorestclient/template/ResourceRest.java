package com.example.tacorestclient.template;

import com.example.tacorestclient.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/rest-template")
public class ResourceRest {

    RestTemplate rest = new RestTemplate();

    @GetMapping("/ingredient1")
    public Ingredient getIngredientById(String ingredientId) {
        Ingredient ingredient = rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
        return ingredient;
    }

    @GetMapping("/ingredient2")
    public Ingredient getIngredientById2(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, urlVariables);
    }

    @GetMapping("/ingredient3")
    public Ingredient getIngredientById3(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }

    @GetMapping("/ingredientEntity")
    public Ingredient getIngredientById4(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);

        log.info("Fetched time : " + responseEntity.getHeaders().getDate());

        return responseEntity.getBody();
    }

    @PutMapping("/ingredient")
    public void updateIngredient() {
        Ingredient ingredient = new Ingredient("FLTO", "Flour Tortilla v2", Ingredient.Type.WRAP);
        rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    @DeleteMapping("/ingredient/{id}")
    public void deleteIngredient(@PathVariable String id) {
        rest.delete("http://localhost:8080/ingredients/{id}", id);
    }
}
