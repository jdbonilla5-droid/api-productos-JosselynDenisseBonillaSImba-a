
package com.denisse.apiproductos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarProductoNoEncontrado(
            ProductoNoEncontradoException ex) {

        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(ProductoInvalidoException.class)
    public ResponseEntity<Map<String, Object>> manejarProductoInvalido(
            ProductoInvalidoException ex) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus estado, String mensaje) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("status", estado.value());
        respuesta.put("error", estado.getReasonPhrase());
        respuesta.put("mensaje", mensaje);

        return ResponseEntity.status(estado).body(respuesta);
    }
}
