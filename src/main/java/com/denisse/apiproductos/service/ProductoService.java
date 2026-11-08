
package com.denisse.apiproductos.service;

import com.denisse.apiproductos.model.Producto;
import com.denisse.apiproductos.exception.ProductoNoEncontradoException;
import com.denisse.apiproductos.exception.ProductoInvalidoException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final Map<Long, Producto> productos =
            new LinkedHashMap<>();

    private final AtomicLong contador =
            new AtomicLong(1);

    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos.values());
    }

    public Producto obtenerPorId(Long id) {

        Producto producto = productos.get(id);

        if (producto == null) {
            throw new ProductoNoEncontradoException(
                    "No existe un producto con el ID: " + id);
        }

        return producto;
    }

    public Producto registrar(Producto producto) {

        validarProducto(producto);

        producto.setId(contador.getAndIncrement());

        productos.put(producto.getId(), producto);

        return producto;
    }

    public Producto actualizar(Long id, Producto producto) {

        obtenerPorId(id);

        validarProducto(producto);

        producto.setId(id);

        productos.put(id, producto);

        return producto;
    }

    public void eliminar(Long id) {

        if (!productos.containsKey(id)) {
            throw new ProductoNoEncontradoException(
                    "No existe un producto con el ID: " + id);
        }

        productos.remove(id);
    }

    private void validarProducto(Producto producto) {

        if (producto == null) {
            throw new ProductoInvalidoException(
                    "Los datos del producto son obligatorios");
        }

        if (producto.getNombre() == null ||
                producto.getNombre().trim().isEmpty()) {

            throw new ProductoInvalidoException(
                    "El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() <= 0) {

            throw new ProductoInvalidoException(
                    "El precio debe ser mayor que cero");
        }

        if (producto.getStock() < 0) {

            throw new ProductoInvalidoException(
                    "El stock no puede ser negativo");
        }
    }
}
