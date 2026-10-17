package com.taskmanager.taskmanager_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarJsonInvalido (HttpMessageNotReadableException ex) {

        Map<String, String> erro = new HashMap<>();

        erro.put("erro", "JSON inválido");
        erro.put("mensagem",
                "Verifique se o JSON enviado está correto e se os valores possuem os formatos esperados.");

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErroValidacao(MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erroCampo -> {
            erros.put(
                    erroCampo.getField(),
                    erroCampo.getDefaultMessage()
            );
        });

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("erro", "Erro de validação");
        resposta.put("mensagens", erros);

        return ResponseEntity.badRequest().body(resposta);
    }



}
