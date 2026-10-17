
package com.taskmanager.taskmanager_api.service;

import com.taskmanager.taskmanager_api.model.Status;
import com.taskmanager.taskmanager_api.model.Tarefa;
import com.taskmanager.taskmanager_api.repository.TarefaRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;

class TarefaServiceTest {

    @Test
    void deveListarTarefas() {
        // Arrange: preparar os objetos do teste
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        List<Tarefa> tarefasEsperadas = List.of(
                new Tarefa(1L, "Estudar Java", Status.PENDENTE),
                new Tarefa(2L, "Estudar Spring", Status.EM_ANDAMENTO)
        );

        when(repository.findAll()).thenReturn(tarefasEsperadas);

        // Act: executar o método que queremos testar
        List<Tarefa> resultado = service.listarTarefas();

        // Assert: verificar o resultado
        assertEquals(2, resultado.size());
        assertEquals(tarefasEsperadas, resultado);
    }


    @Test
    void deveCriarTarefa() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        when(repository.save(org.mockito.ArgumentMatchers.any(Tarefa.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        Tarefa resultado = service.criarTarefa(
                "Estudar Java",
                Status.PENDENTE
        );

        assertEquals("Estudar Java", resultado.getTitulo());
        assertEquals(Status.PENDENTE, resultado.getStatus());
    }


    @Test
    void deveEditarTarefa() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        Tarefa tarefa = new Tarefa(
                1L,
                "Estudar Java",
                Status.PENDENTE
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(tarefa));

        when(repository.save(org.mockito.ArgumentMatchers.any(Tarefa.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        boolean resultado = service.editarTarefa(
                1L,
                "Estudar Spring Boot",
                Status.EM_ANDAMENTO
        );

        assertEquals(true, resultado);
        assertEquals("Estudar Spring Boot", tarefa.getTitulo());
        assertEquals(Status.EM_ANDAMENTO, tarefa.getStatus());
    }


    @Test
    void naoDeveEditarTarefaInexistente() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        boolean resultado = service.editarTarefa(
                99L,
                "Estudar Spring Boot",
                Status.EM_ANDAMENTO
        );

        assertEquals(false, resultado);
    }


    @Test
    void deveRemoverTarefaExistente() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        when(repository.existsById(1L)).thenReturn(true);

        boolean resultado = service.removerTarefa(1L);

        assertEquals(true, resultado);

        org.mockito.Mockito.verify(repository).deleteById(1L);
    }


    @Test
    void naoDeveRemoverTarefaInexistente() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        when(repository.existsById(99L)).thenReturn(false);

        boolean resultado = service.removerTarefa(99L);

        assertEquals(false, resultado);

        org.mockito.Mockito.verify(repository, org.mockito.Mockito.never())
                .deleteById(99L);
    }


    @Test
    void deveListarTarefasPorStatus() {
        TarefaRepository repository = mock(TarefaRepository.class);
        TarefaService service = new TarefaService(repository);

        List<Tarefa> tarefasPendentes = List.of(
                new Tarefa(1L, "Estudar Java", Status.PENDENTE),
                new Tarefa(2L, "Praticar Java", Status.PENDENTE)
        );

        when(repository.findByStatus(Status.PENDENTE))
                .thenReturn(tarefasPendentes);

        List<Tarefa> resultado =
                service.listarTarefasPorStatus(Status.PENDENTE);

        assertEquals(2, resultado.size());
        assertEquals(tarefasPendentes, resultado);
    }


}
