package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.dto.EditarTarefaDTO;
import com.taskmanager.taskmanager_api.dto.TarefaMapper;
import com.taskmanager.taskmanager_api.model.Status;
import com.taskmanager.taskmanager_api.model.Tarefa;
import com.taskmanager.taskmanager_api.dto.CriarTarefaDTO;
import com.taskmanager.taskmanager_api.dto.TarefaResponseDTO;
import com.taskmanager.taskmanager_api.service.TarefaService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService){
        this.tarefaService = tarefaService;
    }

    @GetMapping("/tarefas")
    public List<TarefaResponseDTO> listarTarefas(@RequestParam(required = false)Status status) {

        List<Tarefa> tarefas;

        if (status != null) {
            tarefas = tarefaService.listarTarefasPorStatus(status);
        } else {
            tarefas = tarefaService.listarTarefas();
        }

        return tarefas.stream().map(TarefaMapper::paraResponseDTO).collect(Collectors.toList());
    }

    @PostMapping("/tarefas")
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody CriarTarefaDTO dto) {
        Tarefa novaTarefa = tarefaService.criarTarefa(dto.getTitulo(), dto.getStatus());

        TarefaResponseDTO resposta = TarefaMapper.paraResponseDTO(novaTarefa);

        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/tarefas/{id}")
    public ResponseEntity<?> editarTarefa(@PathVariable Long id, @Valid @RequestBody EditarTarefaDTO dto) {
        boolean editada = tarefaService.editarTarefa(id, dto.getTitulo(), dto.getStatus());

        if (editada) {
            return ResponseEntity.noContent().build();
        }

        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Tarefa não encontrada");
        erro.put("mensagem", "Não existe uma tarefa com o ID informado.");

        return ResponseEntity.status(404).body(erro);
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<?> removerTarefa(@PathVariable Long id) {
        boolean removida =  tarefaService.removerTarefa(id);

        if (removida) {
            return ResponseEntity.noContent().build();
        }

        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Tarefa não encontrada");
        erro.put("mensagem", "Não existe uma tarefa com o ID informado.");

        return ResponseEntity.status(404).body(erro);
    }

}
