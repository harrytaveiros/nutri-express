package br.com.nutriexpress.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Intercepta exceções de toda a aplicação e retorna uma resposta padronizada[cite: 1]
public class GlobalExceptionHandler {

    // 1. O código que JÁ ESTAVA no seu arquivo (Trata erros de @Valid e retorna 400)[cite: 1]
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    // 2. O NOVO CÓDIGO que você vai colar aqui embaixo (Trata erros do Service e retorna 404 ou 500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> erro = new HashMap<>();
        
        // Se a mensagem contiver "não encontrado", devolvemos o 404 Not Found[cite: 1]
        if (ex.getMessage().contains("não encontrado")) {
            erro.put("mensagem", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
        
        // Para outras falhas genéricas
        erro.put("erro", "Erro interno no servidor");
        erro.put("detalhe", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}