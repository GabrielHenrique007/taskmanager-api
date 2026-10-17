package com.taskmanager.taskmanager_api.dto;

import com.taskmanager.taskmanager_api.model.Tarefa;

public class TarefaMapper {

    public static TarefaResponseDTO paraResponseDTO(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getStatus()
        );
    }

}