package com.example.tacorestclient.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Taco {
    private Long id;

    private Date createdAt;

    private String name;

    private List<Ingredient> ingredients;

    void createAt() {
        this.createdAt = new Date();
    }
}
