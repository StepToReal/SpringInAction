package com.example.tacorestclient.template;

import com.example.tacorestclient.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/rest")
public class RestTemplateExam {

    RestTemplate rest = new RestTemplate();

    @GetMapping("/ingredient/{id}")
    public Ingredient getIngredientById(@PathVariable(name = "id") String ingredientId) {
        Ingredient ingredient = rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
        return ingredient;
    }

    @GetMapping("/ingredient2/{id}")
    public Ingredient getIngredientById2(@PathVariable(name = "id") String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, urlVariables);
    }

    @GetMapping("/ingredient3/{id}")
    public Ingredient getIngredientById3(@PathVariable(name = "id") String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/api/ingredients/{id}")
                .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }

    @GetMapping("/ingredient4/{id}")
    public Ingredient getIngredientById4(@PathVariable(name = "id") String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
        log.info("Fetched time: " + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    @PutMapping("/ingredient")
    public void updateIngredient(@RequestBody Ingredient ingredient) {
        rest.put("http://localhost:8080/api/ingredients/{id}", ingredient, ingredient.getId());
    }

    @DeleteMapping("/ingredient/{id}")
    public void deleteINgredient(@PathVariable String id) {
        rest.delete("http://localhost:8080/api/ingredients/{id}", id);
    }

    @PostMapping("/ingredient")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
    }

    @PostMapping("/ingredient2")
    public URI createIngredient2(@RequestBody Ingredient ingredient) {
        return rest.postForLocation("http://localhost:8080/api/ingredients", ingredient);
    }

    @PostMapping("/ingredient3")
    public Ingredient createIngredient3(@RequestBody Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                rest.postForEntity("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);

        log.info("New resource created at " + responseEntity.getHeaders().getLocation());

        return responseEntity.getBody();
    }
}
