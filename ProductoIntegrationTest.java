package com.edinson.forestfruit.integration;

import com.edinson.forestfruit.model.Producto;
import com.edinson.forestfruit.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    private Producto producto;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll().block();
        producto = new Producto(null, "Manzana", 2.50, "Fruta roja y jugosa");
    }

    @Test
    void crearProducto_debeRetornarProductoCreado() {
        webTestClient.post().uri("/api/productos")
                .body(Mono.just(producto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Producto.class)
                .value(p -> assertEquals("Manzana", p.getNombre()));
    }

    @Test
    void obtenerProductoPorId_debeRetornarProducto() {
        Producto productoGuardado = productoRepository.save(producto).block();

        webTestClient.get().uri("/api/productos/{id}", productoGuardado.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(p -> assertEquals("Manzana", p.getNombre()));
    }

    @Test
    void eliminarProductoPorId_debeRetornarNoContent() {
        Producto productoGuardado = productoRepository.save(producto).block();

        webTestClient.delete().uri("/api/productos/{id}", productoGuardado.getId())
                .exchange()
                .expectStatus().isNoContent();

        assertNull(productoRepository.findById(productoGuardado.getId()).block());
    }
}
