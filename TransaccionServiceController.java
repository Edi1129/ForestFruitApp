package com.edinson.forestfruit.controller;

import com.edinson.forestfruit.model.Transaccion;
import com.edinson.forestfruit.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionServiceController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        return transaccionService.guardarTransaccion(transaccion);
    }

    @GetMapping("/{id}")
    public Mono<Transaccion> obtenerTransaccion(@PathVariable String id) {
        return transaccionService.obtenerTransaccion(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarTransaccion(@PathVariable String id) {
        return transaccionService.eliminarTransaccion(id);
    }
}
