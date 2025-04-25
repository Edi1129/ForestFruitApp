package com.edinson.forestfruit.service;

import com.edinson.forestfruit.model.Fruit;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FruitService {

    public List<Fruit> getAllFruits() {
        return Arrays.asList(
                new Fruit("Manzana", 1.5, "Fruta roja"),
                new Fruit("Plátano", 0.8, "Fruta amarilla")
        );
    }

    public Fruit getFruitByName(String nombre) {
        return getAllFruits().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}