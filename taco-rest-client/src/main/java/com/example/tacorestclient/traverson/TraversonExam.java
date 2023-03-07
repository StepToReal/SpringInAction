package com.example.tacorestclient.traverson;

import com.example.tacorestclient.model.Ingredient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/traverson")
public class TraversonExam {
    Traverson traverson = new Traverson(URI.create("http://localhost:8080/"), MediaTypes.HAL_JSON);

    @GetMapping
    public void getTraverson() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

        CollectionModel<Ingredient> ingredientRes =
                traverson.follow("ingredients").toObject(ingredientType);

        Collection<Ingredient> ingredients = ingredientRes.getContent();
    }
}
