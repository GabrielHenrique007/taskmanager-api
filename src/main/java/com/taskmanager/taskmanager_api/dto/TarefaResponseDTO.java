package com.taskmanager.taskmanager_api.dto;

import com.taskmanager.taskmanager_api.model.Status;

public class TarefaResponseDTO {

    private Long id;
    private String titulo;
    private Status status;

    public TarefaResponseDTO(Long id, String titulo, Status status) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Status getStatus() {
        return status;
    }
}