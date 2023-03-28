package com.example.tacorestclient.traverson;

import com.example.tacorestclient.model.Ingredient;
import com.example.tacorestclient.model.Taco;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/traverson")
public class TraversonExam {
    Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
    RestTemplate rest = new RestTemplate();

    @GetMapping
    public void getIngredient() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
                };

        CollectionModel<Ingredient> ingredientRes =
                traverson.follow("ingredients").toObject(ingredientType);

        Collection<Ingredient> ingredients = ingredientRes.getContent();

        System.out.println("done");
    }

    @GetMapping("/recent/tacos")
    public void getRecentTacos() {
        ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<>() {
        };

        CollectionModel<Taco> tacoCollection = traverson
                .follow("tacos", "recents")
                .toObject(tacoType);

        Collection<Taco> tacos = tacoCollection.getContent();

        System.out.println("done");
    }

    @PostMapping("/ingredient")
    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientUrl = traverson
                .follow("ingredients")
                .asLink()
                .getHref();

        return rest.postForObject(ingredientUrl, ingredient, Ingredient.class);
    }
}
