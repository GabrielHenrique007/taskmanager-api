package com.taskmanager.taskmanager_api.repository;

import com.taskmanager.taskmanager_api.model.Status;
import com.taskmanager.taskmanager_api.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatus(Status status);
}
