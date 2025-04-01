package com.edinson.forestfruit.controller;

import com.edinson.forestfruit.model.Fruit;
import com.edinson.forestfruit.repository.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class FruitControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FruitRepository fruitRepository;

    private Fruit fruta;

    @BeforeEach
    void setUp() {
        fruta = new Fruit(null, "Manzana", 2.50, "Fruta roja y jugosa");
        fruitRepository.deleteAll()
                .then(fruitRepository.save(fruta))
                .block();
    }


    @Test
    void obtenerFrutaPorNombre_debeRetornarFruta() {
        webTestClient.get().uri("/api/frutas/Manzana")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Fruit.class)
                .value(fruit -> {
                    assertEquals("Manzana", fruit.getName());
                    assertEquals(2.50, fruit.getPrice());
                    assertEquals("Fruta roja y jugosa", fruit.getDescription());
                });
    }


    @Test
    void crearFruta_debeRetornarFrutaCreada() {
        Fruit nuevaFruta = new Fruit(null, "Pera", 1.80, "Fruta verde y dulce");

        webTestClient.post().uri("/api/frutas")
                .body(Mono.just(nuevaFruta), Fruit.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Fruit.class)
                .value(fruit -> {
                    assertEquals("Pera", fruit.getName());
                    assertEquals(1.80, fruit.getPrice());
                    assertEquals("Fruta verde y dulce", fruit.getDescription());
                });
    }

}

