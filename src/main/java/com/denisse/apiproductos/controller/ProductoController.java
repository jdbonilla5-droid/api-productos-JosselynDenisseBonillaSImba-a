
package com.denisse.apiproductos.controller;

import com.denisse.apiproductos.model.Producto;
import com.denisse.apiproductos.service.ProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Producto> registrar(
            @RequestBody Producto producto) {

        Producto productoRegistrado =
                productoService.registrar(producto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoRegistrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        Producto productoActualizado =
                productoService.actualizar(id, producto);

        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
