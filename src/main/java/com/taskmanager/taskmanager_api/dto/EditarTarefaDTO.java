package com.taskmanager.taskmanager_api.dto;

import com.taskmanager.taskmanager_api.model.Status;
import jakarta.validation.constraints.NotBlank;

public class EditarTarefaDTO {

    @NotBlank
    private String titulo;

    private Status status;

    public EditarTarefaDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}