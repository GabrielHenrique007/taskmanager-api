package com.taskmanager.taskmanager_api.service;

import com.taskmanager.taskmanager_api.model.Status;
import com.taskmanager.taskmanager_api.model.Tarefa;
import com.taskmanager.taskmanager_api.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa criarTarefa(String titulo, Status status){
        Tarefa novaTarefa = new Tarefa(null, titulo, status);
        return tarefaRepository.save(novaTarefa);
    }

    public boolean editarTarefa(Long id, String novoTitulo, Status novoStatus) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setTitulo(novoTitulo);
            tarefa.setStatus(novoStatus);
            tarefaRepository.save(tarefa);
            return true;
        })
                .orElse(false);
    }

    public boolean removerTarefa(Long id) {
       if (tarefaRepository.existsById(id)) {
           tarefaRepository.deleteById(id);
           return true;
       }
       return false;
    }

    public List<Tarefa> listarTarefasPorStatus(Status status) {
        return tarefaRepository.findByStatus(status);
    }
}
